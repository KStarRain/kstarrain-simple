package com.kstarrain.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * @author: DongYu
 * @create: 2019-11-14 16:13
 * @description:
 */
public class CountDownRunnable implements Runnable {

    private CountDownLatch begin;

    private CountDownLatch end;

    public CountDownRunnable(CountDownLatch begin, CountDownLatch end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {

        try {

            System.out.println(Thread.currentThread().getName() + " start !");
            begin.await();//因为此时已经为0了，所以不阻塞
            System.out.println(Thread.currentThread().getName() + " arrived !");

            end.countDown();//countDown() 并不是直接唤醒线程,当end.getCount()为0时线程会自动唤醒

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
