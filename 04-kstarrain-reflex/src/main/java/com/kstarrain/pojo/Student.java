package com.kstarrain.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description:
 */
public class Student {

    String id;

    String name;

    Date birthday;

    BigDecimal money;

    Date createDate;

    Date updateDate;

    String aliveFlag;

    public Student() {
    }

    public Student(String id, String name, Date birthday, BigDecimal money, Date createDate, Date updateDate, String aliveFlag) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.money = money;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.aliveFlag = aliveFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getAliveFlag() {
        return aliveFlag;
    }

    public void setAliveFlag(String aliveFlag) {
        this.aliveFlag = aliveFlag;
    }


}
