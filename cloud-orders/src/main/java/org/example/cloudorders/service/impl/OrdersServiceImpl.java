package org.example.cloudorders.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudapi.GoodsApi;
import org.example.cloudcommon.utils.RedissonUtils;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.domain.TbOrder;
import org.example.cloudorders.dao.TbOrderDao;
import org.example.cloudorders.service.OrdersService;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final TbOrderDao orderDao;

    private final GoodsApi goodsApi;

    /**
     * 保存订单
     *
     * @param order 订单
     * @return {@link R}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    // 开启全局事务
    @GlobalTransactional
    public R saveOrder(TbOrder order) {
        RLock lock = RedissonUtils.getLock("cloud-order:save:" + order.getGoodsId());
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                // 新增订单记录
                if (orderDao.insert(order) > 0) {
                    log.info("订单添加成功");
                    // 新增成功后 要扣减商品库存 [需要远程调用商品服务]
                    log.info("远程调用产品服务，更新商品库存，商品ID是{},修改库存{}", order.getGoodsId(), order.getOrderNum());
                    R res = goodsApi.updateStock(order.getGoodsId(), -order.getOrderNum());

                    // 如果下单业务中出现异常 模拟出现异常的情况
                    // int a = 2 / 0;

                    if (Objects.equals(res.getCode(), R.SUCCESS)) {
                        return R.ok("下单成功");
                    }
            }
        } else {
                return R.fail("获取锁失败");
            }
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
        return R.fail("下单失败");
    }
}

