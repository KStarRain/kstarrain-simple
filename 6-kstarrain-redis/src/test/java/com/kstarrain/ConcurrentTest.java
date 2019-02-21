package com.kstarrain;


import com.kstarrain.constant.BusinessErrorCode;
import com.kstarrain.exception.BusinessException;
import com.kstarrain.utils.DistributedLockUtils;
import com.kstarrain.utils.JedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description: 并发测试  junit不支持多线程测试
 */

@Slf4j
public class ConcurrentTest {

    //商品key
    private static String goodsKey = "concurrent:goods:iphone8";

    //单次抢购数量
    private static int quantity = 1;



    /** 减库存测试 01 */
    @Test
    public void reduceStockByKey_01(){

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
                    jedis.set("concurrent:order:user1" , "买家user1抢购到了" + quantity + "件商品");
                } else {
                    throw new BusinessException(BusinessErrorCode.BUSINESS004);
                }

            } else if (stock == 0) {
                throw new BusinessException(BusinessErrorCode.BUSINESS005);
            } else if (stock < 0) {
                throw new BusinessException(BusinessErrorCode.BUSINESS000);
            }
        } catch (BusinessException e) {
            System.out.println("user1 ---> " + e.getMessage());
        }  catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
    }




    /** 减库存测试 02 */
    @Test
    public void reduceStockByKey_02(){

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
                    jedis.set("concurrent:order:user2" , "买家user2抢购到了" + quantity + "件商品");
                } else {
                    throw new BusinessException(BusinessErrorCode.BUSINESS004);
                }

            } else if (stock == 0) {
                throw new BusinessException(BusinessErrorCode.BUSINESS005);
            } else if (stock < 0) {
                throw new BusinessException(BusinessErrorCode.BUSINESS000);
            }
        } catch (BusinessException e) {
            System.out.println("user1 ---> " + e.getMessage());
        }  catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
    }




    @Test
    public void testDistributedLock01() {

        String KEY = "888";
        String requestId = UUID.randomUUID().toString();
        //是否获取到了锁
        boolean lockable = false;

        Jedis jedis = null;
        try {

            lockable = DistributedLockUtils.tryLock(KEY, requestId, 10000);

            if (lockable){
                System.out.println("获得锁，执行业务逻辑");
            }else {
                System.out.println("未获得锁，请重试");
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (lockable){DistributedLockUtils.releaseLock(KEY,requestId);}
        }
    }





    @Test
    public void testDistributedLock02() {

        String KEY = "888";
        String requestId = UUID.randomUUID().toString();
        //是否获取到了锁
        boolean lockable = false;

        Jedis jedis = null;
        try {

            lockable = DistributedLockUtils.tryLock(KEY, requestId, 10000);

            if (lockable){
                System.out.println("获得锁，执行业务逻辑");
            }else {
                System.out.println("未获得锁，请重试");
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (lockable){DistributedLockUtils.releaseLock(KEY,requestId);}
        }
    }

}
