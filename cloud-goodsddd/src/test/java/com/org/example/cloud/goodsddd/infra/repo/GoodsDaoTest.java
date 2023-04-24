package com.org.example.cloud.goodsddd.infra.repo;

import org.example.cloud.goodsddd.domian.Goods;
import org.example.cloud.goodsddd.infra.repo.GoodsDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class GoodsDaoTest {

    @Resource
    GoodsDao dao;

    @Test
    void selectAll() {
        List<Goods> goods = dao.selectAll();
        goods.forEach(System.out::println);
    }

    @Test
    void save() {
        Goods goods = new Goods();
        goods.setItemId(1);
        goods.setTitle("OPPO-Reno9手机");
        goods.setPrice(2999D);
        goods.setStatus(1); //上架或下架状态
        goods.setCreateTime(new Date());
        goods.setPromoWords("OPPO手机充电五分钟通话两小时");
        goods.setStockNum(100);
        goods.setSmallPic("http://i.heyige.cn/images/logo.png");

        Goods save = dao.save(goods);
        System.out.println(save);
    }

    @Test
    void findById() {
        Optional<Goods> goods = dao.findById(1);
        System.out.println(goods.get());
    }

    @Test
    void getByTitleLike() {
        List<Goods> list = dao.getByTitleLike("%OPPO%");
        list.forEach(System.out::println);
    }

    @Test
    void updateStock() {
        int stock = dao.updateStock(-2, 2);
        System.out.println(stock);
    }
}

