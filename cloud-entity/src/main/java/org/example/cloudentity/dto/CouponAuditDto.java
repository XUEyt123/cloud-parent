package org.example.cloudentity.dto;

import lombok.Data;

@Data
public class CouponAuditDto {
    // 优惠券模板ID
    private Integer id;
    // 创建人ID 后台内部员工
    private Integer aid;
    // 优惠券状态
    private Integer flag;
    // 审核意见
    private String info;
}

