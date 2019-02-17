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
    //商品id
    private String goodsId;
    //采购数
    private int quantity;



    public GoodsController(String buyerId, String goodsId, int quantity) {
        this.buyerId = buyerId;
        this.goodsId = goodsId;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        try {
            goodsService.reduceStockById(buyerId,goodsId,quantity);
        } catch (BusinessException e) {
            System.out.println(buyerId + " ---> " + e.getMessage());
        } catch (Exception e) {
            System.out.println(buyerId + " ---> " + "系统异常");
            log.error(e.getMessage(),e);
        }
    }
}
