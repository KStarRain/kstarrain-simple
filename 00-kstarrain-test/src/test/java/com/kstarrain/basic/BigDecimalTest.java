package com.kstarrain.basic;


import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class BigDecimalTest {


    @Test
    public void test01(){

        String a = "512.146";

        System.out.println(new BigDecimal(a));
        System.out.println(new BigDecimal(a).setScale(2, BigDecimal.ROUND_DOWN ));
        System.out.println(new BigDecimal(a).setScale(2, RoundingMode.DOWN));
        System.out.println(new BigDecimal(a).setScale(2, RoundingMode.HALF_UP));
        System.out.println(new BigDecimal(a).setScale(2, RoundingMode.HALF_DOWN));

        int i = BigDecimal.valueOf(new BigDecimal(a).doubleValue()).movePointRight(2).intValue();
        System.out.println(i);

    }



    @Test
    public void test02() {
        System.out.println(new BigDecimal("1000").multiply(new BigDecimal("0.01")).setScale(0));
    }


    @Test
    public void test03() {

//        System.out.println(new BigDecimal("-1").compareTo(BigDecimal.ZERO));

        System.out.println(BigDecimal.valueOf(20).setScale(2));
//        System.out.println(MoneyFormatUtils.format(0.0001));
//        System.out.println(MoneyFormatUtils.format(null));
//        System.out.println(MoneyFormatUtils.format(1.2));
//        System.out.println(MoneyFormatUtils.format(-1.2));
    }


}
