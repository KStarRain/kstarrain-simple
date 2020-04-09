package com.kstarrain.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description:
 */
@Data
@XStreamAlias("xml")
public class Student {

    String id;

    String name;

    Date birthday;

    BigDecimal money;

    Date createDate;

    Date updateDate;

    Boolean aliveFlag;

    List<Order> orderList;
}
