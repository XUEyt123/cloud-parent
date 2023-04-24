package org.example.cloud.goodsddd.application.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloud.goodsddd.application.inter.GoodsItemService;
import org.example.cloud.goodsddd.domian.GoodsItem;
import org.example.cloud.goodsddd.infra.repo.GoodsItemDao;
import org.example.cloudentity.domain.R;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsItemServiceImpl implements GoodsItemService {

    private final GoodsItemDao itemDao;

    @Override
    public R<List<GoodsItem>> findAll() {
        List<GoodsItem> list = itemDao.findAll();
        return R.ok(list);
    }
}

