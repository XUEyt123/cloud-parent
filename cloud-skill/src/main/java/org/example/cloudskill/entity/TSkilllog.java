package org.example.cloudskill.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

/**
 * 21.秒杀记录表(TSkilllog)实体类
 *
 * @author zed
 * @since 2023-03-21 11:22:19
 */
@Data
public class TSkilllog implements Serializable {

    private static final long serialVersionUID = 493483910967274070L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 秒杀商品ID
     */
    private Integer sgid;
    /**
     * 秒杀结果 1.成功 2.失败
     */
    private String status;
    /**
     * 创建时间
     */
    private Date ctime;

    public TSkilllog() {

    }

    public TSkilllog(Integer uid, Integer sgid, String status) {
        this.uid = uid;
        this.sgid = sgid;
        this.status = status;
    }
}

