package org.example.cloud.goodsddd.domian;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_items")
public class GoodsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String no;
}
