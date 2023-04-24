package org.example.cloudcoupon.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudcommon.utils.ThreadPoolSingle;
import org.example.cloudcoupon.dao.TUsercouponDao;
import org.example.cloudcoupon.entity.TCouponTemplate;
import org.example.cloudcoupon.entity.TUsercoupon;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Slf4j
@Component
@RequiredArgsConstructor
// 监听系统优惠券的队列
@RabbitListener(queues = RabbitMQConstConfig.Q_COUPONSYS)
public class CouponTemSystemListener {
    private final TUsercouponDao couponDao;

    @RabbitHandler
    public void handler(MqMsgBo msgBo) {
        log.info("消费者开始发放系统优惠券");

        int sendType = msgBo.getType();
        if (Objects.equals(sendType, RabbitMQConstConfig.MQTYPE_COUPONSYS)) {
            TCouponTemplate template = (TCouponTemplate) msgBo.getData();
            if (Objects.equals(template.getTarget(), SystemConfig.COUPON_TARGET_ALL)) {
                List<Integer> userIds = getLevelUsers();
                Integer templateId = template.getId();
                String templateKey = template.getTemplateKey();

                int batchs = userIds.size() / SystemConfig.THREAD_COUPON_BATCH;
                batchs = userIds.size() % SystemConfig.THREAD_COUPON_BATCH == 0 ? batchs : batchs + 1;
                for (int i = 0; i < batchs; i++) {
                    int start = i * SystemConfig.THREAD_COUPON_BATCH;
                    List<Integer> pageIds = userIds.stream().skip(start).limit(SystemConfig.THREAD_COUPON_BATCH).collect(Collectors.toList());
                    List<TUsercoupon> ucs = pageIds.stream().map(id -> new TUsercoupon(templateId, id, templateKey)).collect(Collectors.toList());
                    // 使用线程池处理
                    ThreadPoolSingle.getInstance().poolExecutor.execute(() -> {
                        couponDao.insertBatch(ucs);
                    });
                }
                log.info("系统优惠券发放成功");
            }
        }
    }

    // 5005个用户
    private static List<Integer> getLevelUsers() {
        /*List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 5005; i++) {
            ids.add(i);
        }
        return ids;*/
        return Stream.iterate(1, num -> num + 1).limit(5005).collect(Collectors.toList());
    }

}

