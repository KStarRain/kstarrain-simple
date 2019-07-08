package com.kstarrain.pojo;

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

    String id;

    String name;

    Date birthday;

    String birthdayStr;

    BigDecimal money;

    Date createDate;

    String createDateStr;

    Date updateDate;

    String updateDateStr;

    String aliveFlag;
}
