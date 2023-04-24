package org.example.cloudentity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CouponAddDto {

    @NotEmpty(message = "优惠券名称不能为空")
    private String name;
    @NotEmpty(message = "优惠券LOGO不能为空")
    private String logo;
    @NotEmpty(message = "优惠券简介不能为空")
    private String intro;
    @NotNull(message = "优惠券名称不能为空")
    private Integer scope;
    private Integer scopeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expireTime;
    private Integer couponCount;
    private Integer target;
    private Integer targetLevel;
    private Integer sendType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    private double limitmoney;
    private double discount;
}

