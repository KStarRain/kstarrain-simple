package com.kstarrain.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description:
 */
@Data
public class Student {

    @JSONField(serialize = false)
    String id;

    String name;

    Date birthday;

    BigDecimal money;

    Date createDate;

    Date updateDate;

    String aliveFlag;

//    public String getName() {
//        return "hahaha";
//    }
}
