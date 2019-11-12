package com.kstarrain.runnable;

/**
 * @author: DongYu
 * @create: 2019-03-04 20:48
 * @description:
 */
public class MyRunnableTest {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new MyRunnable(),"吕布");
        Thread.sleep(1000l);
        thread.start();
    }
}
