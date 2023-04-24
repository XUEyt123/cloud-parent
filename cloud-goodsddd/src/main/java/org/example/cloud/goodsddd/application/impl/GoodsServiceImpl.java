package com.qf.cloud.goodsddd.application.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.goodsddd.application.inter.GoodsService;
import org.example.cloud.goodsddd.domian.Goods;
import org.example.cloud.goodsddd.infra.repo.GoodsDao;
import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.GoodsDddDto;
import org.example.cloudentity.dto.GoodsStockDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsDao dao;

    @Override
    public R queryAll() {
        return R.ok(dao.findAll());
    }

    @Override
    public R<GoodsDddDto> queryOne(Integer id) {
        Goods goods = dao.findById(id).orElse(null);
        GoodsDddDto dto = new GoodsDddDto();
        BeanUtils.copyProperties(goods,dto);
        return R.ok(dto);
    }

    @Override
    @Transactional
    public R update(GoodsStockDto dto) {
        log.info("修改{}的库存，扣减{}库存", dto.getId(), dto.getNum());
        if (dao.updateStock(-dto.getNum(), dto.getId()) > 0) {
            return R.ok("库存修改成功");
        }
        return R.fail("库存修改失败");
    }
}
