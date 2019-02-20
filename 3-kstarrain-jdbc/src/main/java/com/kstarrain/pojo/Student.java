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

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", money=" + money +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", aliveFlag='" + aliveFlag + '\'' +
                '}';
    }
}
