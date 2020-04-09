package com.kstarrain.utils;


import org.junit.Test;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class VersionUtilsTest {


    @Test
    public void testString(){
        int i = VersionUtils.compareVersion("2", "2.1");
        System.out.println(i);
    }



}
