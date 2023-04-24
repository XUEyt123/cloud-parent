package org.example.cloudentity.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbGoods implements Serializable {

    private static final long serialVersionUID = 1953897223727190878L;
    /**
     * 商品ID
     */
    private Integer goodsId;
    /**
     * 商品库存数量
     */
    private Integer goodsStock;
    /**
     * 商品价格
     */
    private Double goodsPrice;
    /**
     * 商品名称
     */
    private String goodsName;


}
