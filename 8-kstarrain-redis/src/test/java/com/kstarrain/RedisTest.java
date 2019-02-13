package com.kstarrain;

import com.alibaba.fastjson.JSON;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.JedisPoolUtil;
import com.kstarrain.utils.TestDataUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Tuple;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: DongYu
 * @create: 2019-02-08 15:48
 * @description:
 */
public class RedisTest {

    private Jedis client = new Jedis("127.0.0.1", 6379);


    @Test
    public void testCommon() {

        String KEY = "key_string";

        //设置过期时间
        client.expire(KEY,10);

        //判断key是否存在
        Boolean hasKey = client.exists(KEY);
        System.out.println(hasKey);

        //删除key
        client.del(KEY);

        client.close();
    }
    
    @Test
    public void testString() {

        String KEY = "key_string";

        //设置key-value
        client.set(KEY, "貂蝉");

        //获取key
        String valus = client.get(KEY);
        System.out.println(valus);

        client.close();
    }

    @Test
    public void testStudent(){

        String KEY = "key_student";

        client.set(KEY,JSON.toJSONString(TestDataUtils.createStudent1()));

        String studentJson = client.get(KEY);
        Student student = JSON.parseObject(studentJson, Student.class);
        System.out.println();

        client.close();
    }


    /**
     * 参考文档: https://blog.csdn.net/qq_36898043/article/details/82155202
     *          https://blog.csdn.net/sengmay/article/details/54633297
     */
    @Test
    public void testList() {
        
        ShardedJedis client = JedisPoolUtil.instance().getResource();
        String KEY = "key_list";

        try {
            //一个一个添加： 将值插入到列表头部
//            client.lpush(KEY,"貂蝉");
            //一个一个添加： 将值插入到列表尾部
//            client.rpush(KEY,"吕布");

            List<String> list = new ArrayList<>();
            list.add("貂蝉");
            list.add("吕布");
            client.rpush(KEY,list.toArray(new String[list.size()]));

            List<String> value = client.lrange(KEY, 0, -1);
            System.out.println(value);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 业务操作完成，将连接返回给连接池
            client.close();
        }
    }


    @Test
    public void testSet() {
        ShardedJedis client = JedisPoolUtil.instance().getResource();
        String KEY = "key_set";

        try {
//            client.sadd(KEY,"貂蝉");
//            client.sadd(KEY,"吕布");


            List<String> list = new ArrayList<>();
            list.add("貂蝉");
            list.add("吕布");
            client.sadd(KEY,list.toArray(new String[list.size()]));


            Set<String> value = client.smembers(KEY);

            System.out.println(value);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }


    @Test
    public void testSortedSet() {
        ShardedJedis client = JedisPoolUtil.instance().getResource();
        String KEY = "key_sorted_set";

        try {
            client.zadd(KEY,1.1,"吕布");
            client.zadd(KEY,1.2,"貂蝉");

            Set<String> value = client.zrange(KEY,0,-1);
            System.out.println(value);

            Set<Tuple> tuples = client.zrangeWithScores(KEY, 0, -1);
            if (CollectionUtils.isNotEmpty(tuples)){
                for (Tuple tuple : tuples) {
                    System.out.println(tuple.getScore() + " - " + tuple.getElement());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    @Test
    public void testHashMap() {
        ShardedJedis client = JedisPoolUtil.instance().getResource();
        String KEY = "key_hashmap";

        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("2018/01/01","天津");
            map.put("2018/01/02","上海");
            client.hmset(KEY,map);

            Map<String, String> stringStringMap = client.hgetAll(KEY);

            System.out.println(stringStringMap);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

}
