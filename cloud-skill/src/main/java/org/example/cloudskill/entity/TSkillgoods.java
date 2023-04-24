package org.example.cloudskill.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

/**
 * 18.秒杀商品表(TSkillgoods)实体类
 *
 * @author zed
 * @since 2023-03-21 11:22:19
 */
@Data
public class TSkillgoods implements Serializable {

    private static final long serialVersionUID = -1845703421886803182L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String title;
    /**
     * 图片地址
     */
    private String picurl;
    /**
     * 描述信息
     */
    private String info;
    /**
     * 原价
     */
    private Double price;
    /**
     * 现价
     */
    private Double currprice;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 状态
     */
    private Integer flag;
    /**
     * 商品类型
     */
    private Integer itemId;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 秒杀活动id
     */
    private Integer said;
    /**
     * 静态化页面路径
     */
    private String htmlurl;

}

