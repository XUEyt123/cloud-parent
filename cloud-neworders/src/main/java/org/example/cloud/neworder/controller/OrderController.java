package org.example.cloud.neworder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.neworder.service.OrderService;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.OrderAddDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("new/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 第一版本 下单接口
     *
     * @param dto dto
     * @return {@link R}
     */
    @PostMapping("save")
    public R save(@RequestBody OrderAddDto dto) {
        // 添加参数校验
        return orderService.save(dto);
    }
}
