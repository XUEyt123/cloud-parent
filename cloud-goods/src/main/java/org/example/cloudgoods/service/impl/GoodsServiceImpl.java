package org.example.cloudgoods.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.cloudgoods.dao.TbGoodsDao;
import org.example.cloudgoods.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final TbGoodsDao goodsDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStock(Integer goodsId, Integer goodsStock) {
        return goodsDao.updateStock(goodsId, goodsStock) > 0;
    }
}

