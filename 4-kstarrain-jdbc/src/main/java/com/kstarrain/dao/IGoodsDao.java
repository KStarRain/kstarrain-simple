package com.kstarrain.dao;

import com.kstarrain.pojo.Goods;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:35
 * @description:
 */
public interface IGoodsDao {

    Goods findGoodsById(Connection conn, String id) throws SQLException;

    int reduceStockById_error(Connection conn, int quantity, String id) throws SQLException;

    int reduceStockById(Connection conn, String id, int quantity, int version) throws SQLException;






}
