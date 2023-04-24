package org.example.cloudentity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsDddDto {

    private Integer id;
    private Integer itemId;
    private String title;
    private String promoWords;
    private String smallPic;
    private Double price;
    private Date createTime;
    private Integer status;
    private Integer stockNum;
}
