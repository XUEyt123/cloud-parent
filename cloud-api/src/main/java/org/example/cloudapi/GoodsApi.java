package org.example.cloudapi;

import org.example.cloudentity.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cloud-goods")
@RequestMapping("goods")
public interface GoodsApi {
    @PostMapping("update")
    R updateStock(@RequestParam Integer goodsId, @RequestParam Integer goodsNum);
}
