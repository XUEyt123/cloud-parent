package org.example.cloudcoupon.service;

import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.UserCouponDto;

/**
 * 用户优惠券服务接口
 *
 * @author zed
 * @date 2023/03/16
 */
public interface UserCouponService {

    // 根据优惠券状态查询，用户Id从请求头中获取
    R query(int status);

    // 根据ID查询用户优惠券
    R<UserCouponDto> detail(int id);

    // 用户领取优惠券 ul：用户等级 ctid：优惠券模板ID
    R save(int ul, int ctid, int uid);

    // 更新优惠券状态
    R update(int id, int flag);
}

