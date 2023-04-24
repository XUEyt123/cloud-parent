package org.example.cloudorders.service;

import org.example.cloudentity.domain.R;
import org.example.cloudentity.domain.TbOrder;

public interface OrdersService {

    /**
     * 保存订单
     *
     * @param order 订单
     * @return {@link R}
     */
    R saveOrder(TbOrder order);
}

