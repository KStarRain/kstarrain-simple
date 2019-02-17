package com.kstarrain.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description: 商品
 */
@Data
public class Goods {

    //商品id
    String goodsId;

    //商品名字
    String goodsName;

    //商品库存
    int stock;

    //商品状态(1:上架  0:下架)
    String status;

    //创建时间
    Date createDate;

    //更新时间
    Date updateDate;

    //删除标记
    String aliveFlag;

    //数据版本号
    int version;

}
