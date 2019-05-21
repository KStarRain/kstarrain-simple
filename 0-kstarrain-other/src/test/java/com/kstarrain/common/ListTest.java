package com.kstarrain.common;


import com.kstarrain.utils.ListUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class ListTest {


    @Test
    public void test1(){

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add(null);


        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");

        List<String> list3 = new ArrayList<>();
        list3.add(null);
        list3.add(null);

        List<String> intersection = ListUtils.intersection(list1,list2,list3);
        System.out.println(intersection);

    }




}
