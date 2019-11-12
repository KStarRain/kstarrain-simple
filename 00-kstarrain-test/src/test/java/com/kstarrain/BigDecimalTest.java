package com.kstarrain;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author: Dong Yu
 * @create: 2019-09-03 10:19
 * @description:
 */
public class BigDecimalTest {

    @Test
    public void testString() {
        System.out.println(new BigDecimal("1000").multiply(new BigDecimal("0.01")).setScale(0));
    }

}
