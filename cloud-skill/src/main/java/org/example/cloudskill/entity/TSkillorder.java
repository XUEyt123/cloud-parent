package org.example.cloudskill.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

/**
 * 19.秒杀订单表(TSkillorder)实体类
 *
 * @author zed
 * @since 2023-03-21 11:22:20
 */
@Data
public class TSkillorder implements Serializable {

    private static final long serialVersionUID = -2366847253658010489L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 订单号
     */
    private String no;
    /**
     * 商品id
     */
    private Integer sgid;
    /**
     * 价格
     */
    private Double price;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 状态
     */
    private Integer flag;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 收货地址
     */
    private Integer uaid;
    /**
     * 用户id
     */
    private Integer uid;
    public TSkillorder() {
    }

    public TSkillorder(String no, Integer sgid, Double price, Integer num, Integer uaid, Integer uid) {
        this.no = no;
        this.sgid = sgid;
        this.price = price;
        this.num = num;
        this.uaid = uaid;
        this.uid = uid;
    }


}

