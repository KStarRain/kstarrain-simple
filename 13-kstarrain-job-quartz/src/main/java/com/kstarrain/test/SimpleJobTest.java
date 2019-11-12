package com.kstarrain.test;

import com.kstarrain.job.BusinessJob1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author: DongYu
 * @create: 2019-02-22 13:12
 * @description:
 */
public class SimpleJobTest {

    public static void main(String[] args) throws SchedulerException {

        //引进作业程序
        JobDetail businessJob1 = JobBuilder.
                                    // 作业程序类
                                    newJob(BusinessJob1.class).
                                    // 作业程序的唯一标识
                                    withIdentity("BusinessJob1").
                                  build();
        //设置一个触发器
        Trigger businessJob11 = TriggerBuilder.newTrigger().
                                    // 触发器唯一标识
                                    withIdentity("BusinessJob1").
                                    // 作业首次启动时间(延迟1秒执行)
                                    startAt(new Date(System.currentTimeMillis() + 1000)).
                                    // 设置具体调度规则 每隔一秒执行 并一直重复
                                    withSchedule(
                                            //简单调度规则
                                            SimpleScheduleBuilder.simpleSchedule().
                                            //单个作业间隔时间
                                            withIntervalInSeconds(1).
                                            //一致重复
                                            repeatForever()
                                                ).
                                build();

        //通过SchedulerFactory来获取一个调度器
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        //将作业和触发器设置到调度器中
        scheduler.scheduleJob(businessJob1, businessJob11);
        //启动调度器
        scheduler.start();
    }
}
