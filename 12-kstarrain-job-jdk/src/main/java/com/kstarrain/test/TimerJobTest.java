package com.kstarrain.test;

import com.kstarrain.job.TimerTaskJob1;
import com.kstarrain.job.TimerTaskJob2;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;

/**
 * @author: DongYu
 * @create: 2019-02-22 10:15
 * @description: 使用jdk自带的Timer实现定时任务
 *               https://www.cnblogs.com/chenssy/p/3788407.html
 */
@Slf4j
public class TimerJobTest {

    /**
     *  缺点：1.不支持cron表达式
     *       2.如果业务逻辑抛出RuntimeException，Timer会终止该线程内所有任务的运行,一定要自己抓捕异常.
     */
    public static void main(String[] args) {
        log.info("...........服务器启动...........");

        TimerTaskJob1 task1 = new TimerTaskJob1();
        TimerTaskJob2 task2 = new TimerTaskJob2();


        /**
         *  第一个参数：作业程序(执行具体的业务逻辑)
         *  第二个参数：服务启动时 延时n时间后开始执行第一个定时任务
         *  第一个参数：作业程序的执行间隔时间
         */
        new Timer().schedule(task1,0,1000);
        new Timer().schedule(task1,0,1000);
    }
}
