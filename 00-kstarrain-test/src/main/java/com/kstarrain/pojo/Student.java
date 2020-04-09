package com.kstarrain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description:
 */
@Data
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

//    @JSONField(serialize = false)
    String id;

    String name;

    Date birthday;

    BigDecimal money;

    Date createDate;

    Date updateDate;

    String aliveFlag;

    Integer age;


//    public String getName() {
//        return "hahaha";
//    }
}
