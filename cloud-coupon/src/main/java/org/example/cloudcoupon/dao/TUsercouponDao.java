package org.example.cloudcoupon.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cloudcoupon.entity.TUsercoupon;

import java.util.List;

@Mapper
public interface TUsercouponDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TUsercoupon queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TUsercoupon> queryAll();

    /**
     * 新增数据
     *
     * @param tUsercoupon 实例对象
     * @return 影响行数
     */
    int insert(TUsercoupon tUsercoupon);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TUsercoupon> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TUsercoupon> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TUsercoupon> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TUsercoupon> entities);

    /**
     * 修改数据
     *
     * @param tUsercoupon 实例对象
     * @return 影响行数
     */
    int update(TUsercoupon tUsercoupon);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}


