package com.kstarrain.service;

import com.kstarrain.pojo.Goods;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-02-17 12:37
 * @description:
 */
public interface IGoodsService {

    /**
     * 抢购商品 减库存
     * @param buyerId  买家id
     * @param goodsKey 商品key
     * @param quantity 采购量
     */
    void reduceStockByKey(String buyerId, String goodsKey, int quantity);


    List<Goods> findAllGoods(String key) throws InterruptedException;
}
