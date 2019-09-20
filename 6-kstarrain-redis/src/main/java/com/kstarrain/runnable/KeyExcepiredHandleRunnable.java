package com.kstarrain.runnable;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: DongYu
 * @create: 2019-02-17 13:19
 * @description:
 */
@Slf4j
public class KeyExcepiredHandleRunnable implements Runnable{


    //key
    private String key;


    public KeyExcepiredHandleRunnable(String key) {
        this.key = key;
    }

    @Override
    public void run() {
        log.info("处理 key：{} 业务逻辑", key);
    }
}
