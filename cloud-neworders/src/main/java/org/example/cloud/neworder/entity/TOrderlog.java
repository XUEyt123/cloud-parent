package org.example.cloud.neworder.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TOrderlog implements Serializable {
    private static final long serialVersionUID = 486032461728064431L;

    private Integer id;

    private Integer oid;
    /**
     * 对应订单状态类型
     */
    private Integer type;
    /**
     * 内容
     */
    private String info;

    private Date createTime;

}
