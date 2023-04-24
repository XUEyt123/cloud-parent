package org.example.cloud.goodsddd.infra.repo;

import org.example.cloud.goodsddd.domian.GoodsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsItemDao extends JpaRepository<GoodsItem,Integer> {
}

