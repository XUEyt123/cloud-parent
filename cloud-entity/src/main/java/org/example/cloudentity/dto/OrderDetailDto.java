package org.example.cloudentity.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
    private Integer id;
    private Integer oid;
    private Integer gid;
    private Integer status;
    private String title;
    private String smallPic;
    private Double price;//订单中商品价格
    private Integer num;//数量
}
