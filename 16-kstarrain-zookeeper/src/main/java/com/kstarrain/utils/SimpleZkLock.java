package com.kstarrain.utils;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * @author: DongYu
 * @create: 2019-11-14 09:47
 * @description: 简单的分布式锁的实现
 */
@Slf4j
public class SimpleZkLock{

    //zk地址和端口
    private static final String ZK_ADDRESSES = "127.0.0.1:2181";
    private ZkClient zkClient = new ZkClient(ZK_ADDRESSES, 1000);


    private static final String PARENT_PATH = "/simple_lock";

    private String LOCK;
    private CountDownLatch countDownLatch;


    public SimpleZkLock(String lock) {
        this.LOCK = PARENT_PATH + "/" + lock;
        zkClient.createPersistent(PARENT_PATH, true);
    }

    public void getLock() {
        if (!tryLock()) {
            //等待获取锁
            waitLock();
            //递归重新获取锁
            getLock();
        }
    }


    //直接创建临时节点，如果创建成功，则表示获取了锁,创建不成功则处理异常
    private boolean tryLock() {

        if (null == zkClient) return false;

        try {
            zkClient.createEphemeral(LOCK);
            log.info(Thread.currentThread().getName()+"-------获取锁成功");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void waitLock() {
        //监听器
        IZkDataListener iZkDataListener = new IZkDataListener() {
            //节点被删除回调
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
            //节点改变被回调
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                // TODO Auto-generated method stub

            }
        };
        zkClient.subscribeDataChanges(LOCK, iZkDataListener);
        //如果存在则阻塞
        if (zkClient.exists(LOCK)) {
            log.info(Thread.currentThread().getName()+"-获取锁失败 等待获取锁...");
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //删除监听
        zkClient.unsubscribeDataChanges(LOCK, iZkDataListener);
    }


    public void releaseLock() {
        if (null != zkClient) {
            //删除节点
            zkClient.delete(LOCK);
            zkClient.close();
            log.info(Thread.currentThread().getName()+"-------释放锁");
        }

    }



}
