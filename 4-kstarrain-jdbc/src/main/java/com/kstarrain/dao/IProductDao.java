package com.kstarrain.dao;

import com.kstarrain.pojo.Product;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:35
 * @description:
 */
public interface IProductDao {

    Product findProductById(Connection conn, String id) throws SQLException;

    int reduceStockById_error(Connection conn, int quantity, String id) throws SQLException;

    int reduceStockById(Connection conn, int quantity, String id, int version) throws SQLException;






}
