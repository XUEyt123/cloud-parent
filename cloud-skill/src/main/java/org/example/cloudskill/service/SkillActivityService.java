package org.example.cloudskill.service;


import org.example.cloudentity.domain.R;
import org.example.cloudskill.dto.SkillActivityAddDto;
import org.example.cloudskill.dto.SkillActivityAuditDto;

/**
 * 秒杀活动业务层接口
 *
 * @author zed
 * @date 2023/03/21
 */
public interface SkillActivityService {

    /**
     * 添加秒杀活动
     *
     * @param dto 新增秒杀活动dto对象
     * @return {@link R}
     */
    R save(SkillActivityAddDto dto);

    /**
     * 审核秒杀活动
     *
     * @param dto 秒杀活动的审核dto
     * @return {@link R}
     */
    R change(SkillActivityAuditDto dto);

    /**
     * 根据状态查询秒杀活动列表
     *
     * @param flag 秒杀活动状态[未开始，进行中...]
     * @return {@link R}
     */
    R queryList(int flag);

    /**
     * 查询秒杀活动倒计时
     *
     * @param id 秒杀活动id
     * @return {@link R}
     */
    R queryTime(int id);
}
