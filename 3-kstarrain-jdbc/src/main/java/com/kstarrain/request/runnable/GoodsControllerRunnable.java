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
    //商品id
    private String goodsId;
    //采购数
    private int quantity;


    public GoodsControllerRunnable(GoodsController goodsController, String buyerId, String goodsId, int quantity) {
        this.goodsController = goodsController;
        this.buyerId = buyerId;
        this.goodsId = goodsId;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        goodsController.reduceStockById(buyerId,goodsId,quantity);
    }
}
