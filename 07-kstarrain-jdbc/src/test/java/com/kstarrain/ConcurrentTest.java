package com.kstarrain;


import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.dao.IGoodsDao;
import com.kstarrain.dao.impl.GoodsDaoImpl;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.pojo.Goods;
import com.kstarrain.service.IOrderService;
import com.kstarrain.service.impl.OrderServiceImpl;
import com.kstarrain.utils.JDBCUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description: 并发测试  junit不支持多线程测试
 */

@Slf4j
public class ConcurrentTest {

    private IGoodsDao goodsDao = new GoodsDaoImpl();
    private IOrderService oderService = new OrderServiceImpl();

    //商品id
    private static String goodsId = "d7fbbfd87a08408ba994dea6be435111";

    //采购量
    private static int quantity = 1;

    /** 减库存错误测试 01 */
    @Test
    public void reduceStockById_error01(){

        Connection conn = null;
        try {
            if (quantity <= 0){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            /** 减库存 */
            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Goods goods = goodsDao.findGoodsById(conn, goodsId);
            if (goods == null){throw new BusinessException(BusinessErrorCode.BUSINESS002);}

            if (goods.getStock() > 0){

                if (quantity > goods.getStock()){throw new BusinessException(BusinessErrorCode.BUSINESS003);}

                // update t_product set STOCK = STOCK - ?,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ?
                int num = goodsDao.reduceStockById_error(conn, goodsId, quantity);
                System.out.println(num);

            }else if (goods.getStock() == 0){
                throw new BusinessException(BusinessErrorCode.BUSINESS005);
            }else if (goods.getStock() < 0){
                throw new BusinessException(BusinessErrorCode.BUSINESS000);
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

        Connection conn = null;
        try {
            if (quantity <= 0){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            /** 减库存 */
            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Goods goods = goodsDao.findGoodsById(conn, goodsId);
            if (goods == null){throw new BusinessException(BusinessErrorCode.BUSINESS002);}

            if (goods.getStock() > 0){

                if (quantity > goods.getStock()){throw new BusinessException(BusinessErrorCode.BUSINESS003);}

                // update t_product set STOCK = STOCK - ?,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ?
                int num = goodsDao.reduceStockById_error(conn, goodsId, quantity);
                System.out.println(num);

            }else if (goods.getStock() == 0){
                throw new BusinessException(BusinessErrorCode.BUSINESS005);
            }else if (goods.getStock() < 0){
                throw new BusinessException(BusinessErrorCode.BUSINESS000);
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

    /** 减库存测试(update同一条数据,若该语句使用了索引，则为行锁。若未使用索引，则为表锁) 01 */
    @Test
    public void reduceStockById_01(){

        Connection conn = null;
        try {

            if (quantity <= 0){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

            conn = JDBCUtils.getConnection();

            //开启事务
            conn.setAutoCommit(false);

            /** 减库存 */
            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Goods goods = goodsDao.findGoodsById(conn, goodsId);
            if (goods == null){throw new BusinessException(BusinessErrorCode.BUSINESS002);}

            if (goods.getStock() > 0){

                if (quantity > goods.getStock()){throw new BusinessException(BusinessErrorCode.BUSINESS003);}

                // update t_product set STOCK = STOCK - ?,VERSION = VERSION + 1 ,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ? and VERSION = ?
                int num = goodsDao.reduceStockById(conn, goodsId, quantity, goods.getVersion());

                if (num == 0){throw new BusinessException(BusinessErrorCode.BUSINESS004);}

            }else if (goods.getStock() == 0){
                throw new BusinessException(BusinessErrorCode.BUSINESS005);
            }else if (goods.getStock() < 0){
                throw new BusinessException(BusinessErrorCode.BUSINESS000);
            }

            /** 创建订单 */
            //创建订单
            oderService.createOrder(conn, "user1", goodsId, quantity);
            System.out.println();

            conn.commit();
        } catch (BusinessException e) {
            JDBCUtils.rollback(conn);
            System.out.println("user1 ---> " + e.getMessage());
        } catch (Exception e) {
            JDBCUtils.rollback(conn);
            log.error(e.getMessage(),e);
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }

    /** 减库存测试(update同一条数据,若该语句使用了索引，则为行锁。若未使用索引，则为表锁) 02 */
    @Test
    public void reduceStockById_02(){

        Connection conn = null;
        try {

            if (quantity <= 0){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

            conn = JDBCUtils.getConnection();

            //开启事务
            conn.setAutoCommit(false);

            /** 减库存 */
            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Goods goods = goodsDao.findGoodsById(conn, goodsId);
            if (goods == null){throw new BusinessException(BusinessErrorCode.BUSINESS002);}

            if (goods.getStock() > 0){

                if (quantity > goods.getStock()){throw new BusinessException(BusinessErrorCode.BUSINESS003);}

                // update t_product set STOCK = STOCK - ?,VERSION = VERSION + 1 ,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ? and VERSION = ?
                int num = goodsDao.reduceStockById(conn, goodsId, quantity, goods.getVersion());

                if (num == 0){throw new BusinessException(BusinessErrorCode.BUSINESS004);}

            }else if (goods.getStock() == 0){
                throw new BusinessException(BusinessErrorCode.BUSINESS005);
            }else if (goods.getStock() < 0){
                throw new BusinessException(BusinessErrorCode.BUSINESS000);
            }

            /** 创建订单 */
            //创建订单
            oderService.createOrder(conn, "user2", goodsId, quantity);
            System.out.println();

            conn.commit();
        } catch (BusinessException e) {
            JDBCUtils.rollback(conn);
            System.out.println("user2 ---> " + e.getMessage());
        } catch (Exception e) {
            JDBCUtils.rollback(conn);
            log.error(e.getMessage(),e);
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }

    /** 测试mysql死锁 01 */
    @Test
    public void test_deadlock1(){

        String goodsId1 = "d7fbbfd87a08408ba994dea6be435111";
        String goodsId2 = "d7fbbfd87a08408ba994dea6be435222";

        Connection conn = null;
        try {

            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            int num1 = goodsDao.reduceStockById_error(conn, goodsId1, quantity);

            int num2 = goodsDao.reduceStockById_error(conn, goodsId2, quantity);

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


    /** 测试mysql死锁 02 */
    @Test
    public void test_deadlock2(){

        String goodsId1 = "d7fbbfd87a08408ba994dea6be435111";
        String goodsId2 = "d7fbbfd87a08408ba994dea6be435222";

        Connection conn = null;
        try {

            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            int num1 = goodsDao.reduceStockById_error(conn, goodsId2, quantity);

            int num2 = goodsDao.reduceStockById_error(conn, goodsId1, quantity);

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


}
