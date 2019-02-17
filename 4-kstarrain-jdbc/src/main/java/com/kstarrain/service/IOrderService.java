package com.kstarrain.service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-02-17 12:37
 * @description:
 */
public interface IOrderService {

    /**
     * 生成订单
     * @param conn
     * @param buyerId  买家id
     * @param goodsId  商品id
     * @param quantity 采购量
     * @throws SQLException
     */
    void createOrder(Connection conn, String buyerId, String goodsId, int quantity) throws SQLException;
}
