package org.example.cloudgoods.service;

public interface GoodsService {


    /**
     * 更新库存
     *
     * @param goodsId    商品id
     * @param goodsStock 库存修改数量
     */

    Boolean updateStock(Integer goodsId, Integer goodsStock);
}
