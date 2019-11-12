package com.kstarrain.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:18
 * @description: 订单
 */
@Data
public class Order {

    //订单id(主键自增)
    int orderId;

    //订单编号
    String orderCode = "";

    //商品id
    String goodsId;

    //商品数量
    int goodsNum;

    //买家id
    String buyerId;

    //交易状态(1:等待买家付款 2:买家已付款，等待卖家发货 3:卖家已发货，等待买家确认 4:交易成功 5:交易关闭)
    int tradeStatus;

    //创建时间
    Date createDate;

    //更新时间
    Date updateDate;

    //删除标记
    String aliveFlag;
}
