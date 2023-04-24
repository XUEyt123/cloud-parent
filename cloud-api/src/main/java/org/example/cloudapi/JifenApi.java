package org.example.cloudapi;



import org.example.cloudentity.domain.Jifen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Map;

@FeignClient("cloud-jifen")
@RequestMapping("jifen")
public interface JifenApi {

    @PostMapping("save")
    Map<String, Object> save(@RequestBody Jifen jifen);
}
