package com.kstarrain.dao.impl;

import com.kstarrain.dao.IGoodsDao;
import com.kstarrain.pojo.Goods;
import com.kstarrain.utils.JDBCUtils;

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
    public Goods findProductById(Connection conn, String id) throws SQLException {

        Goods product = null;

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "select * from t_goods where ALIVE_FLAG = '1' and STATUS = '1' and ID = ? " ;

            //预加载
            st = conn.prepareStatement(sql);
            st.setString(1,id);

            //执行SQL语句
            rs = st.executeQuery();

            //处理结果集
            while (rs.next()){
                product = new Goods();
                product.setId(rs.getString("ID"));
                product.setName(rs.getString("NAME"));
                product.setStock(rs.getInt("STOCK"));
                product.setStatus(rs.getString("STATUS"));
                product.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                product.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
                product.setAliveFlag(rs.getString("ALIVE_FLAG"));
                product.setVersion(rs.getInt("VERSION"));
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
            String sql = "update t_goods set STOCK = STOCK - ?,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ?";

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
    public int reduceStockById(Connection conn, int quantity, String id, int version) throws SQLException {
        int num = 0;

        PreparedStatement st = null;

        try {
            String sql = "update t_goods set STOCK = STOCK - ?,VERSION = VERSION + 1 ,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ? and VERSION = ?";

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
