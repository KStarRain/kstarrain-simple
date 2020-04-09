package com.kstarrain.utils.app;

import com.kstarrain.utils.HighPerformanceZkLock;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: DongYu
 * @create: 2019-02-16 19:12
 * @description: zk分布式锁测试
 */
@Slf4j
public class ZkLockRequest {


    /** 高并发抢购测试  */
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(0);
        countDownLatch.countDown();
        System.out.println(countDownLatch.getCount());


        ExecutorService executor = Executors.newFixedThreadPool(100);
        // 测试一万人同时抢购
        for (int i = 1; i <= 10; i++) {
            executor.execute(new LockRunnable(i));
        }
        executor.shutdown();
    }



    static class LockRunnable implements Runnable{

        private int num;

        public LockRunnable(int num) {
            this.num = num;
        }

        @Override
        public void run() {
//            Lock zkLock = new SimpleZkLock("pms");
            HighPerformanceZkLock zkLock = new HighPerformanceZkLock();
            zkLock.getLock();

            //模拟业务操作
            try {
                Thread.sleep(500);
                log.info(Thread.currentThread().getName()+" 用户"+num+ "-------抢到了一个商品");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                zkLock.releaseLock();
            }
        }

    }

}
