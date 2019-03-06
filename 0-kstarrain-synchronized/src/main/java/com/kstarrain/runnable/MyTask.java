package com.kstarrain.runnable;

/**
 * @author: DongYu
 * @create: 2019-03-04 20:46
 * @description:
 */
public class MyTask implements Runnable {

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"==="+i);
        }

    }
}
