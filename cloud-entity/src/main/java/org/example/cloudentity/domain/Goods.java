package org.example.cloudentity.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Goods {
    // 商品ID
    private Integer id;
    //商品名称
    private String name;
    // 商品价格
    private Integer price;

}
