package org.example.cloudcoupon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudcoupon.service.CouponTemplateService;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.CouponAddDto;
import org.example.cloudentity.dto.CouponAuditDto;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("coupon")
public class CouponTemController {
    private final CouponTemplateService templateService;

    /**
     * 新增优惠券模板
     *
     * @param dto dto
     * @return {@link R}
     */
    @PostMapping("save")
    public R save(@RequestBody @Valid CouponAddDto dto) {
        return templateService.save(dto);
    }

    /**
     * 分页查询优惠券模板
     *
     * @param page 页面
     * @param size 大小
     * @return {@link R}
     */
    @PostMapping("page")
    public R findByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return templateService.queryAll(page, size);
    }

    // 优惠券审核接口
    @PostMapping("audit")
    public R audit(@RequestBody CouponAuditDto dto) {
        Assert.notNull(dto.getId(),"ID不能为空");
        return templateService.audit(dto);
    }
}
