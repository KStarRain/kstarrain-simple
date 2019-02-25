package com.kstarrain.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author: DongYu
 * @create: 2019-02-22 12:25
 * @description: 业务定时任务(quartz)
 *
 * 1.默认多线程异步执行
 * 2.单个任务时，在上一个调度未完成时，下一个调度时间到时，会另起一个线程开始新的调度。业务繁忙时，一个任务会有多个调度，可能导致数据处理异常。
 *   单个任务时，若需要单线程顺序执行需要加上注解@DisallowConcurrentExecution
 * 3.多个任务时，任务之间没有直接影响，多任务执行的快慢取决于CPU的性能
 *
 */
@Slf4j
@DisallowConcurrentExecution //禁止一个工作任务还没执行完，下一个工作（同jobkey）就开始执行
public class BusinessJob1 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        try {

            log.info("开始执行作业逻辑 " + jobExecutionContext.getJobDetail().getKey().getName()+ " -- " + jobExecutionContext.getJobDetail().getJobClass().getName());
            int a = 5/0;
            log.info("结束执行作业逻辑 " + jobExecutionContext.getJobDetail().getKey().getName()+ " -- " + jobExecutionContext.getJobDetail().getJobClass().getName());

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

}
