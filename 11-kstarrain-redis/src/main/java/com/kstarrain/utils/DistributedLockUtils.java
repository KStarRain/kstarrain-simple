package com.kstarrain.utils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @author: DongYu
 * @create: 2019-02-20 18:05
 * @description: 基于redis的分布式锁
 *   参考文档: https://wudashan.cn/2017/10/23/Redis-Distributed-Lock-Implement/#%E5%8F%82%E8%80%83%E9%98%85%E8%AF%BB
 */
@Slf4j
public class DistributedLockUtils {

    /** 解锁的lua脚本 */
    public static final String UNLOCK_LUA;

    /** SET IF NOT EXIST 等效于 SETNX */
    public static final String NX = "NX";
    /** 以秒为单位设置 key 的过期时间 */
    public static final String EX = "EX";
    /** 以毫秒秒为单位设置 key 的过期时间 */
    public static final String PX = "PX";

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    public static final String LOCK_PREFIX = "DISTRIBUTED_LOCK_";



    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call('get',KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call('del',KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }


    /**
     * 尝试获取分布式锁
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 过期时间
     * @return           是否获取成功
     */
    public static boolean tryLock(String lockKey, String requestId, int expireTime) {

        Jedis jedis = JedisPoolUtils.getJedis();
        try {
        /*
            第一个为key，我们使用key来当锁，因为key是唯一的。
            第二个为value，我们传的是requestId，很多童鞋可能不明白，有key作为锁不就够了吗，为什么还要用到value？
                 原因就是我们在上面讲到可靠性时，分布式锁要满足第四个条件解铃还须系铃人，通过给value赋值为requestId，
                 我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID().toString()方法生成。
            第三个为nxxx，这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
            第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
            第五个为time，与第四个参数相呼应，代表key的过期时间。
        * */
            String result = jedis.set(LOCK_PREFIX + lockKey, requestId, NX, EX, expireTime);

            return LOCK_SUCCESS.equalsIgnoreCase(result);

        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }



    /**
     * 释放分布式锁
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return          是否释放成功
     */
    public static boolean releaseLock(String lockKey, String requestId) {

        Jedis jedis = JedisPoolUtils.getJedis();

        try {
            // lua语言 首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（解锁）
            Object result = jedis.eval(UNLOCK_LUA, Collections.singletonList(LOCK_PREFIX + lockKey), Collections.singletonList(requestId));

            return RELEASE_SUCCESS.equals(result);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

}
