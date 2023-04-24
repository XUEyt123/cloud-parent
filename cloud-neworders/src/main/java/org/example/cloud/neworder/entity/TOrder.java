package org.example.cloud.neworder.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TOrder implements Serializable {
    private static final long serialVersionUID = -92037699960152516L;

    private Integer id;

    private Integer uid;
    /**
     * 用户收货地址
     */
    private Integer uaid;
    /**
     * 总金额
     */
    private Double totalMoney;
    /**
     * 支付金额
     */
    private Double payMoney;
    /**
     * 优惠金额
     */
    private Double freeMoney;
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * 订单状态
     */
    private Integer flag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 订单编号
     */
    private String no;

}

