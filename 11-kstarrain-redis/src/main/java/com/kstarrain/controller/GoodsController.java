package com.kstarrain.controller;

import com.kstarrain.exception.BusinessException;
import com.kstarrain.pojo.Goods;
import com.kstarrain.service.IGoodsService;
import com.kstarrain.service.impl.GoodsServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-02-17 13:19
 * @description:
 */
@Slf4j
public class GoodsController{

    private IGoodsService goodsService = new GoodsServiceImpl();


    public void reduceStockByKey(String buyerId, String goodsKey, int quantity){
        try {
            goodsService.reduceStockByKey(buyerId,goodsKey,quantity);
        } catch (BusinessException e) {
            System.out.println(buyerId + " ---> " + e.getMessage());
        } catch (Exception e) {
            System.out.println(buyerId + " ---> " + "系统异常");
            log.error(e.getMessage(),e);
        }
    }


    /**
     * 测试缓存击穿(缓存过期的时候，大量数据并发直达数据库)
     */
    public List<Goods> findAllGoods(String key){
        List<Goods> allGoods = new ArrayList<>();
        try {
            allGoods = goodsService.findAllGoods(key);
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("系统异常");
            log.error(e.getMessage(),e);
        }
        return allGoods;
    }
}
