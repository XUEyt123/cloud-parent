package org.example.cloudapi;

import org.example.cloudentity.domain.R;
import org.example.cloudentity.dto.GoodsDddDto;
import org.example.cloudentity.dto.GoodsStockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("cloud-goodsddd")
@RequestMapping("/service/goods")
public interface GoodsProvider {

    @GetMapping("single")
    R<GoodsDddDto> single(@RequestParam int id);

    @PostMapping("update")
    R update(@RequestBody GoodsStockDto dto);
}

