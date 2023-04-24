package org.example.cloudcoupon.service;

import com.github.pagehelper.PageInfo;
import org.example.cloudcoupon.entity.TCouponTemplate;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.CouponAddDto;
import org.example.cloudentity.dto.CouponAuditDto;

public interface CouponTemplateService {
    /**
     * 分页查询全部优惠券模板
     *
     * @param pageIndex 页面索引
     * @param pageSize  页面大小
     * @return {@link R}<{@link PageInfo}<{@link TCouponTemplate}>>
     */
    R<PageInfo<TCouponTemplate>> queryAll(Integer pageIndex, Integer pageSize);

    /**
     * 新增优惠券模板
     *
     * @param dto 优惠券模板
     * @return {@link R}
     */
    R save(CouponAddDto dto);

    /**
     * 审核优惠券模板
     *
     * @param dto dto
     * @return {@link R}
     */
    R audit(CouponAuditDto dto);
}

