package org.example.cloudskill.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.cloudskill.entity.TSkillactivity;

/**
 * 17.秒杀活动表(TSkillactivity)表数据库访问层
 *
 * @author zed
 * @since 2023-03-21 11:22:14
 */
@Mapper
public interface TSkillactivityDao extends BaseMapper<TSkillactivity> {

}

