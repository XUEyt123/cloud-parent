package org.example.cloud.goodsddd.application.inter;

import org.example.cloud.goodsddd.domian.GoodsItem;
import org.example.cloudentity.domain.R;

import java.util.List;

/**
 * 商品分类服务接口
 *
 * @author zed
 * @date 2023/03/20
 */
public interface GoodsItemService {

    /**
     * 查询全部商品分类
     */
    R<List<GoodsItem>> findAll();
}
