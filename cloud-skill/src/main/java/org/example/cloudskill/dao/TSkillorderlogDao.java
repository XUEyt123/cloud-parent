package org.example.cloudskill.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cloudskill.entity.TSkillorderlog;

import java.util.List;

/**
 * 20.秒杀订单流水表(TSkillorderlog)表数据库访问层
 *
 * @author zed
 * @since 2023-03-21 11:22:21
 */
@Mapper
public interface TSkillorderlogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TSkillorderlog queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TSkillorderlog> queryAll();

    /**
     * 新增数据
     *
     * @param tSkillorderlog 实例对象
     * @return 影响行数
     */
    int insert(TSkillorderlog tSkillorderlog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TSkillorderlog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TSkillorderlog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TSkillorderlog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TSkillorderlog> entities);

    /**
     * 修改数据
     *
     * @param tSkillorderlog 实例对象
     * @return 影响行数
     */
    int update(TSkillorderlog tSkillorderlog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

