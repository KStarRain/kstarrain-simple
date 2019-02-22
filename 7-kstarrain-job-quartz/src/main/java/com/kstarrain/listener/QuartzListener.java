package com.kstarrain.listener;

import com.kstarrain.job.BusinessJob1;
import com.kstarrain.job.BusinessJob2;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author: DongYu
 * @create: 2019-02-22 16:46
 * @description: quartz定时任务监听器，tomcat启动时加载所有作业
 */
@Slf4j
public class QuartzListener implements ServletContextListener {

    private Scheduler scheduler;
    private String GROUP = "kstarrain-job-quartz";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {


        try {
            log.info("QuartzListener init start");

            //通过SchedulerFactory来获取一个调度器
            scheduler = new StdSchedulerFactory().getScheduler();

            //绑定作业到调度器上
            bindingJobToScheduler("BusinessJob1", BusinessJob1.class, "BusinessJob1任务描述","*/5 * * * * ?", scheduler);
            bindingJobToScheduler("BusinessJob2", BusinessJob2.class, "BusinessJob2任务描述","0/10 * * * * ?", scheduler);

            //启动调度器
            scheduler.start();

            log.info("QuartzListener init success");
        } catch (SchedulerException e) {
            log.error("QuartzListener init fail");
            log.error(e.getMessage(),e);
        }


    }

    /**
     * @param jobKey          作业的唯一标识
     * @param jobClass        作业的具体执行类
     * @param cronExpression  cron表达式
     * @param scheduler       调度器
     * @throws SchedulerException
     */
    private  <T> void bindingJobToScheduler(String jobKey, Class<? extends Job> jobClass, String jobDescription,String cronExpression, Scheduler scheduler) throws SchedulerException {

        //引进作业程序
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey,GROUP).withDescription(jobDescription).build();

        //设置一个触发器
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("TRIGGER_"+jobKey,GROUP).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

        //将作业程序和触发器设置到调度器中
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }



    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            if (scheduler != null){
                scheduler.shutdown();
            }
            log.info("QuartzListener destroyed success");
        } catch (SchedulerException e) {
            log.error("QuartzListener destroyed fail");
            log.error(e.getMessage(),e);
        }

    }
}
