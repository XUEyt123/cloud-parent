package org.example.cloudgoods.controller;


import lombok.RequiredArgsConstructor;
import org.example.cloudentity.domain.Goods;
import org.example.cloudentity.domain.R;
import org.example.cloudgoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService service;

    @Value("${server.port}")
    String port;

    @GetMapping("/{id}")
    public Object findGoodsById(@PathVariable("id") Integer id) {
        System.out.println("当前服务运行了:"+port);
        return new Goods(id, "雪铁龙C6", 120000);
    }
    /**
     * 更新库存
     *
     * @param goodsId  商品id
     * @param goodsNum 库存修改数量
     * @return {@link R}
     */
    @PostMapping("update")
    public R updateStock(@RequestParam Integer goodsId, @RequestParam Integer goodsNum) {
        if (service.updateStock(goodsId, goodsNum)) {
            return R.ok();
        }
        return R.fail();
    }


}
