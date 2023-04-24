package org.example.cloudcoupon.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.RedisKeyConfig;
import org.example.cloudcommon.utils.DateUtil;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudcoupon.entity.TCouponTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = RabbitMQConstConfig.Q_COUPONUSE)
public class CouponTemUserListener {

    @RabbitHandler
    public void handler(MqMsgBo bo) {
        // 用户级别优惠券的 派发
        // 优惠券模板审核通过后 优惠券下发到Redis中 后续等待用户从Redis中领取
        int mqType = bo.getType();
        if (Objects.equals(mqType, RabbitMQConstConfig.MQTYPE_COUPONUSE)) {
            TCouponTemplate template = (TCouponTemplate) bo.getData();
            Integer templateId = template.getId();
            String key = RedisKeyConfig.COUPON_CACHE + templateId;
            // 优惠券数量
            RedissonUtils.setList(key, template.getCouponCount());
            // 可以领取优惠券的用户级别
            RedissonUtils.setList(key, template.getTargetLevel());
            // 这个缓存key的过期时间应该跟优惠券模板的领取 结束时间一致
            RedissonUtils.expire(key, DateUtil.lastSeconds(template.getExpireTime()));
            log.info("用户优惠券发放成功!");
        }

    }
}

