package org.example.cloudskill.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

/**
 * 20.秒杀订单流水表(TSkillorderlog)实体类
 *
 * @author zed
 * @since 2023-03-21 11:22:21
 */
@Data
public class TSkillorderlog implements Serializable {

    private static final long serialVersionUID = -1992307202711012942L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 秒杀订单id
     */
    private Integer oid;
    /**
     * 状态
     */
    private Integer type;
    /**
     * 备注信息
     */
    private String info;
    /**
     * 更新时间
     */
    private Date ctime;
    public TSkillorderlog() {
    }

    public TSkillorderlog(Integer oid, Integer type, String info) {
        this.oid = oid;
        this.type = type;
        this.info = info;
    }


}

