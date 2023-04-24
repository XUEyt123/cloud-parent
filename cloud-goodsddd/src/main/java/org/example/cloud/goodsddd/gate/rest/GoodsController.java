package org.example.cloud.goodsddd.gate.rest;


import lombok.RequiredArgsConstructor;
import org.example.cloud.goodsddd.application.inter.GoodsService;
import org.example.cloud.goodsddd.domian.Goods;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.GoodsStockDto;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制器
 *
 * @author zed
 * @date 2022/9/28
 */
@RestController
@RequestMapping("/service/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService service;

    @GetMapping("all")
    public R all() {
        return service.queryAll();
    }

    @GetMapping("single")
    public R<Goods> single(@RequestParam int id) {
        return service.queryOne(id);
    }

    @PostMapping("update")
    public R update(@RequestBody GoodsStockDto dto) {
        return service.update(dto);
    }
}


