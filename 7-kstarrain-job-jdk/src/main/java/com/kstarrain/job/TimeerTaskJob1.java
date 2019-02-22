package com.kstarrain.job;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: DongYu
 * @create: 2019-02-22 10:15
 * @description: 作业程序(使用jdk自带的Timer)
 */
@Slf4j
public class TimeerTaskJob1 extends TimerTask{

    @Override
    public void run() {
        try {
            log.info(Thread.currentThread().getName() + "开始执行作业逻辑 TimeerTask 1");
//            int a = 5/0;
            log.info(Thread.currentThread().getName() + "结束执行作业逻辑 TimeerTask 1");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
