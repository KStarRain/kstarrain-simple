package com.kstarrain.dao;

import com.kstarrain.pojo.Order;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:35
 * @description:
 */
public interface IOrderDao {

    /**
     * 添加订单
     *
     * @param conn
     * @param order
     * @return
     * @throws SQLException
     */
    int insertOrder(Connection conn, Order order) throws SQLException;






}
