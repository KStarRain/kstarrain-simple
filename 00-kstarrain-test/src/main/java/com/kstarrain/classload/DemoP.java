package com.kstarrain.classload;

/**
 * @author: DongYu
 * @create: 2019-03-05 21:42
 * @description:
 */
public class DemoP {

    static int num = 0;

    public DemoP() {
        num++;
        System.out.println("加载父类构造函数       num = " + num);         //4 加载父类构造方法
    }

    static {
        num++;
        System.out.println("加载父类static代码块   num = " + num);    //1 加载父类的静态代码块
    }

    {
        num++;
        System.out.println("加载父类匿名代码块      num = " + num);       //3 加载父类代码块
    }
}
