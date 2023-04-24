package org.example.cloud.neworder.listener;


import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.neworder.dao.TOrderDao;
import org.example.cloud.neworder.dao.TOrderitemDao;
import org.example.cloud.neworder.dao.TOrderlogDao;
import org.example.cloud.neworder.entity.TOrder;
import org.example.cloud.neworder.entity.TOrderitem;
import org.example.cloud.neworder.entity.TOrderlog;
import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.OrderFlag;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.RedisKeyConfig;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudentity.dto.OrderSyncDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
@RabbitListener(queues = RabbitMQConstConfig.Q_ORDER_SYNC)
@RequiredArgsConstructor
public class OrderSyncListener {

    private final TOrderDao orderDao;
    private final TOrderitemDao itemDao;
    private final TOrderlogDao logDao;

    @RabbitHandler
    public void handler(MqMsgBo bo, Channel channel, Message message) throws IOException {
        log.info("消费者开始同步订单到数据库");
        int type = bo.getType();
        if (Objects.equals(RabbitMQConstConfig.MQTYPE_ORDERSYNC, type)) {
            OrderSyncDto dto = (OrderSyncDto) bo.getData();
            // 获取订单编号
            String no = dto.getNo();
            // 根据订单编号从redis中去订单对象
            TOrder order = (TOrder) RedissonUtils.getHash(RedisKeyConfig.ORDER_ADD, no);
            if (orderDao.insert(order) > 0) {
                // 新增订单详情
                TOrderitem item = new TOrderitem();
                item.setGid(dto.getGid());
                item.setNum(dto.getNum());
                item.setOid(order.getId());
                item.setPrice(dto.getPrice());
                itemDao.insert(item);
                // 新增订单流水
                TOrderlog orderLog = new TOrderlog();
                orderLog.setOid(order.getId());
                orderLog.setCreateTime(new Date());
                orderLog.setInfo("新增订单");
                orderLog.setType(OrderFlag.待付款.getCode());
                logDao.insert(orderLog);
                log.info("订单同步到数据库成功");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        }
    }
}
