package org.example.cloudskill.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudskill.dao.TSkillgoodsDao;
import org.example.cloudskill.dao.TSkillorderDao;
import org.example.cloudskill.dao.TSkillorderlogDao;
import org.example.cloudskill.entity.TSkillorder;
import org.example.cloudskill.entity.TSkillorderlog;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description: 监听 队列消息 延迟消息 1分钟
 * @Author: zed
 * @Date: 2022/5/19 10:19
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = RabbitMQConstConfig.Q_SKILLORDER_TIMEOUT)
public class OrderTimeOutListener {

    private final TSkillorderDao dao;
    private final TSkillorderlogDao skillorderlogDao;
    private final TSkillgoodsDao skillgoodsDao;

    @RabbitHandler
    @Transactional
    public void handler(MqMsgBo bo) {
        log.info("开始校验秒杀订单超时数据");
        //1.查询数据库
        TSkillorder order = (TSkillorder) bo.getData();
        List<TSkillorder> skillOrders = dao.queryByUidAndSgid(order.getUid(), order.getSgid());

        if (CollectionUtils.isEmpty(skillOrders)) {
            // 校验是否为未付款订单
            if (order.getFlag() == SystemConfig.SKILL_ORDER_ADD) {
                // 取消订单
                TSkillorder updateOrder = new TSkillorder();
                updateOrder.setId(order.getId());
                updateOrder.setFlag(SystemConfig.SKILL_ORDER_OUT);
                if (dao.update(updateOrder) > 0) {
                    // 库存释放
                    skillgoodsDao.updateStock(order.getSgid(), order.getNum());
                    // 日志
                    skillorderlogDao.insert(new TSkillorderlog(order.getId(), SystemConfig.SKILL_ORDER_OUT, "秒杀订单超时未支付！"));
                    log.info("结束校验秒杀订单超时数据");
                } else {
                    log.info("订单超时，修改数据库状态！");
                }
            } else {
                log.info("订单没有超时");
            }
        }
    }
}
