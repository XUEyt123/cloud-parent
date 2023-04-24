package org.example.cloudskill.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cloudskill.entity.TSkilllog;

import java.util.List;

/**
 * 21.秒杀记录表(TSkilllog)表数据库访问层
 *
 * @author zed
 * @since 2023-03-21 11:22:19
 */
@Mapper
public interface TSkilllogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TSkilllog queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TSkilllog> queryAll();

    /**
     * 新增数据
     *
     * @param tSkilllog 实例对象
     * @return 影响行数
     */
    int insert(TSkilllog tSkilllog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TSkilllog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TSkilllog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TSkilllog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TSkilllog> entities);

    /**
     * 修改数据
     *
     * @param tSkilllog 实例对象
     * @return 影响行数
     */
    int update(TSkilllog tSkilllog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

