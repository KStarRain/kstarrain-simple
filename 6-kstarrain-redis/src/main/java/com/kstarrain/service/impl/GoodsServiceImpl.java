package com.kstarrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.pojo.Goods;
import com.kstarrain.service.IGoodsService;
import com.kstarrain.utils.DistributedLockUtils;
import com.kstarrain.utils.JedisPoolUtils;
import com.kstarrain.utils.JedisUtils;
import com.kstarrain.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

            Goods goods = JSON.parseObject(jedis.get(goodsKey), Goods.class);
            //获取商品剩余存货量
            int stock = goods.getStock();

            if (stock > 0) {

                if (quantity > stock){throw new BusinessException(BusinessErrorCode.BUSINESS003);}

                // 开启事务
                Transaction tx = jedis.multi();

                /** 减库存 */
                goods.setStock(stock - quantity);
                tx.set(goodsKey,JSON.toJSONString(goods));

                // 提交事务，如果此时监视的key被改动了，则返回null
                List<Object> execResult = tx.exec();

                if (CollectionUtils.isNotEmpty(execResult)) {//抢购成功

                    /** 创建订单 */
                    jedis.set("concurrent:order:" + buyerId, "买家" + buyerId + "抢购到了" + quantity + "件" + goods.getGoodsName());
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

    @Override
    public List<Goods> findAllGoods(String key) throws InterruptedException {

        //从缓存获取数据
        List<Goods> result = JSON.parseArray(JedisUtils.get(key), Goods.class);

        if (CollectionUtils.isEmpty(result)){

            String requestId = UUID.randomUUID().toString();

            //分布式锁
            if (DistributedLockUtils.tryLock(key,requestId,5)){

                try {
                    //从数据库取数据
                    result = this.getAllGoodsFromDB();

                    //将数据存入缓存
                    JedisUtils.set(key,JSON.toJSONString(result));
                } finally {
                    //释放锁
                    DistributedLockUtils.releaseLock(key,requestId);
                }
            }else {
                //再次从缓存中查询
                result = JSON.parseArray(JedisUtils.get(key), Goods.class);

                //如果缓存中还没有数据，休眠一会递归查询
                if (CollectionUtils.isEmpty(result)){
                    Thread.sleep(2000l);
                    result = findAllGoods(key);
                }
            }
        }else{
            System.out.println(ThreadLocalUtils.getValue("USER_ID",String.class) + " ---> 查询缓存");
        }
        return result;

    }




    //模仿从数据库取数据，用时21秒
    private List<Goods> getAllGoodsFromDB() throws InterruptedException {

        Thread.sleep(1000l);

        List<Goods> allGoods = new ArrayList<>();

        Goods goods1 = new Goods();
        goods1.setGoodsId("d7fbbfd87a08408ba994dea6be435111");
        goods1.setGoodsName("iphone8");
        goods1.setStock(10);
        allGoods.add(goods1);

        Goods goods2 = new Goods();
        goods2.setGoodsId("d7fbbfd87a08408ba994dea6be435222");
        goods2.setGoodsName("xiaomi");
        goods2.setStock(10);
        allGoods.add(goods2);

        System.out.println(ThreadLocalUtils.getValue("USER_ID",String.class) + " ---> 查询数据库");

        return allGoods;

    }
}