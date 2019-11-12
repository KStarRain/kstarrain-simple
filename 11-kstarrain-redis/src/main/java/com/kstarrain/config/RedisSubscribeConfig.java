package com.kstarrain.config;

import com.kstarrain.listener.RedisKeyExpiredListener;
import com.kstarrain.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;

/**
 * @author: Dong Yu
 * @create: 2019-09-19 15:39
 * @description:  https://www.jianshu.com/p/f5f81b2b2005
 */
public class RedisSubscribeConfig {

    public static void main(String[] args) {

        String CHANNEL_KEY_EXPIRED_EVENT = "__keyevent@0__:expired";

        String CHANNEL_TEST              = "test_channel";

        Jedis jedis = JedisPoolUtils.getJedis();

        // 使用subscriber订阅，这一句之后，线程进入订阅模式，阻塞。
        jedis.subscribe(new RedisKeyExpiredListener(), CHANNEL_KEY_EXPIRED_EVENT, CHANNEL_TEST);
    }
}
