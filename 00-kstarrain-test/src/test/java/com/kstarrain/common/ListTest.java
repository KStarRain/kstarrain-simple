package com.kstarrain.common;


import com.alibaba.fastjson.JSON;
import com.kstarrain.utils.ListUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class ListTest {


    @Test
    public void test1(){

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");

        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");

        List<String> list3 = new ArrayList<>();
        list3.add("1");
        list3.add("2");
        list3.add("3");

        List<String> list4 = new ArrayList<>();

        List<String> list5 = null;

        List<String> intersection = ListUtils.intersection(list1,list2,list3,list4,list5);
        System.out.println(intersection);

        if (CollectionUtils.isNotEmpty(intersection)){

            //性能考虑，为了之后不用list.contains方法
            Map<String, String> tempMap = new HashMap<>();

            for (String s : intersection) {
                tempMap.put(s,s);
            }

            //用倒序删下标的方法去重不会漏掉某些元素
            for (int i = list.size() - 1; i >= 0; i--) {
                String abbr = list.get(i);
                if (!abbr.equals(tempMap.get(abbr))){
                    list.remove(i);
                }
            }
        }

        System.out.println(list);



    }

    @Test
    public void test2(){

        int[] x = new int[10];
        System.out.println(x[0]);

    }

    @Test
    public void test3(){

        List<String> result = new ArrayList<>();
        result.addAll(new ArrayList<>());

        List<String> result2 = new ArrayList<>();
        result2.add("1234");
        result.addAll(result2);

        System.out.println(JSON.toJSONString(result));

    }


}
