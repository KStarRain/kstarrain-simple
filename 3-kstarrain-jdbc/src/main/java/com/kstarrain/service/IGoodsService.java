package com.kstarrain.service;

import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-02-17 12:37
 * @description:
 */
public interface IGoodsService {

    /**
     * 抢购商品 减库存
     * @param buyerId  买家id
     * @param goodsId  商品id
     * @param quantity 采购量
     */
    void reduceStockById(String buyerId, String goodsId, int quantity) throws SQLException;
}
