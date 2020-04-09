package com.kstarrain.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description:
 */
@Entity
@Table(name = "t_student")
public class Student {

    @Column(name = "ID")
    String id;

    @Column(name = "NAME")
    String name;

    @Column(name = "BIRTHDAY")
    Date birthday;

    @Column(name = "MONEY")
    BigDecimal money;

    @Column(name = "CREATE_DATE")
    Date createDate;

    @Column(name = "UPDATE_DATE")
    Date updateDate;

    @Column(name = "ALIVE_FLAG")
    String aliveFlag;
}
