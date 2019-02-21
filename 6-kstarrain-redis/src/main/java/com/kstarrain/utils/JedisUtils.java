package com.kstarrain.utils;

import redis.clients.jedis.Jedis;

/**
 * @author: DongYu
 * @create: 2019-02-21 17:11
 * @description:
 */
public class JedisUtils {


    private interface RedisCommand<T>{
        T run(Jedis jedis);
    }

    private static <T> T execute(RedisCommand<T> jedisCommand){
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedisCommand.run(jedis);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }

    }

    public static String set(String key,String value) {
        return execute(new RedisCommand<String>() {
            @Override
            public String run(Jedis jedis) {
                return jedis.set(key,value);
            }
        });
    }


    public static String get(String key) {
        return execute(new RedisCommand<String>() {
            @Override
            public String run(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }
}
