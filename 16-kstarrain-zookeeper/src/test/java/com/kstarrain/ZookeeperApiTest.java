package com.kstarrain;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * @author: DongYu
 * @create: 2019-11-12 09:59
 * @description: 使用zooKeeper原生API操作
 *               参考文档：https://www.jianshu.com/p/040551605dda
 */
@Slf4j
public class ZookeeperApiTest {

    private static final String ZK_ADDRESSES = "127.0.0.1:2181";
    private static ZooKeeper zkClient = null;


    @Before
    public void init() throws Exception {
        //三个参数分别为连接的zookeeper集群服务器的ip，超时时间，监听器
        zkClient = new ZooKeeper(ZK_ADDRESSES, 2000, new Watcher(){
            //收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
            public void process(WatchedEvent event) {
                log.info("{} --- {}",event.getType(),event.getPath());
            }});
    }


    /**
     * 创建数据节点到zk中
     */
    @Test
    public void add() throws Exception {

        /*
         * 传入四个参数
         * 1、创建的节点
         * 2、节点数据
         * 3、节点的权限，OPEN_ACL_UNSAFE表示内部应用权限
         * 4、节点类型，4种：
         *      持久化节点，
         *      有序的持久化节点， 例如：创建节点/test/sort  最终创建成功的节点为/test/sort0000000000,继续创建的为/test/sort0000000001
         *      临时节点， 客户端可以建立一个临时节点，在会话结束或者会话超时后，zookeeper会自动删除该节点。
         *      有序的的临时节点
         */
        String path = zkClient.create("/test/locksss/", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        log.info(path);
    }


    /**
     * 获取子节点
     */
    @Test
    public void getChildren() throws Exception{

        /*
         * 传入2个参数
         * 1、指定获取哪个节点的孩子
         * 2、是否使用监听器(watcher)，true表示使用以上的监听功能
         */
        List<String> children = zkClient.getChildren("/test",true);
        for (String child : children) {
            log.info(child);
        }
        System.in.read();
    }


    /**
     * 判断节点是否存在
     */
    @Test
    public void exist() throws Exception{

        //一个参数是节点，一个是是否用监听功能,Stat封装了该节点的相关信息比如：czxid，mzxid，ctime，mtime等
        Stat stat = zkClient.exists("/test", false);

        System.out.println(stat != null ? "存在" : "不存在");
    }


    /**
     * 获取节点数据
     */
    @Test
    public void getData() throws Exception{
        byte[] data = zkClient.getData("/test", false, null);
        System.out.println(new String(data));
    }


    /**
     * 修改节点数据
     * @throws Exception
     */
    @Test
    public void update() throws Exception{
        //原 /idea节点的数据为helloworld
        zkClient.setData("/test", "helloworld2222".getBytes(), -1);
    }


    /**
     * @删除节点
     */
    @Test
    public void delete() throws Exception{
        //第一个参数为要删除的节点，第二个参数表示版本，-1表示所有版本
        zkClient.delete("/test",-1);
    }



}
