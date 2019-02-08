package com.kstarrain.pojo;

import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description:
 */
public class Product {

    String id;

    String name;

    int stock;

    String status;

    Date createDate;

    Date updateDate;

    String aliveFlag;

    int version;

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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
