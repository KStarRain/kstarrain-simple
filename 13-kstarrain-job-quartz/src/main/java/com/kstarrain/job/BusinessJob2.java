package com.kstarrain.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author: DongYu
 * @create: 2019-02-22 12:25
 * @description: 业务定时任务(quartz)
 */
@Slf4j
@DisallowConcurrentExecution //禁止一个工作任务还没执行完，下一个工作（同jobkey）就开始执行
public class BusinessJob2 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        try {

            log.info("开始执行作业逻辑 " + jobExecutionContext.getJobDetail().getKey().getName()+ " -- " + jobExecutionContext.getJobDetail().getJobClass().getName());
            Thread.sleep(7000L);
            log.info("结束执行作业逻辑 " + jobExecutionContext.getJobDetail().getKey().getName()+ " -- " + jobExecutionContext.getJobDetail().getJobClass().getName());

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

}
