package com.kstarrain.service.impl;

import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.dao.IGoodsDao;
import com.kstarrain.dao.impl.GoodsDaoImpl;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.pojo.Goods;
import com.kstarrain.service.IGoodsService;
import com.kstarrain.service.IOrderService;
import com.kstarrain.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: DongYu
 * @create: 2019-02-17 12:55
 * @description: 高并发秒杀--基于数据库版本号的乐观锁
 */
public class GoodsServiceImpl implements IGoodsService {

    private IGoodsDao goodsDao = new GoodsDaoImpl();

    private IOrderService oderService = new OrderServiceImpl();

    @Override
    public void reduceStockById(String buyerId, String goodsId, int quantity) throws SQLException {

        Connection conn = null;
        try {

            if (quantity <= 0){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

            conn = JDBCUtils.getConnection();

            //开启事务
            conn.setAutoCommit(false);

            /** 减库存 */
            this.reduceStockById(conn, goodsId, quantity);

            /** 创建订单 */
            oderService.createOrder(conn, buyerId, goodsId, quantity);

            conn.commit();
        } catch (Exception e) {
            JDBCUtils.rollback(conn);
            throw e;
        } finally{
            JDBCUtils.closeConnection(conn);
        }

    }

    /** 减库存 */
    private void reduceStockById(Connection conn, String goodsId, int quantity) throws SQLException {

        // select * from t_student where ALIVE_FLAG = '1' and ID = ?
        Goods goods = goodsDao.findProductById(conn, goodsId);

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

    }
}
