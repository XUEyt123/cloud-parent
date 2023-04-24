package org.example.cloud.neworder.service;


import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.OrderAddDto;
import org.example.cloudentity.dto.OrderFlagDto;

public interface OrderService {

    // 第一版本的下单
    R save(OrderAddDto dto);

    // 第二版本的下单
    R save2(OrderAddDto dto);

    // 查询我的订单
    R queryMy(int flag);

    // 更改订单状态
    R updateFlag(OrderFlagDto dto);

    // 取消订单
    R cancel(String no);


}
