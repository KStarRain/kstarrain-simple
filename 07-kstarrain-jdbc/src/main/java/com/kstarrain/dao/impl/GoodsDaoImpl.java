package com.kstarrain.dao.impl;

import com.kstarrain.dao.IGoodsDao;
import com.kstarrain.pojo.Goods;
import com.kstarrain.utils.JDBCUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:36
 * @description:
 */
public class GoodsDaoImpl implements IGoodsDao {


    @Override
    public Goods findGoodsById(Connection conn, String id) throws SQLException {

        Goods product = null;

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "select * from t_goods where alive_flag = '1' and status = '1' and goods_id = ? " ;

            //预加载
            st = conn.prepareStatement(sql);
            st.setString(1,id);

            //执行SQL语句
            rs = st.executeQuery();

            //处理结果集
            while (rs.next()){
                product = new Goods();
                product.setGoodsId(rs.getString("goods_id"));
                product.setGoodsName(rs.getString("goods_name"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getString("status"));
                product.setCreateDate(rs.getTimestamp("create_date"));
                product.setUpdateDate(rs.getTimestamp("update_date"));
                product.setAliveFlag(rs.getString("alive_flag"));
                product.setVersion(rs.getInt("version"));
                break;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtils.closeResultSet(rs);
            JDBCUtils.closeStatement(st);
        }

        return product;
    }

    @Override
    public int reduceStockById_error(Connection conn,int quantity, String id) throws SQLException {
        int num = 0;

        PreparedStatement st = null;

        try {
            String sql = "update t_goods set stock = stock - ?,update_date = SYSDATE() where alive_flag = '1' and goods_id = ?";

            st = conn.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setString(2, id);

            num = st.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            JDBCUtils.closeStatement(st);
        }
        return num;
    }

    @Override
    public int reduceStockById(Connection conn, String id, int quantity, int version) throws SQLException {
        int num = 0;

        PreparedStatement st = null;

        try {
            String sql = "update t_goods set stock = stock - ?,version = version + 1 ,update_date = SYSDATE() where alive_flag = '1' and goods_id = ? and version = ?";

            st = conn.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setString(2, id);
            st.setInt(3, version);

            num = st.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            JDBCUtils.closeStatement(st);
        }
        return num;
    }


}
