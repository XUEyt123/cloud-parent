package org.example.cloudskill.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudskill.dao.TSkilllogDao;
import org.example.cloudskill.dao.TSkillorderDao;
import org.example.cloudskill.dao.TSkillorderlogDao;
import org.example.cloudskill.entity.TSkilllog;
import org.example.cloudskill.entity.TSkillorder;
import org.example.cloudskill.entity.TSkillorderlog;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @Description: 订单同步操作
 * @Author: zed
 * @Date: 2022/5/19 10:19
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = RabbitMQConstConfig.Q_SKILLORDER_SYNC)
public class OrderSyncListener {

    private final TSkillorderDao dao;
    private final TSkilllogDao logDao;
    private final TSkillorderlogDao skillorderlogDao;

    @RabbitHandler
    @Transactional
    public void handler(MqMsgBo bo) {
        log.info("开始同步秒杀订单数据");
        //1.数据同步
        TSkillorder order = (TSkillorder) bo.getData();
        if (Objects.nonNull(order)) {
            // 新增订单
            if (dao.insert(order) > 0) {
                skillorderlogDao.insert(new TSkillorderlog(order.getId(), SystemConfig.SKILL_ORDER_ADD, "秒杀订单生成！"));
                logDao.insert(new TSkilllog(order.getUid(), order.getSgid(), "1"));//记录秒杀结果
                log.info("秒杀下单成功");
            } else {
                log.info("订单新增数据库失败！");
            }
        } else {
            log.info("订单同步，消息内容为空！");
        }
    }
}
