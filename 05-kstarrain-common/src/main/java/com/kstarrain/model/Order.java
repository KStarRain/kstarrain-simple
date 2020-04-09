package com.kstarrain.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description: 订单
 */
@Data
@XStreamAlias("order")
public class Order {

    //商品名字
    String goodsName;

}
