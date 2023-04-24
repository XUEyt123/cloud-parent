package org.example.cloudorders.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cloudentity.domain.TbOrder;

import java.util.List;

@Mapper
public interface TbOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    TbOrder queryById(String orderId);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TbOrder> queryAll();

    /**
     * 新增数据
     *
     * @param tbOrder 实例对象
     * @return 影响行数
     */
    int insert(TbOrder tbOrder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbOrder> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<TbOrder> entities);

    /**
     * 修改数据
     *
     * @param tbOrder 实例对象
     * @return 影响行数
     */
    int update(TbOrder tbOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 影响行数
     */
    int deleteById(String orderId);

}


