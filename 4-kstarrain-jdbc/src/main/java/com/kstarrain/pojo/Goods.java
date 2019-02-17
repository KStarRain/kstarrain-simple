package com.kstarrain.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description:
 */
@Data
public class Goods {

    String id;

    String name;

    int stock;

    String status;

    Date createDate;

    Date updateDate;

    String aliveFlag;

    int version;

}
