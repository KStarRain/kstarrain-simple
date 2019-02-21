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

}
