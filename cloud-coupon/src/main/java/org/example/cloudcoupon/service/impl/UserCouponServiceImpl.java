package org.example.cloudcoupon.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcommon.config.RedisKeyConfig;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudcommon.utils.SnowFlowUtil;
import org.example.cloudcoupon.dao.TUsercouponDao;
import org.example.cloudcoupon.entity.TUsercoupon;
import org.example.cloudcoupon.service.UserCouponService;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.UserCouponDto;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService {

    private final TUsercouponDao couponDao;

    @Override
    public R query(int status) {
        return null;
    }

    @Override
    public R<UserCouponDto> detail(int id) {
        return null;
    }

    /**
     * 用户领取优惠券的方法
     *
     * @param ul   用户级别
     * @param ctid couponTemplateId 优惠券模板ID
     * @param uid  用户ID
     * @return {@link R}
     */
    @Override
    public R save(int ul, int ctid, int uid) {
        // 查询优惠券活动是否结束
        String key = RedisKeyConfig.COUPON_CACHE + ctid;
        if (RedissonUtils.checkKey(key)) {
            // 活动还没结束  校验用户是否领过该优惠券--查询数据库，查询Redis
            boolean meiLingQu = true; // 该用户是否领取过
            boolean diYiGeRenLing = false;
            // RedisKeyConfig.COUPON_USERS + ctid 这个key的value类型是set集合 里面保存的是领取过优惠券的用户ID
            if (RedissonUtils.checkKey(RedisKeyConfig.COUPON_USERS + ctid)) {
                meiLingQu = !RedissonUtils.exists(RedisKeyConfig.COUPON_USERS + ctid, uid + "");
            } else {
                diYiGeRenLing = true;//第一次有人领取这个模板的优惠券
            }
            if (meiLingQu) {
                // 下一步 要校验 用户等级够不够领这个优惠券 优惠券数量够不够
                // 获取分布式锁
                RLock lock = RedissonUtils.getLock(RedisKeyConfig.COUPON_LOCK + ctid);
                try {
                    if (lock.tryLock(RedisKeyConfig.COUPON_LOCK_TIME, TimeUnit.SECONDS)) {
                        //3.验证数量是否足够
                        int count = (int) RedissonUtils.getList(key, 0);
                        if (count > 0) {
                            // 优惠券还有 下一步校验用户等级够不够
                            int level = (int) RedissonUtils.getList(key, 1);
                            if (ul >= level) {
                                // 用户等级够领取
                                // 到用户优惠券表中新增记录
                                TUsercoupon uc = new TUsercoupon(ctid, uid, SnowFlowUtil.getInstance().nextId() + "");
                                if (couponDao.insert(uc) > 0) {
                                    // 更新list里的优惠券数量
                                    RedissonUtils.setList(key, count--);
                                    // 更新set 把用户ID放入领取过优惠券的Redis set集合中
                                    RedissonUtils.setSet(RedisKeyConfig.COUPON_USERS + ctid, uid + "");
                                    // 如果是第一次设置这个key 需要设置过期时间
                                    if (diYiGeRenLing) {
                                        RedissonUtils.expire(RedisKeyConfig.COUPON_USERS + ctid, RedissonUtils.ttl(key) / 1000);
                                    }
                                    return R.ok("用户领取优惠券成功");
                                }
                            } else {
                                return R.fail("你什么档次，也能来领优惠券?");
                            }
                        } else {
                            return R.fail("亲,你来晚了，优惠券领完了！");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        } else {
            return R.fail("亲，活动已经结束了，下次别来了");
        }
        return R.fail("系统故障，稍后再领");
    }

    @Override
    public R update(int id, int flag) {
        return null;
    }
}
