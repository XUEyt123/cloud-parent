package org.example.cloudentity.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbOrder implements Serializable {
    private static final long serialVersionUID = -7639955659914285039L;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     *下单数量
     */
    private Integer orderNum;
    /**
     * 订单的总金额
     */
    private Double orderAmount;
    /**
     * 商品的ID
     */
    private Integer goodsId;

}
