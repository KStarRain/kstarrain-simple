package com.kstarrain;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.*;
import org.apache.zookeeper.Watcher;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * @author: DongYu
 * @create: 2019-11-12 09:59
 * @description: 使用zkClient操作
 *               文档： https://www.cnblogs.com/qlqwjy/p/10545166.html
 */
@Slf4j
public class ZkClientTest {

    private static final String ZK_ADDRESSES = "127.0.0.1:2181";
    private static ZkClient zkClient = null;


    @Before
    public void init(){
        zkClient = new ZkClient(new ZkConnection(ZK_ADDRESSES), 2000);
    }


    /**
     * 创建数据节点到zk中
     */
    @Test
    public void add(){

//        log.info(zkClient.create("/test2/003", "hahaha", CreateMode.PERSISTENT));

//        // 递归创建节点，只是节点的值为空
//        zkClient.createPersistent("/test2", 1234);

        zkClient.createPersistent("/test2", true);
    }


    /**
     * 获取子节点
     */
    @Test
    public void getChildren(){
        List<String> children = zkClient.getChildren("/test2");
        for (String child : children) {
            log.info(child);
        }
    }


    /**
     * 判断节点是否存在
     */
    @Test
    public void exist(){
        boolean exists = zkClient.exists("/test2");
        System.out.println(exists);
    }


    /**
     * 获取节点数据
     */
    @Test
    public void queryData(){
        Object data = zkClient.readData("/test2");
        System.out.println(data);
    }


    /**
     * 修改节点数据
     */
    @Test
    public void update(){
        List<Object> data = new ArrayList<>();
        data.add("吕布");
        zkClient.writeData("/test2/003", data);
    }


    /**
     * 删除节点
     */
    @Test
    public void delete(){

//        System.out.println(zkClient.delete("/test2"));

        // 递归删除
        System.out.println(zkClient.deleteRecursive("/test2"));
    }


    /**
     * 监听节点数据变化
     */
    @Test
    public void watchData() throws IOException, InterruptedException {

        String node = "/test2";

        IZkDataListener iZkDataListener = new IZkDataListener() {

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                log.warn("监听到 节点:{} 已被删除", dataPath);
            }

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                log.warn("监听到 节点:{} 的数据 更改为 {}", dataPath, data);
            }
        };
        zkClient.subscribeDataChanges(node, iZkDataListener);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

        zkClient.unsubscribeDataChanges(node, iZkDataListener);
    }


    /**
     * 监听子节点变化(只能监听子节点，孙节点无效)
     */
    @Test
    public void watchChild() throws IOException, InterruptedException {

        String node = "/test2";

        IZkChildListener iZkChildListener = new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

                if (currentChilds == null) {
                    log.warn("监听到 节点:{} 已经被删除", parentPath);
                } else {
                    log.warn("监听到 节点:{} 的子节点更改为 {}", parentPath, currentChilds);
                }
            }
        };

        zkClient.subscribeChildChanges(node, iZkChildListener);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

        zkClient.unsubscribeChildChanges(node, iZkChildListener);
    }



    /**
     * 监听服务连接状态,可手动启动关闭zookeeper查看触发
     */
    @Test
    public void watchStateChanges() throws IOException, InterruptedException {

        IZkStateListener iZkStateListener = new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                if (state == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("连接zookeeper成功");
                } else if (state == Watcher.Event.KeeperState.Disconnected) {
                    System.out.println("zookeeper断开");
                } else {
                    System.out.println("other" + state);
                }
            }

            @Override //连接关闭,过了session的设置时间,再连接session就会重置,触发监听
            public void handleNewSession() throws Exception {
                System.out.println("newsession");
            }

            @Override
            public void handleSessionEstablishmentError(Throwable throwable) throws Exception {


            }
        };
        zkClient.subscribeStateChanges(iZkStateListener);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

        zkClient.unsubscribeStateChanges(iZkStateListener);
    }


}
