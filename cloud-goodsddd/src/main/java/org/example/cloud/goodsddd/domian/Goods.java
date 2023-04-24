package org.example.cloud.goodsddd.domian;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity //标识 映射类
@Table(name = "t_goods")//设置表名
public class Goods {
    @Id
    // GeneratedValue 表中主键的生成策略
    @GeneratedValue(strategy = GenerationType.AUTO)
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

