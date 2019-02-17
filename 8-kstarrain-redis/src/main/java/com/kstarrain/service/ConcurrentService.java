package com.kstarrain.service;

import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.utils.JedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-02-16 21:43
 * @description:
 */
@Slf4j
public class ConcurrentService implements Runnable {

    //用户
    private String userName;
    //商品
    private String goodsKey;
    //商品总数
    private Integer quantity;

    public ConcurrentService(String userName, String goodsKey, Integer quantity) {
        this.userName = userName;
        this.goodsKey = goodsKey;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        this.reduceStockByKey(goodsKey);
    }


    private void reduceStockByKey(String goodsKey){

        Jedis jedis = null;

        try {
            jedis = JedisPoolUtils.getJedis();


            //监视一个key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
            jedis.watch(goodsKey);

            //获取商品剩余存货量
            int stock = Integer.parseInt(jedis.get(goodsKey));

            if (stock > 0) {

                Transaction tx = jedis.multi();// 开启事务

                tx.incrBy(goodsKey, -1);// 商品数量-1

                List<Object> execResult = tx.exec();// 提交事务，如果此时监视的key被改动了，则返回null
                if (CollectionUtils.isNotEmpty(execResult)) {
                    String successMsg = userName + "---> 抢购到第【" + ((quantity - stock) + 1) + "】份商品";

                    //抢购成功
                    jedis.set("goodsResult:sucess:" + userName, successMsg);
                    System.out.println(successMsg);
                } else {
                    throw new BusinessException(BusinessErrorCode.BUSINESS002);
                }

            } else if (stock == 0) {
                throw new BusinessException(BusinessErrorCode.BUSINESS003);
            } else if (stock < 0) {
                throw new BusinessException(BusinessErrorCode.BUSINESS000);
            }
        } catch (BusinessException e) {
            System.out.println(userName + " ---> " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }


    }



}