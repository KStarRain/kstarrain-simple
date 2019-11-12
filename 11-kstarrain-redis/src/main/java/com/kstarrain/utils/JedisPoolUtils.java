package com.kstarrain.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author: DongYu
 * @create: 2019-02-08 17:25
 * @description:
 */
@Slf4j
public class JedisPoolUtils {

    private static JedisPool jedisPool;

    private static ShardedJedisPool shardedJedisPool;



    /**
     * 初始化单机连接池(在多线程环境同步)
     */
    private static synchronized void initJedisPool() {

        if (jedisPool != null){return;}

        ResourceBundle bundle = ResourceBundle.getBundle("properties/redis");

        // 生成连接池配置信息
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(bundle.getString("redis.maxTotal"))); // 最大连接数
        config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.maxWaitMillis")));// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.testOnBorrow")));// 在获取连接的时候检查有效性, 默认false,性能考虑，正式环境为false

        List<JedisShardInfo> shards = new ArrayList<>();

        String host = bundle.getString("redis.host");
        if (StringUtils.isBlank(host)){
            throw new RuntimeException("key(redis.host) is not allowed to be null");
        }

        String portStr = bundle.getString("redis.port");
        if (StringUtils.isBlank(portStr)){
            throw new RuntimeException("key(redis.port) is not allowed to be null");
        }
        int port = Integer.valueOf(portStr);


        // 在应用初始化的时候生成连接池
        jedisPool = new JedisPool(config, host, port);
    }


    /**
     * 初始化分片连接池(在多线程环境同步)
     */
    private static synchronized void initShardedJedisPool() {

        if (shardedJedisPool != null){return;}

        ResourceBundle bundle = ResourceBundle.getBundle("properties/redis");

        // 生成连接池配置信息
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(bundle.getString("redis.maxTotal"))); // 最大连接数
        config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.maxWaitMillis")));// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.testOnBorrow")));// 在获取连接的时候检查有效性, 默认false,性能考虑，正式环境为false

        List<JedisShardInfo> shards = new ArrayList<>();

        String addressesStr = bundle.getString("redis.addresses");

        if (StringUtils.isBlank(addressesStr)){
            throw new RuntimeException("key(redis.addresses) is not allowed to be null");
        }

        String[] addresses = addressesStr.split(",");
        for (String address : addresses) {
            String[] hostPort = address.split(":");
            shards.add(new JedisShardInfo(hostPort[0],Integer.valueOf(hostPort[1])));
        }

        // 在应用初始化的时候生成连接池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }



    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static Jedis getJedis() {
        if (jedisPool == null) {
            initJedisPool();
        }
        return jedisPool.getResource();
    }

    /**
     * 获取一个ShardedJedis 对象
     *
     * @return
     */
    public static ShardedJedis getShardedJedis() {
        if (shardedJedisPool == null) {
            initShardedJedisPool();
        }
        return shardedJedisPool.getResource();
    }


    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public static void closePipelined(Pipeline pipelined) {
        if (pipelined != null) {
            try {
                pipelined.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
    }



}
