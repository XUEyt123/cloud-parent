package com.org.example.cloud.goodsddd.infra.repo;

import org.example.cloud.goodsddd.domian.GoodsItem;
import org.example.cloud.goodsddd.infra.repo.GoodsItemDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class GoodsItemDaoTest {

    @Resource
    GoodsItemDao itemDao;

    /**
     * 测试JPA新增方法
     * <p>
     * save: 既可以做新增也可以做更新
     */
    @Test
    void save() {
        GoodsItem item = new GoodsItem();
        item.setName("手机");
        item.setNo(UUID.randomUUID().toString());
        GoodsItem goodsItem = itemDao.save(item);
        System.out.println(goodsItem);
    }

    /**
     * 测试JPA根据ID查询
     */
    @Test
    void findById() {
        Optional<GoodsItem> goodsItem = itemDao.findById(1);
        System.out.println(goodsItem.get());
    }

    @Test
    void update() {
        GoodsItem item = new GoodsItem();
        item.setName("手机类别");
        item.setId(1);
        GoodsItem goodsItem = itemDao.save(item);
        System.out.println(goodsItem);
    }

    @Test
    void delete() {
        itemDao.deleteById(1);
        System.out.println("删除成功");
    }

}

