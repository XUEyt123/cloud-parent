package org.example.cloudcoupon.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcoupon.service.UserCouponService;
import org.example.cloudentity.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("userCoupon")
public class UserCouponController {

    private final UserCouponService service;

    /**
     * 用户领取优惠券
     *
     * @param ctid 优惠券模板ID
     * @param ul   ul用户等级
     * @param uid  uid用户ID
     * @return {@link R}
     */
    @PostMapping("save")
    public R save(Integer ctid, Integer ul, Integer uid) {
        return service.save(ul, ctid, uid);
    }
}
