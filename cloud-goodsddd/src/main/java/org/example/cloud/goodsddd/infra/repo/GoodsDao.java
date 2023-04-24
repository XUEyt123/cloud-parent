package org.example.cloud.goodsddd.infra.repo;

import org.example.cloud.goodsddd.domian.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsDao extends JpaRepository<Goods, Integer> {

    /**
     * 查询全部商品
     * from Goods 不是SQL语句  这个叫做JPQL语句【JPA Query Language】
     * 它查询的不是表和字段 而是【类和属性】
     */
    @Query("from Goods")
    List<Goods> selectAll();

    // 方法名解析查询:按照约定，编写方法名，就会自动生成SQL语句
    Optional<Goods> findById(Integer id);

    // 更具商品名称模糊查询
    List<Goods> getByTitleLike(String title);

    // 原生SQL语句：根据ID更新商品库存
    @Modifying // DML语句添加这个注解
    // 原生SQL查询中 占位符使用:参数名
    @Query(value = "update t_goods set stock_num=stock_num+:num where id=:id",nativeQuery = true)
    @Transactional
    int updateStock(Integer num,Integer id);
}

