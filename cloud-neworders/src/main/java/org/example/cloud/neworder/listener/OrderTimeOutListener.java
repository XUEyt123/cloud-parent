package org.example.cloud.neworder.listener;


import com.rabbitmq.client.Channel;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.neworder.dao.TOrderDao;
import org.example.cloud.neworder.dao.TOrderitemDao;
import org.example.cloud.neworder.dao.TOrderlogDao;
import org.example.cloud.neworder.entity.TOrder;
import org.example.cloud.neworder.entity.TOrderlog;
import org.example.cloudapi.GoodsProvider;
import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.OrderFlag;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.RedisKeyConfig;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudentity.dto.GoodsStockDto;
import org.example.cloudentity.dto.OrderDetailDto;
import org.example.cloudentity.dto.OrderSyncDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Description: 监听 队列消息 延迟消息 30分钟
 * @Author: zed
 * @Date: 2022/5/19 10:19
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = RabbitMQConstConfig.Q_ORDER_TIMEOUT)
public class OrderTimeOutListener {

    private final TOrderDao orderDao;
    private final TOrderitemDao orderitemDao;
    private final TOrderlogDao orderlogDao;
    private final GoodsProvider goodsProvider;

    @RabbitHandler
    @Transactional
    @GlobalTransactional
    public void handler(MqMsgBo msgBo, Channel channel, Message message) throws IOException {
        // 超时的订单会进入这里
        log.info("处理超时的订单...");
        if (msgBo.getType() == RabbitMQConstConfig.MQTYPE_ORDERSYNC) {
            OrderSyncDto orderSyncDto = (OrderSyncDto) msgBo.getData();
            // 获取到订单编号
            String no = orderSyncDto.getNo();
            TOrder order = orderDao.queryByNo(no);
            if (order.getFlag() == OrderFlag.待付款.getCode()) {
                // 修改订单状态
                orderDao.updateFlag(order.getId(), OrderFlag.已超时.getCode());
                // 释放库存 [加库存]
                List<OrderDetailDto> detailDtos = orderitemDao.queryOrderDetailsByOid(order.getId());
                // 修改每一个订单项中商品的库存
                detailDtos.forEach(dto -> goodsProvider.update(new GoodsStockDto(dto.getGid(), -dto.getNum())));
                // 添加一条订单状态的记录
                TOrderlog orderLog = new TOrderlog();
                orderLog.setCreateTime(new Date());
                orderLog.setOid(order.getId());
                orderLog.setInfo("订单超时取消");
                orderLog.setType(OrderFlag.已超时.getCode());
                // 新增到数据库中
                orderlogDao.insert(orderLog);
                // 清除redis中的field
                RedissonUtils.delField(RedisKeyConfig.ORDER_ADD, no);
                // 手动ACK
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                log.info("取消订单成功");
            } else {
                log.info("取消订单失败");
            }
        }
    }
}
