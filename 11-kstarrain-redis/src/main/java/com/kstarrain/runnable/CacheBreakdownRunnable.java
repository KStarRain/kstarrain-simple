package com.kstarrain.runnable;

import com.kstarrain.controller.GoodsController;
import com.kstarrain.pojo.Goods;
import com.kstarrain.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-02-17 13:19
 * @description: 该类为了模仿多个线程请求单例的controller 获取key的数据
 */
@Slf4j
public class CacheBreakdownRunnable implements Runnable{

    private GoodsController goodsController;

    //用户id
    private String userId;

    //key
    private String key;


    public CacheBreakdownRunnable(GoodsController goodsController, String userId, String key) {
        this.goodsController = goodsController;
        this.userId = userId;
        this.key = key;
    }

    @Override
    public void run() {
        ThreadLocalUtils.setValue("USER_ID",userId);
        List<Goods> allGoods = goodsController.findAllGoods(key);
    }
}
