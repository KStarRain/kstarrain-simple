package com.kstarrain.test;

import com.kstarrain.job.MyJob1;
import com.kstarrain.job.MyJob2;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: DongYu
 * @create: 2019-02-22 11:03
 * @description:
 */
@Slf4j
public class ScheduledExecutorServiceTest {


    /**
     *  缺点：1.不支持cron表达式
     *       2.如果业务逻辑抛出RuntimeException，会终止该线程内所有任务的运行，一定要自己抓捕异常.
     */
    public static void main(String[] args) {
        log.info("...........服务器启动...........");

        MyJob1 task1 = new MyJob1();
        MyJob2 task2 = new MyJob2();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(task1,0,1,TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(task2,0,1,TimeUnit.SECONDS);
    }
}
