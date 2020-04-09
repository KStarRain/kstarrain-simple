package com.kstarrain.basic;

import org.junit.Test;

/**
 * @author: Dong Yu
 * @create: 2019-09-11 12:54
 * @description:
 */
public class NumTest {

    @Test
    public void test1() {

        Integer a = new Integer(5);
        Integer b = new Integer(5);

        Integer c = 5;
        Integer d = 5;

        Integer e = 128;
        Integer f = 128;

        int g = 128;



        System.out.println(a == b);
        System.out.println(b == c);
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(f == g);
    }

    @Test
    public void test2() {

        Long a = 1000l;
        Long b = null;

        System.out.println(a.equals(b));
    }

    @Test
    public void test3() {

        Integer a = 3000009;
        Integer b = 3000009;

        System.out.println(a.equals(b));
    }
}
