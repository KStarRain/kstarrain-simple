package com.kstarrain.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author: DongYu
 * @create: 2019-02-08 17:25
 * @description:
 */
public class JedisPoolUtil {


    private static ShardedJedisPool shardedJedisPool;

    /**
     * 单例对象
     * @return
     */
    public static ShardedJedisPool instance() {
        if (shardedJedisPool != null) {
            return shardedJedisPool;
        }else {
            shardedJedisPool = getShardedJedisPool();
            return shardedJedisPool;
        }
    }



    private static ShardedJedisPool getShardedJedisPool() {

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
        return new ShardedJedisPool(config, shards);
    }


    /**
     * 应用关闭时，释放连接池资源
     * @param pool
     */
    public void poolDestroy(ShardedJedisPool pool) {
        if (pool != null){
            pool.destroy();
        }
    }

}
