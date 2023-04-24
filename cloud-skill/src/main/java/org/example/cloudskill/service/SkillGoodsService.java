package org.example.cloudskill.service;


import org.example.cloudentity.domain.R;
import org.example.cloudskill.dto.SkillGoodsAddDto;
import org.example.cloudskill.dto.SkillGoodsDto;

/**
 * 秒杀商品业务层接口
 *
 * @author zed
 * @date 2023/03/22
 */
public interface SkillGoodsService {

    /**
     * 新增秒杀商品
     *
     * @param dto dto
     * @return {@link R}
     */
    R save(SkillGoodsAddDto dto);

    /**
     * 秒杀商品的上架
     *
     * @param dto dto
     * @return {@link R}
     */
    R up(SkillGoodsDto dto);

    /**
     * 根据秒杀活动ID查询参与这个秒杀活动的商品列表
     *
     * @param said 说
     * @return {@link R}
     */
    R queryList(int said);
}
