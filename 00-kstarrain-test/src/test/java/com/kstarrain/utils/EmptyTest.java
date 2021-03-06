package com.kstarrain.utils;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.util.*;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class EmptyTest {


    @Test
    public void testString(){

        String a = "#" + null;
        System.out.println(a);
//        String str = null;
////        str = "";
////        str = " ";
////        System.out.println(str.isEmpty());
//        System.out.println("-------------------------");
//        System.out.println(StringUtils.isBlank(str));
    }

    @Test
    public void testList(){
//        List<String> list = null;
//        list = new ArrayList();
//        list.add(null);
////        System.out.println(list.isEmpty());
//        for (String s : list) {
//            System.out.println(s);
//        }
//        System.out.println("-------------------------");
//        System.out.println(CollectionUtils.isEmpty(list));


        System.out.println(CollectionUtils.isEmpty(Collections.emptyList()));

       /* List<Student> list2 = null;
        list2 = new ArrayList();
        list2.add(null);
        for (Student s2 : list2) {
            System.out.println(s2);
        }*/

    }

    @Test
    public void testSet(){
        Set<String> set = null;
//        set = new HashSet<>();
//        set.add("1");
//        System.out.println(set.isEmpty());
        System.out.println("-------------------------");
        System.out.println(CollectionUtils.isEmpty(set));
    }

    @Test
    public void testMap(){
        Map<String,String> map = null;
//        map = new HashMap<>();
//        map.put("1","貂蝉");
//        System.out.println(map.isEmpty());
        System.out.println("-------------------------");
        System.out.println(MapUtils.isEmpty(map));
    }


    @Test
    public void testFor(){
        List<String> list = null;
        list = new ArrayList<>();
        System.out.println(list.size());

        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("==============");

        Map<Object, Object> map = null;
        map = new HashMap();
        System.out.println(map.size());
        Set<Map.Entry<Object, Object>> entries = map.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    @Test
    public void test01(){

        String a = null;
        a.contains("#");
    }


}
