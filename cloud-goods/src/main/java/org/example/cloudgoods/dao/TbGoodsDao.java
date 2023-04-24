package org.example.cloudgoods.dao;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.cloudentity.domain.TbGoods;

import java.util.List;

@Mapper
public interface TbGoodsDao {

    /**
     * 更新库存
     *
     * @param goodsId  商品id
     * @param goodsNum 下单数量[可以是正的也可以是负的]
     * @return int
     */
    int updateStock(@Param("goodsId") Integer goodsId,@Param("goodsNum") Integer goodsNum);

    /**
     * 通过ID查询单条数据
     *
     * @param goodsId 主键
     * @return 实例对象
     */
    TbGoods queryById(Integer goodsId);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TbGoods> queryAll();

    /**
     * 新增数据
     *
     * @param tbGoods 实例对象
     * @return 影响行数
     */
    int insert(TbGoods tbGoods);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbGoods> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbGoods> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbGoods> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<TbGoods> entities);

    /**
     * 修改数据
     *
     * @param tbGoods 实例对象
     * @return 影响行数
     */
    int update(TbGoods tbGoods);

    /**
     * 通过主键删除数据
     *
     * @param goodsId 主键
     * @return 影响行数
     */
    int deleteById(Integer goodsId);

}


