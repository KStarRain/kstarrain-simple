package com.kstarrain.test;

/**
 * @author: DongYu
 * @create: 2019-03-05 13:44
 * @description:
 */
public class TestB extends TestA{
    static int a = 0;

    {
        System.out.println("hello B");
    }

    public TestB(){
        System.out.println("TestB constructor!");
    }

    static {
        a = 1;
        System.out.println("TestB static" + a);
    }
}
