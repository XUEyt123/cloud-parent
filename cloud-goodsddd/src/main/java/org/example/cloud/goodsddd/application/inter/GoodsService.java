package org.example.cloud.goodsddd.application.inter;

import org.example.cloud.goodsddd.domian.Goods;
import org.example.cloudentity.domain.R;

public interface GoodsService {
    // 查询全部商品
    R queryAll();

    // 查询单个商品详情
    R<Goods> queryOne(Integer id);

    // 修改库存
    R update(GoodsStockDto dto);
}

