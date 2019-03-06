package com.kstarrain;

import com.alibaba.fastjson.JSON;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.DistributedLockUtils;
import com.kstarrain.utils.JedisPoolUtils;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * @author: DongYu
 * @create: 2019-02-08 15:48
 * @description:
 */
@Slf4j
public class RedisTest {


    /** 测试通用命令(设置key有效时间，判断key是否存在，删除key) */
    @Test
    public void testCommon() {
        String KEY = "key_string";

        Jedis jedis = null;
        try {
            jedis = new Jedis("127.0.0.1", 6379);

            //设置过期时间
            jedis.expire(KEY,10);

            //判断key是否存在
//            Boolean hasKey = jedis.exists(KEY);
//            System.out.println(hasKey);

            //删除key
            jedis.del(KEY);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
    }


    /** 测试string */
    @Test
    public void testString() {
        String KEY = "key_string";

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            //设置key-value
            jedis.set(KEY, "貂蝉");

//            //SETNX 是『SET if Not eXists』(如果不存在，则 SET)
//            Long count = jedis.setnx(KEY, "吕布");
//            if (count == 0){
//                System.out.println("设置吕布失败");
//            }else {
//                System.out.println("设置吕布成功");
//            }
//
//
            //获取key
//            String valus = jedis.get(KEY);
//            System.out.println(valus);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    /** 测试string */
    @Test
    public void testStudent(){
        String KEY = "key_student";

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

//            jedis.set(KEY,JSON.toJSONString(TestDataUtils.createStudent1()));

            String studentJson = jedis.get(KEY);
            System.out.println(studentJson);
            Student student = JSON.parseObject(studentJson, Student.class);
            System.out.println(student);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    /**
     *  测试list
     *  参考文档: https://blog.csdn.net/qq_36898043/article/details/82155202
     *          https://blog.csdn.net/sengmay/article/details/54633297
     */
    @Test
    public void testList() {
        String KEY = "key_list";

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            //一个一个添加： 将值插入到列表头部
//            jedis.lpush(KEY,"貂蝉");
            //一个一个添加： 将值插入到列表尾部
//            jedis.rpush(KEY,"吕布");

            List<String> list = new ArrayList<>();
            list.add("貂蝉");
            list.add("吕布");
            list.add("吕布");
            jedis.rpush(KEY,list.toArray(new String[list.size()]));

            List<String> value = jedis.lrange(KEY, 0, -1);
            System.out.println(value);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    /** 测试set */
    @Test
    public void testSet() {
        String KEY = "key_set";

        List<String> values = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            values.add("貂蝉" + i);
        }

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            long start1 = System.currentTimeMillis();
            for (String value : values) {
                jedis.sadd(KEY,value);
            }
            long end1 = System.currentTimeMillis();
            System.out.println("循环插入 " + values.size() + " 条数据共花费了 " +(end1-start1) +" 毫秒");


            long start2 = System.currentTimeMillis();
            jedis.sadd(KEY,values.toArray(new String[values.size()]));
            long end2 = System.currentTimeMillis();
            System.out.println("批量插入 " + values.size() + " 条数据共花费了 " +(end2-start2) +" 毫秒");


            Set<String> value = jedis.smembers(KEY);
            System.out.println(value);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    /** 测试sort set */
    @Test
    public void testSortedSet() {
        String KEY = "key_sorted_set";

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            jedis.zadd(KEY,1.1,"吕布");
            jedis.zadd(KEY,1.2,"貂蝉");

            Set<String> value = jedis.zrange(KEY,0,-1);
            System.out.println(value);

            Set<Tuple> tuples = jedis.zrangeWithScores(KEY, 0, -1);
            if (CollectionUtils.isNotEmpty(tuples)){
                for (Tuple tuple : tuples) {
                    System.out.println(tuple.getScore() + " - " + tuple.getElement());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    /** 测试hashmap */
    @Test
    public void testHashMap() {
        String KEY = "key_hashmap";

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            HashMap<String, String> map = new HashMap<>();
            map.put("2018/01/01","天津");
            map.put("2018/01/02","上海");
            jedis.hmset(KEY,map);

//            Map<String, String> values = jedis.hgetAll(KEY);
//            System.out.println(values);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    /** 测试增量同步set*/
    @Test
    public void testSyncSet() {
        String KEY = "key_set";

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            /** ----- 造数据 start ----- */
            Collection<String> oldData = new ArrayList<>();
            oldData.add("貂蝉");
            oldData.add("妲己");
            jedis.sadd(KEY,oldData.toArray(new String[oldData.size()]));

            Collection<String> newValues = new ArrayList<>();
            newValues.add("张飞");
            newValues.add("吕布");
            /** ----- 造数据 end  ----- */


            Set<String> oldValues = jedis.smembers(KEY);
            // Don't have old data
            if (CollectionUtils.isEmpty(oldValues)) {
                jedis.sadd(KEY,oldValues.toArray(new String[oldValues.size()]));
                return;
            }

            // 增加的值
            List<String> addValues = new ArrayList<>();
            addValues.addAll(newValues);
            addValues.removeAll(oldValues);

            // 减少的值
            List<String> minusValues = new ArrayList<>();
            minusValues.addAll(oldValues);
            minusValues.removeAll(newValues);

            // do add
            if (CollectionUtils.isNotEmpty(addValues)) {
                jedis.sadd(KEY,addValues.toArray(new String[addValues.size()]));
            }
            // do minus
            if (CollectionUtils.isNotEmpty(minusValues)) {
                jedis.srem(KEY,minusValues.toArray(new String[minusValues.size()]));
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /** 测试redis管道 */
    @Test
    public void testPipeline() {
        String KEY = "key_set";

        List<String> values = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            values.add("貂蝉" + i);
        }

        Jedis jedis = null;
        Pipeline pipelined = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            long start1 = System.currentTimeMillis();
            for (String value : values) {
                jedis.sadd(KEY,value);
            }
            long end1 = System.currentTimeMillis();
            System.out.println("循环单次插入 " + values.size() + " 条数据共花费了 " +(end1-start1) +" 毫秒");


            /** 管道技术 */
            long start2 = System.currentTimeMillis();
            pipelined = jedis.pipelined();
            for (String value : values) {
                pipelined.sadd(KEY,value);
            }
            // 同步获取所有的回应
            pipelined.sync();
            long end2 = System.currentTimeMillis();
            System.out.println("管道技术插入 " + values.size() + " 条数据共花费了 " +(end2-start2) +" 毫秒");

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closePipelined(pipelined);
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    /** 测试redis事务
     *  https://www.cnblogs.com/liuchuanfeng/p/7190654.html
     * */
    @Test
    public void testTransaction01() {
        String KEY = "key_set";

        String KEY_HASHMAP = "key_hashmap";

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            //该命令用户高并发时的乐观锁 监视key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
            jedis.watch(KEY);

            //开启事务
            Transaction tx = jedis.multi();

            tx.sadd(KEY, "貂蝉");
//
//            int a = 5/0;

//            tx.sadd(KEY, "吕布");

            //提交事务
            List<Object> exec = tx.exec();
            if (exec == null){
                System.out.println("事务提交失败，原因为提交前key被其他用户修改");
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }

    }


    @Test
    public void testTransaction02() {
        String KEY = "key_set";

        String KEY_HASHMAP = "key_hashmap";

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            //该命令用户高并发时的乐观锁 监视key，当事务执行之前这个key发生了改变，事务会被中断，事务exec返回结果为null
            jedis.watch(KEY);

            //开启事务
            Transaction tx = jedis.multi();

            tx.sadd(KEY, "吕布");

            //提交事务
            List<Object> exec = tx.exec();
            if (exec == null){
                System.out.println("事务提交失败，原因为提交前key被其他用户修改");
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }

    }




}
