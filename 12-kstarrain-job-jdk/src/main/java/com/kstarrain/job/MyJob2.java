package com.kstarrain.job;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: DongYu
 * @create: 2019-02-22 11:03
 * @description:
 */
@Slf4j
public class MyJob2 implements Runnable{

    @Override
    public void run() {
        try {
            log.info(Thread.currentThread().getName()  + "开始执行作业逻辑ScheduledExecutor2");
            Thread.sleep(5000L);
            log.info(Thread.currentThread().getName()  + "结束执行作业逻辑ScheduledExecutor2");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
