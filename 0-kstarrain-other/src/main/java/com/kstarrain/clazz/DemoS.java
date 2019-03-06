package com.kstarrain.clazz;

/**
 * @author: DongYu
 * @create: 2019-03-05 21:42
 * @description:
 */
public class DemoS extends DemoP {

    public DemoS() {                                 //6 加载子类构造方法
        num++;
        System.out.println("加载子类构造函数       num = " + num);
    }

    static {                                          //5 加载子类代码块
        num++;
        System.out.println("加载子类static代码块   num = " + num);
    }

    {
        num++;
        System.out.println("加载子类匿名代码块      num = " + num);        //2 加载子类的静态代码块
    }
}
