package org.example.cloudskill.dto;

import lombok.Data;

@Data
public class SkillOrderAddDto {
    private Integer sgid;//秒杀商品id
    private Integer said;//秒杀活动id
    private Integer maxcount;//限购
    private double price;//价格
    private Integer uaid;//收货地址
    private Integer num;//购买数量
}
