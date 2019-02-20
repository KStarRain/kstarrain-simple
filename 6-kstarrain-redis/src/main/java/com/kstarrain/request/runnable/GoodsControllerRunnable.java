package com.kstarrain.request.runnable;

import com.kstarrain.controller.GoodsController;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: DongYu
 * @create: 2019-02-17 13:19
 * @description: 该类为了模仿多个线程请求单例的controller
 */
@Slf4j
public class GoodsControllerRunnable implements Runnable{

    private GoodsController goodsController;

    //用户id
    private String buyerId;
    //商品key
    private String goodsKey;
    //采购数
    private int quantity;


    public GoodsControllerRunnable(GoodsController goodsController, String buyerId, String goodsKey, int quantity) {
        this.goodsController = goodsController;
        this.buyerId = buyerId;
        this.goodsKey = goodsKey;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        goodsController.reduceStockByKey(buyerId,goodsKey,quantity);
    }
}
