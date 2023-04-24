package org.example.cloudskill.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.cloudskill.dto.SkillGoodsDetailDto;
import org.example.cloudskill.entity.TSkillgoods;

import java.util.List;

/**
 * 18.秒杀商品表(TSkillgoods)表数据库访问层
 *
 * @author zed
 * @since 2023-03-21 11:22:18
 */
@Mapper
public interface TSkillgoodsDao {


    /**
     * 通过活动ID查询秒杀商品
     *
     * @param said 说
     * @return {@link List}<{@link TSkillgoods}>
     */
    List<TSkillgoods> findBySaid(Integer said);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TSkillgoods queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TSkillgoods> queryAll();

    /**
     * 新增数据
     *
     * @param tSkillgoods 实例对象
     * @return 影响行数
     */
    int insert(TSkillgoods tSkillgoods);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TSkillgoods> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TSkillgoods> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TSkillgoods> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TSkillgoods> entities);

    /**
     * 修改数据
     *
     * @param tSkillgoods 实例对象
     * @return 影响行数
     */
    int update(TSkillgoods tSkillgoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
    // 根据活动商品的ID获取商品详情
    SkillGoodsDetailDto findGoodsDetailDTOById(Integer id);

    @Update("UPDATE t_skillgoods set stock=stock+#{num} where id=#{sgid}")
    void updateStock(@Param("sgid") Integer sgid, @Param("num") Integer num);


}

