package com.kstarrain;


import java.util.HashMap;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2019-02-19 14:35
 * @description: https://blog.csdn.net/hewenbo111/article/details/80487252
 */
public class ThreadLocalTest extends Thread{

//    private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal();

    private static ThreadLocal<Map<String,Object>> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--主线程开启");
        Map<String, Object> threadLocalData = threadLocal.get();
        if(threadLocalData == null){
            threadLocalData = new HashMap<>();
            threadLocal.set(threadLocalData);
        }
        threadLocalData.put("THREADLOCAL_USER_INFO","貂蝉");

        System.out.println("--主线程获取本地变量值：" + threadLocal.get());

        new ThreadLocalTest().start();

        //主线程休眠3s
        Thread.sleep(3000);
        System.out.println("--主线程结束s");


    }

    @Override
    public void run() {
        System.out.println("----子线程开启。。。。");

        System.out.println("----子线程获取本地变量值：" + threadLocal.get());

        System.out.println("----子线程结束。。。。");

    }
}
