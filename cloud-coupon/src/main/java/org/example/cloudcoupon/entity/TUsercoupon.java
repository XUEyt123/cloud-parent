package org.example.cloudcoupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.cloudcommon.config.SystemConfig;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
public class TUsercoupon implements Serializable {
    private static final long serialVersionUID = 4086999370455008231L;
    private Integer id;
    /**
     * 优惠券模板ID
     */
    private Integer templateId;
    /**
     * 前端用户ID
     */
    private Integer userId;
    /**
     * 优惠券码
     */
    private String couponCode;
    /**
     * 优惠券分发时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date assignDate;
    /**
     * 优惠券状态
     */
    private Integer status;

    // 在构造方法中 把固定的属性直接赋值
    public TUsercoupon(Integer templateId, Integer userId, String couponCode) {
        this.templateId = templateId;
        this.userId = userId;
        this.couponCode = couponCode;
        this.assignDate=new Date();
        this.status= SystemConfig.USER_COUPON_NO;
    }

}
