package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudentity.domain.R;
import org.example.service.CheckInService;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInServiceImpl implements CheckInService {

    // 用户签到的key的前缀
    private static final String CHECK_IN_PRE_KEY = "USER_CHECKIN:DAY:";

    // 用户连续签到的缓存key的前缀
    private static final String CONTINUOUS_CHECK_IN_COUNT_PRE_KEY = "USER_CHECKIN:CONTINUOUS_COUNT:";

    // 日期格式化器
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final StringRedisTemplate stringRedisTemplate;


    /**
     * 用户签到
     * setbit key offset 1
     * setbit USER:CHECKIN:DAY:20230317 userId 1
     *
     * @param userId 用户id
     * @return {@link R}
     */
    @Override
    public R checkIn(Long userId) {
        // 获取当前日期
        String date = LocalDate.now().format(DATE_TIME_FORMATTER);
        String key = getCheckInKey(date);
        // 判断用户是否签到过，如果没有签到就进行签到
        if (isCheckIn(key, userId)) {
            return R.fail("您已经签到过了，无需重复签到");
        }
        // 继续签到
        stringRedisTemplate.opsForValue().setBit(key, userId, true);
        // 更新当前用户连续签到的天数
        updateContinuousCheckIn(userId);
        return R.ok("用户签到成功");
    }


    @Override
    public R<Long> countDateCheckIn(String date) {
        byte[] key = getCheckInKey(date).getBytes();
        Long count = stringRedisTemplate.execute((RedisCallback < Long >) connection -> connection.bitCount(key));
        return R.ok(count);
    }

    @Override
    public R<Long> countCheckIn(Long userId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DATE_TIME_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_TIME_FORMATTER);
        long days = ChronoUnit.DAYS.between(start, end);
        AtomicLong count = new AtomicLong(0);
        Stream.iterate(start, d -> d.plusDays(1)).limit(days + 1).forEach(day -> {
            String key = getCheckInKey(day.format(DATE_TIME_FORMATTER));
            if (stringRedisTemplate.opsForValue().getBit(key, userId)){
                count.incrementAndGet();
            }
        });
        return R.ok(count.get());
    }

    @Override
    public R<Long> getContinuousCheckIn(Long userId) {
        String key = getContinueCheckInKey(userId);
        String days = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(days)) {
            days = "0";
        }
        return R.ok(Long.parseLong(days));
    }


    /**
     * 获取用户是否签到过
     * getbit key offet
     *
     * @param key    缓存key
     * @param userId 用户id
     * @return boolean
     */
    private boolean isCheckIn(String key, Long userId) {
        Boolean isCheckIn = stringRedisTemplate.opsForValue().getBit(key, userId);
        return Optional.ofNullable(isCheckIn).orElse(false);
    }

    /**
     * 更新用户连续签到天数
     *
     * @param userId 用户id
     */
    private void updateContinuousCheckIn(Long userId) {
        // 获取连续签到的key
        String key = getContinueCheckInKey(userId);
        // 获取指定用户的连续签到的天数
        String val = stringRedisTemplate.opsForValue().get(key);
        long count = 0;
        if (Objects.nonNull(val)) {
            count = Long.parseLong(val);
        }
        // 连续签到天数加一
        count++;
        // 更新连续签到天数
        stringRedisTemplate.opsForValue().set(key, count + "");
        // 设置连续签到天数key的过期时间
        // 过期时间需要设置为 后天的凌晨零分零零秒
        LocalDateTime time = LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).withSecond(0);
        stringRedisTemplate.expireAt(key, time.toInstant(ZoneOffset.of("+8")));
        log.info("更新用户连续签到天数");
    }


    /**
     * 获取签到日期的key
     *
     * @param date 日期
     * @return {@link String}
     */
    private String getCheckInKey(String date) {
        return CHECK_IN_PRE_KEY + date;
    }

    /**
     * 获取用户连续签到的缓存key
     *
     * @param userId 用户id
     * @return {@link String}
     */
    private String getContinueCheckInKey(Long userId) {
        return CONTINUOUS_CHECK_IN_COUNT_PRE_KEY + userId;
    }
}
