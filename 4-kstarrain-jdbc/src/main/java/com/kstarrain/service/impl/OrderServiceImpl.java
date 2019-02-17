package com.kstarrain.service.impl;

import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.dao.IGoodsDao;
import com.kstarrain.dao.IOrderDao;
import com.kstarrain.dao.impl.GoodsDaoImpl;
import com.kstarrain.dao.impl.OrderDaoImpl;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.pojo.Goods;
import com.kstarrain.pojo.Order;
import com.kstarrain.service.IGoodsService;
import com.kstarrain.service.IOrderService;
import com.kstarrain.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author: DongYu
 * @create: 2019-02-17 12:55
 * @description: 订单service
 */
public class OrderServiceImpl implements IOrderService {

    private IOrderDao orderDao = new OrderDaoImpl();


    @Override
    public void createOrder(Connection conn, String buyerId, String goodsId, int goodsNum) throws SQLException {

        Order order = new Order();

        order.setGoodsId(goodsId);
        order.setGoodsNum(goodsNum);
        order.setBuyerId(buyerId);
        order.setTradeStatus(1);
        order.setCreateDate(new Date());
        order.setUpdateDate(new Date());
        order.setAliveFlag("1");
        orderDao.insertOrder(conn, order);

    }
}
