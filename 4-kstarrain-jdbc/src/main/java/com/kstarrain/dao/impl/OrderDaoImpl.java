package com.kstarrain.dao.impl;

import com.kstarrain.dao.IGoodsDao;
import com.kstarrain.dao.IOrderDao;
import com.kstarrain.pojo.Goods;
import com.kstarrain.pojo.Order;
import com.kstarrain.utils.JDBCUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:36
 * @description:
 */
@Slf4j
public class OrderDaoImpl implements IOrderDao {

    @Override
    public int insertOrder(Connection conn, Order order) throws SQLException {
        int num = 0;

        PreparedStatement st = null;

        try {
            String sql = "insert into t_order(   order_id, " +
                                                "order_code, " +
                                                "goods_id, " +
                                                "goods_num, " +
                                                "buyer_id, " +
                                                "trade_status, " +
                                                "create_date, " +
                                                "update_date, " +
                                                "alive_flag)" +
                                            "values(?,?,?,?,?,?,?,?,?)";



            st = conn.prepareStatement(sql);
            st.setString(1, null);
            st.setString(2, order.getOrderCode());
            st.setString(3, order.getGoodsId());
            st.setInt(4, order.getGoodsNum());
            st.setString(5, order.getBuyerId());
            st.setInt(6, order.getTradeStatus());
            st.setTimestamp(7, new Timestamp(order.getCreateDate().getTime()));
            st.setTimestamp(8, new Timestamp(order.getUpdateDate().getTime()));
            st.setString(9, order.getAliveFlag());

            num = st.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtils.closeStatement(st);
        }
        return num;
    }
}
