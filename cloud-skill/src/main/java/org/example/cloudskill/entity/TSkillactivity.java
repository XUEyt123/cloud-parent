package org.example.cloudskill.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TSkillactivity implements Serializable {

    private static final long serialVersionUID = 5813567815131618088L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String title;
    /**
     * 描述
     */
    private String info;
    /**
     * 活动图片
     */
    private String picurl;
    /**
     * 开始时间
     */
    private Date stime;
    /**
     * 结束时间
     */
    private Date etime;
    /**
     * 状态
     */
    private Integer flag;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 最大购买量
     */
    private Integer maxcount;

}

