package org.example.cloud.goodsddd.gate.rest;

import lombok.RequiredArgsConstructor;
import org.example.cloud.goodsddd.application.inter.GoodsItemService;
import org.example.cloudentity.domain.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server/goodsitem/")
@RequiredArgsConstructor
public class GoodsItemController {

    private final GoodsItemService service;

    @GetMapping("all")
    public R all() {
        return service.findAll();
    }
}


