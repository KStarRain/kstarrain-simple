package com.kstarrain.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * @author: DongYu
 * @create: 2019-11-14 16:11
 * @description:
 */
public class CountDownTest {

    public static void main(String[] args) {

        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(2);

        for(int i=1; i<=2; i++){
            Thread thread = new Thread(new CountDownRunnable(begin, end),String.valueOf(i));
            thread.start();
        }

        try{
            System.out.println("the race begin");
            begin.countDown();
//            System.out.println(end.getCount());
            end.await();//await() 方法具有阻塞作用，也就是说主线程在这里暂停
            System.out.println("the race end");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
