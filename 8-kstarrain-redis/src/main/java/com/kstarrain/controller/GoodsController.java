package com.kstarrain.controller;

import com.kstarrain.exception.BusinessException;
import com.kstarrain.service.IGoodsService;
import com.kstarrain.service.impl.GoodsServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: DongYu
 * @create: 2019-02-17 13:19
 * @description:
 */
@Slf4j
public class GoodsController implements Runnable{

    private IGoodsService goodsService = new GoodsServiceImpl();

    //用户id
    private String buyerId;
    //商品key
    private String goodsKey;
    //采购数
    private int quantity;



    public GoodsController(String buyerId, String goodsKey, int quantity) {
        this.buyerId = buyerId;
        this.goodsKey = goodsKey;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        try {
            goodsService.reduceStockByKey(buyerId,goodsKey,quantity);
        } catch (BusinessException e) {
            System.out.println(buyerId + " ---> " + e.getMessage());
        } catch (Exception e) {
            System.out.println(buyerId + " ---> " + "系统异常");
            log.error(e.getMessage(),e);
        }
    }
}
