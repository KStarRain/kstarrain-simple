package com.kstarrain;

import com.kstarrain.service.ConcurrentService;
import com.kstarrain.utils.JedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: DongYu
 * @create: 2019-02-16 19:12
 * @description: 基于watch的redis秒杀(乐观锁)
*                https://blog.csdn.net/qq1013598664/article/details/70183908
 */
@Slf4j
public class ConcurrentTest {

    @Test
    public void test(){

        String GOODS_KEY = "goods:iphone8";
        // 设置10个商品
        String GOODS_NUMBER = "10";

        /** 初始化商品 */
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.set(GOODS_KEY, GOODS_NUMBER);
        //删除抢购成功的人
        Set<String> keys = jedis.keys("goodsResult*");
        if (CollectionUtils.isNotEmpty(keys)){
            jedis.del(keys.toArray(new String[keys.size()]));
        }
        jedis.close();


        ExecutorService executor = Executors.newFixedThreadPool(20);
        // 测试一万人同时抢购
        for (int i = 0; i < 10000; i++) {
            executor.execute(new ConcurrentService("user" + i));
        }
        executor.shutdown();
    }

}
