package org.example.cloud.neworder.service.impl;

import org.example.cloud.neworder.entity.TOrder;
import org.example.cloud.neworder.service.OrderService;
import org.example.cloudapi.GoodsProvider;
import org.example.cloudcommon.config.MqMsgBo;
import org.example.cloudcommon.config.OrderFlag;
import org.example.cloudcommon.config.RabbitMQConstConfig;
import org.example.cloudcommon.config.RedisKeyConfig;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudcommon.utils.SnowFlowUtil;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.*;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单服务实现类
 *
 * @author zed
 * @date 2023/03/20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

//    private final TOrderDao orderDao;
//
//    private final TOrderitemDao itemDao;
//
//    private final TOrderlogDao logDao;

    private final GoodsProvider goodsProvider;

    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    // 分布式事务
    @GlobalTransactional
    public R save(OrderAddDto dto) {
        GoodsDddDto goodsDddDto = null;
        Lock lock = new ReentrantLock();
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                // 远程调用商品服务 根据ID 查询商品对象
                R<GoodsDddDto> result = goodsProvider.single(dto.getGid());
                if (Objects.equals(R.SUCCESS, result.getCode())) {
                    goodsDddDto = result.getData();
                    if (goodsDddDto.getStockNum() > dto.getNum()) {
                        // 远程扣减库存
                        GoodsStockDto stockDto = new GoodsStockDto();
                        // 商品ID
                        stockDto.setId(dto.getGid());
                        // 下单商品数量
                        stockDto.setNum(dto.getNum());
                        goodsProvider.update(stockDto);
                    } else {
                        return R.fail("库存不足，下单失败");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        // 获取锁成功 开始1、下单[订单表+订单详情表] 2、更新库存 3、订单记录表
        TOrder order = new TOrder();
        // 订单编号
        order.setNo(SnowFlowUtil.getInstance().nextId() + "");
        order.setFlag(OrderFlag.待付款.getCode());
        order.setCreateTime(new Date());
        order.setUaid(dto.getUaid());
        order.setUpdateTime(new Date());
        // 需要支付的总金额
        double totalMoney = goodsDddDto.getPrice() * dto.getNum();
        order.setTotalMoney(totalMoney);
        // 优惠金额
        double freeMoney = dto.getScore() / 100D;
        order.setFreeMoney(freeMoney);
        // 需要实际支付的金额
        order.setPayMoney(totalMoney - freeMoney);
        // UID后期可以熊ThreadLocal上下文中获取
        order.setUid(1);
        // 把订单对象放到redis中
        RedissonUtils.setHash(RedisKeyConfig.ORDER_ADD, order.getNo(), order);

        // MQ发送消息给订单的交换机
        OrderSyncDto orderSyncDto = new OrderSyncDto();
        orderSyncDto.setGid(dto.getGid());
        orderSyncDto.setNum(dto.getNum());
        orderSyncDto.setPrice(goodsDddDto.getPrice());
        orderSyncDto.setUcid(dto.getUcid());
        orderSyncDto.setNo(order.getNo());
        MqMsgBo bo = new MqMsgBo(SnowFlowUtil.getInstance().nextId(), RabbitMQConstConfig.MQTYPE_ORDERSYNC, orderSyncDto);
        rabbitTemplate.convertAndSend(RabbitMQConstConfig.EX_ORDERADD, "", bo);

        return R.ok("下单成功");
    }

    @Override
    public R save2(OrderAddDto dto) {
        return null;
    }

    @Override
    public R queryMy(int flag) {
        return null;
    }

    @Override
    public R updateFlag(OrderFlagDto dto) {
        return null;
    }

    @Override
    public R cancel(String no) {
        return null;
    }
}
