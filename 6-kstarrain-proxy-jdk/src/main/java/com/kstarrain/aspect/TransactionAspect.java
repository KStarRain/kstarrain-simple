package com.kstarrain.aspect;

/**
 * @author: DongYu
 * @create: 2019-01-31 12:55
 * @description: 事务切面
 */
public class TransactionAspect {

    public void startTransaction(){
        System.out.println("-----开启事务-----");
    }


    public void commitTransaction(){
        System.out.println("-----提交事务-----");
    }
}
