package org.example.cloud.neworder.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TOrderitem implements Serializable {
    private static final long serialVersionUID = -10477581877098441L;

    private Integer id;

    private Integer oid;

    private Integer gid;
    /**
     * 价格
     */
    private Double price;

    private Integer num;

}

