package com.kstarrain.request;

import com.alibaba.fastjson.JSON;
import com.kstarrain.controller.GoodsController;
import com.kstarrain.pojo.Goods;
import com.kstarrain.request.runnable.ReduceStockRunnable;
import com.kstarrain.utils.JedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: DongYu
 * @create: 2019-02-16 19:12
 * @description: 基于watch的redis秒杀(乐观锁)
*                https://blog.csdn.net/qq1013598664/article/details/70183908
 */
@Slf4j
public class ReduceStockRequest {

    //商品id
    private static String goodsId = "d7fbbfd87a08408ba994dea6be435111";
    //商品key
    private static String goodsKey = "concurrent:goods:" + goodsId;
    //商品总库存数
    private static int stock = 10;

    //单次抢购数量
    private static int quantity = 1;




    /** 高并发抢购测试  */
    public static void main(String[] args) {

        GoodsController goodsController = new GoodsController();

        // 初始化商品
        initGoods();

        ExecutorService executor = Executors.newFixedThreadPool(1000);
        // 测试一万人同时抢购
        for (int i = 1; i <= 10000; i++) {
            executor.execute(new ReduceStockRunnable(goodsController,"user" + i, goodsKey, quantity));
        }
        executor.shutdown();

    }



    private static void initGoods() {

        Jedis jedis = JedisPoolUtils.getJedis();

        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        goods.setGoodsName("iphone8");
        goods.setStock(stock);

        //设置需要抢购的商品商品
        jedis.set(goodsKey, JSON.toJSONString(goods));

        //删除抢购成功key
        Set<String> keys = jedis.keys("concurrent:order:*");
        if (CollectionUtils.isNotEmpty(keys)){
            jedis.del(keys.toArray(new String[keys.size()]));
        }
        jedis.close();
    }

}
