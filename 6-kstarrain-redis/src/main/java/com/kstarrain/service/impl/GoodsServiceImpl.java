package com.kstarrain.service.impl;

import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.service.IGoodsService;
import com.kstarrain.utils.JedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-02-16 21:43
 * @description:
 */
public class GoodsServiceImpl implements IGoodsService {


    @Override
    public void reduceStockByKey(String buyerId, String goodsKey, int quantity){

        Jedis jedis = null;

        try {

            if (quantity <= 0){throw new BusinessException(BusinessErrorCode.BUSINESS001);}

            jedis = JedisPoolUtils.getJedis();

            if (!jedis.exists(goodsKey)){throw new BusinessException(BusinessErrorCode.BUSINESS002);}

            //监视一个key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
            jedis.watch(goodsKey);

            //获取商品剩余存货量
            int stock = Integer.parseInt(jedis.get(goodsKey));

            if (stock > 0) {

                if (quantity > stock){throw new BusinessException(BusinessErrorCode.BUSINESS003);}

                // 开启事务
                Transaction tx = jedis.multi();

                /** 减库存 */
                tx.set(goodsKey,String.valueOf(stock - quantity));

                // 提交事务，如果此时监视的key被改动了，则返回null
                List<Object> execResult = tx.exec();

                if (CollectionUtils.isNotEmpty(execResult)) {//抢购成功

                    /** 创建订单 */
                    jedis.set("concurrent:order:" + buyerId, "买家" + buyerId + "抢购到了" + quantity + "件商品");
                } else {
                    throw new BusinessException(BusinessErrorCode.BUSINESS004);
                }

            } else if (stock == 0) {
                throw new BusinessException(BusinessErrorCode.BUSINESS005);
            } else if (stock < 0) {
                throw new BusinessException(BusinessErrorCode.BUSINESS000);
            }
        }  catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }



    }
}