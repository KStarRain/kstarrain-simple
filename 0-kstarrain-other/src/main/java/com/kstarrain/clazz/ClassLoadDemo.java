package com.kstarrain.clazz;

/**
 * @author: DongYu
 * @create: 2019-03-05 21:11
 * @description:
 */
public class ClassLoadDemo {                             //第一步,准备加载类

    static int num = 0;                                  //第二步，静态变量和静态代码块的加载顺序由编写先后决定

    {
        num ++;
        System.out.println("匿名代码块   num = " + num);   //第四步,加载匿名代码块
    }

    int a = 5;                                           //第五步,加载变量  

    static {                                             //第三步，静态块，然后执行静态代码块
        num ++;
        System.out.println("static代码块 num = " + num);
    }

    static void run(){                                   //静态方法，调用的时候才加载,注意看
        num ++;
        System.out.println("静态方法     num = " + num);
    }

    public ClassLoadDemo() {                             //第六步,最后加载构造函数，完成对象的建立
        num ++;
        System.out.println("无参构造函数  num = " + num);
    }
}
