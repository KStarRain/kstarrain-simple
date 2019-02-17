package com.kstarrain.service;

import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.dao.IGoodsDao;
import com.kstarrain.dao.impl.GoodsDaoImpl;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.pojo.Goods;
import com.kstarrain.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-02-16 21:35
 * @description: 高并发秒杀--基于数据库版本号的乐观锁
 */

public class ConcurrentService implements Runnable {

    private IGoodsDao goodsDao = new GoodsDaoImpl();

    //用户
    private String userName;
    //商品
    private String id;
    //商品总数
    private Integer quantity;

    public ConcurrentService(String userName, String id, Integer quantity) {
        this.userName = userName;
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        this.reduceStockById(id);
    }


    private void reduceStockById(String id){

        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            //开启事务
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Goods goods = goodsDao.findProductById(conn, id);
            if (goods == null){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

            if (product.getStock() > 0){

                if (product.getStock() < QUANTITY){throw new RuntimeException("采购量不能大于库存量");}

                // update t_product set STOCK = STOCK - ?,VERSION = VERSION + 1 ,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ? and VERSION = ?
                int num = goodsDao.reduceStockById(conn, QUANTITY, ID, product.getVersion());

                if (num == 0){throw new RuntimeException("抢购失败，请重新尝试");}

            }else if (product.getStock() == 0){
                throw new RuntimeException("产品库存不足");
            }else if (product.getStock() < 0){
                throw new RuntimeException("系统异常");
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
