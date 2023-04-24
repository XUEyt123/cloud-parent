package org.example.cloud.neworder.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cloud.neworder.entity.TOrderitem;
import org.example.cloudentity.dto.OrderDetailDto;

import java.util.List;

@Mapper
public interface TOrderitemDao {

    /**
     * 通过订单ID查询订单详情列表
     *
     * @param oid oid
     * @return {@link List}<{@link OrderDetailDto}>
     */
    List<OrderDetailDto> queryOrderDetailsByOid(Integer oid);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TOrderitem queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TOrderitem> queryAll();

    /**
     * 新增数据
     *
     * @param tOrderitem 实例对象
     * @return 影响行数
     */
    int insert(TOrderitem tOrderitem);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TOrderitem> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TOrderitem> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TOrderitem> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TOrderitem> entities);

    /**
     * 修改数据
     *
     * @param tOrderitem 实例对象
     * @return 影响行数
     */
    int update(TOrderitem tOrderitem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

