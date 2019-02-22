package com.kstarrain.test;

import com.kstarrain.job.BusinessJob2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author: DongYu
 * @create: 2019-02-22 13:12
 * @description:
 */
public class CronJobTest {

    public static void main(String[] args) throws SchedulerException {

        //通过SchedulerFactory来获取一个调度器
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        //绑定作业到调度器上
//        bindingScheduler("BusinessJob1", BusinessJob1.class, "*/1 * * * * ?", scheduler);
//        bindingScheduler("BusinessJob2", BusinessJob1.class, "*/5 * * * * ?", scheduler);
        bindingJobToScheduler("BusinessJob2", BusinessJob2.class, "0/10 * * * * ?", scheduler);

        //启动调度器
        scheduler.start();
    }

    /**
     * @param jobKey          作业的唯一标识
     * @param jobClass        作业的具体执行类
     * @param cronExpression  cron表达式
     * @param scheduler       调度器
     * @throws SchedulerException
     */
    private static <T> void bindingJobToScheduler(String jobKey, Class<? extends Job> jobClass, String cronExpression, Scheduler scheduler) throws SchedulerException {

        //引进作业程序
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();

        //设置一个触发器
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

        //将作业程序和触发器设置到调度器中
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
