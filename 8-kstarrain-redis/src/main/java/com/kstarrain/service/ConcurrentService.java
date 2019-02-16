package com.kstarrain.service;

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

    private String userName;

    private String GOODS_KEY = "goods:iphone8";
    // 共10个商品
    private Integer GOODS_NUMBER = 10;

    private Jedis jedis = JedisPoolUtils.getJedis();


    public ConcurrentService(String userName) {
        this.userName = userName;
    }

    @Override
    public void run() {

        try {
            //监视一个key，当事务执行之前这个key发生了改变，事务会被打算，事务exec返回结果为null
            jedis.watch(GOODS_KEY);

            int goodsCount = Integer.parseInt(jedis.get(GOODS_KEY)); // 当前剩余商品数量

            if (goodsCount > 0) {

                Transaction tx = jedis.multi();// 开启事务

                tx.incrBy(GOODS_KEY, -1);// 商品数量-1

                List<Object> execResult = tx.exec();// 提交事务，如果此时监视的key被改动了，则返回null
                if (CollectionUtils.isNotEmpty(execResult)) {
                    String value = userName + "---> 抢购到第【" + ((GOODS_NUMBER - goodsCount) + 1) + "】份商品";
                    System.out.println(value);
                    jedis.set("goodsResult:iphone8:" + userName, value); // 业务代码，处理抢购成功
                } else {
                    System.out.println(userName + "---> 抢购失败，请重新抢购");
                }

            } else if (goodsCount == 0) {
                System.out.println("商品已抢完，" + userName + "---> 抢购失败 XXX");
            } else if (goodsCount < 0) {
                System.out.println("系统异常");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

    }
}