package com.kstarrain.runnable;

/**
 * @author: DongYu
 * @create: 2019-03-04 20:48
 * @description:
 */
public class MyTaskTest {

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask,"吕布");
        Thread.sleep(1000l);
        thread.start();
    }
}
