package com.kstarrain.thread;

/**
 * @author: DongYu
 * @create: 2019-03-04 19:54
 * @description:
 */
public class MyThreadTest {

    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        thread1.setName("貂蝉");
        thread1.run();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(i);
//        }

    }
}
