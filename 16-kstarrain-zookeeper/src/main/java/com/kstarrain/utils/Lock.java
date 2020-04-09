package com.kstarrain.utils;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author: DongYu
 * @create: 2019-11-13 16:04
 * @description:  参考文档：http://www.pianshen.com/article/4687316303/
 */

@Slf4j
public abstract class Lock {

    //zk地址和端口
    public static final String ZK_ADDRESSES = "127.0.0.1:2181";
    //超时时间
    public static final int SESSION_TIMEOUT = 1000;
    //创建zk
    protected ZkClient zkClient = new ZkClient(ZK_ADDRESSES, SESSION_TIMEOUT);


    /**
     * 可以认为是模板模式，两个子类分别实现它的抽象方法
     * 1，简单的分布式锁
     * 2，高性能分布式锁
     */
    public void getLock() {
        if (!tryLock()) {
            //等待获取锁
            waitLock();
            //递归重新获取锁
            getLock();
        }
    }
    public abstract boolean tryLock();

    public abstract void waitLock();

    public abstract void releaseLock();


}
