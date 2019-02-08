package com.kstarrain;


import com.kstarrain.dao.IProductDao;
import com.kstarrain.dao.impl.ProductDaoImpl;
import com.kstarrain.pojo.Product;
import com.kstarrain.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description: 并发测试
 */


public class ConcurrentlTest {


    private static String ID = "d7fbbfd87a08408ba994dea6be435111";

    private static int QUANTITY = 1;

    /** 减库存错误测试 01 */
    @Test
    public void reduceStockById_error01(){
        IProductDao productDao = new ProductDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Product product = productDao.findProductById(conn, ID);
            if (product == null){throw new RuntimeException("产品不存在");}

            if (product.getStock() > 0){

                if (product.getStock() < QUANTITY){throw new RuntimeException("采购量不能大于库存量");}

                // update t_product set STOCK = STOCK - ?,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ?
                int num = productDao.reduceStockById_error(conn, QUANTITY, ID);
                System.out.println(num);

            }else if (product.getStock() == 0){
                throw new RuntimeException("产品库存不足");
            }else if (product.getStock() < 0){
                throw new RuntimeException("并发问题引起库存为负数");
            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            System.out.println(e.getMessage());
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


    /** 减库存错误测试 02 */
    @Test
    public void reduceStockById_error02(){
        IProductDao productDao = new ProductDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Product product = productDao.findProductById(conn, ID);
            if (product == null){throw new RuntimeException("产品不存在");}

            if (product.getStock() > 0){

                if (product.getStock() < QUANTITY){throw new RuntimeException("采购量不能大于库存量");}

                // update t_product set STOCK = STOCK - ?,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ?
                int num = productDao.reduceStockById_error(conn, QUANTITY, ID);
                System.out.println(num);

            }else if (product.getStock() == 0){
                throw new RuntimeException("产品库存不足");
            }else if (product.getStock() < 0){
                throw new RuntimeException("并发问题引起库存为负数");
            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            System.out.println(e.getMessage());
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }

    /** 减库存测试 01 */
    @Test
    public void reduceStockById_01(){
        IProductDao productDao = new ProductDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Product product = productDao.findProductById(conn, ID);
            if (product == null){throw new RuntimeException("产品不存在");}

            if (product.getStock() > 0){

                if (product.getStock() < QUANTITY){throw new RuntimeException("采购量不能大于库存量");}

                // update t_product set STOCK = STOCK - ?,VERSION = VERSION + 1 ,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ? and VERSION = ?
                int num = productDao.reduceStockById(conn, QUANTITY, ID, product.getVersion());

                if (num == 0){throw new RuntimeException("抢购失败，请重新尝试");}

            }else if (product.getStock() == 0){
                throw new RuntimeException("产品库存不足");
            }else if (product.getStock() < 0){
                throw new RuntimeException("并发问题引起库存为负数");
            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            System.out.println(e.getMessage());
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


    /** 减库存测试 02 */
    @Test
    public void reduceStockById_02(){
        IProductDao productDao = new ProductDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Product product = productDao.findProductById(conn, ID);
            if (product == null){throw new RuntimeException("产品不存在");}

            if (product.getStock() > 0){

                if (product.getStock() < QUANTITY){throw new RuntimeException("采购量不能大于库存量");}

                // update t_product set STOCK = STOCK - ?,VERSION = VERSION + 1 ,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ? and VERSION = ?
                int num = productDao.reduceStockById(conn, QUANTITY, ID, product.getVersion());

                if (num == 0){throw new RuntimeException("抢购失败，请重新尝试");}

            }else if (product.getStock() == 0){
                throw new RuntimeException("产品库存不足");
            }else if (product.getStock() < 0){
                throw new RuntimeException("并发问题引起库存为负数");
            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            System.out.println(e.getMessage());
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


}
