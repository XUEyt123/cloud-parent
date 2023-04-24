package org.example.cloud.neworder.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.cloud.neworder.entity.TOrder;

import java.util.List;

@Mapper
public interface TOrderDao {

    /**
     * 更新订单状态
     *
     * @param id   订单id
     * @param flag 订单状态
     * @return int 修改了几行
     */
    @Update("update t_order set flag=${flag} where id=${id}")
    int updateFlag(@Param("id") Integer id, @Param("flag") Integer flag);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TOrder queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TOrder> queryAll();

    /**
     * 新增数据
     *
     * @param tOrder 实例对象
     * @return 影响行数
     */
    int insert(TOrder tOrder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TOrder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TOrder> entities);

    /**
     * 修改数据
     *
     * @param tOrder 实例对象
     * @return 影响行数
     */
    int update(TOrder tOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    TOrder queryByNo(String no);
}
