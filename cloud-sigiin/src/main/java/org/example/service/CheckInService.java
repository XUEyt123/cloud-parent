package org.example.service;

import org.example.cloudentity.domain.R;

public interface CheckInService {

    /**
     * 签到
     *
     * @param userId 用户id
     * @return {@link R}
     */
    R checkIn(Long userId);

    /**
     * 统计指定日期的签到人数
     *
     * @param date 日期
     * @return {@link R}<{@link Long}>
     */
    R<Long> countDateCheckIn(String date);

    /**
     * 统计指定日期范围内的用户的签到次数
     *
     * @param userId    用户id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return {@link R}<{@link Long}>
     */
    R<Long> countCheckIn(Long userId, String startDate, String endDate);

    /**
     * 获取连续签到的天数
     *
     * @param userId 用户id
     * @return {@link R}<{@link Long}>
     */
    R<Long> getContinuousCheckIn(Long userId);
}

