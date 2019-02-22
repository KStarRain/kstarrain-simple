package com.kstarrain.job;

import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * @author: DongYu
 * @create: 2019-02-22 10:15
 * @description: 作业程序(使用jdk自带的Timer)
 */
@Slf4j
public class TimeerTaskJob2 extends TimerTask{

    @Override
    public void run() {
        try {
            log.info(Thread.currentThread().getName() + "开始执行定时任务Timer2");
            Thread.sleep(5000L);
            log.info(Thread.currentThread().getName() + "结束执行定时任务Timer2");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
