package com.kstarrain.request;

import com.alibaba.fastjson.JSON;
import com.kstarrain.controller.GoodsController;
import com.kstarrain.pojo.Goods;
import com.kstarrain.request.runnable.CacheBreakdownRunnable;
import com.kstarrain.request.runnable.ReduceStockRunnable;
import com.kstarrain.utils.JedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.security.Key;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: DongYu
 * @create: 2019-02-16 19:12
 * @description: 缓存击穿
 *               https://blog.csdn.net/sanyaoxu_2/article/details/79472465
 */
@Slf4j
public class CacheBreakdownRequest {

    private static String key = "all_goods";


    /** 缓存并发测试  */
    public static void main(String[] args) {

        GoodsController goodsController = new GoodsController(); // 初始化商品

        deleteAllGoods();

        ExecutorService executor = Executors.newFixedThreadPool(1000);
        // 测试一万人同时抢购
        for (int i = 1; i <= 10000; i++) {
            executor.execute(new CacheBreakdownRunnable(goodsController,"user" + i,key));
        }
        executor.shutdown();

    }

    private static void deleteAllGoods() {
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.del(key);
        jedis.close();
    }


}
