package com.kstarrain.thread;

/**
 * @author: DongYu
 * @create: 2019-03-04 20:00
 * @description:
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        //这里就是线程要执行的代码
        for (int i = 0; i < 10; i++) {
            System.out.println(getName()+ "--"+  i);
        }
    }
}
