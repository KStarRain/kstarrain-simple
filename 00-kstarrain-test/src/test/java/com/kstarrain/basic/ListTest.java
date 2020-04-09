package com.kstarrain.basic;


import com.alibaba.fastjson.JSON;
import com.kstarrain.pojo.Hotel;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.ListUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class ListTest {


    @Test
    public void test1() {

        List<Hotel> data = new ArrayList<>();
        data.add(new Hotel("汉庭酒店", false, 4));
        data.add(new Hotel("如家酒店", true, 3));
        data.add(new Hotel("锦江之星", true, 5));


        List<Hotel> hotels;
        hotels = data.stream()
                .filter(
                        new Predicate<Hotel>() {
                            @Override
                            public boolean test(Hotel hotel) {
                                return hotel.getEnable();
                            }
                        }
                )
                .sorted(
                        new Comparator<Hotel>() {
                            @Override
                            public int compare(Hotel o1, Hotel o2) {
                                return o2.getStar() - o1.getStar();
                            }
                        }
//                        (o1,o2) -> o2.getStar() - o1.getStar()
                )
                .collect(Collectors.toList());


        hotels.forEach(System.out :: println);

//                .sorted(Comparator.comparing(SubstituteLabel::getPriority)).collect(Collectors.toList());
    }


    @Test
    public void test2() {

        List<String> totalList = new ArrayList<>();
        totalList.addAll(null);
        System.out.println();
    }


    @Test
    public void test3() {

        List<String> childrens = new ArrayList<>();
        childrens.add("00002");
        childrens.add("00004");
        childrens.add("00001");
        childrens.add("00010");
        childrens.add("00003");


        //排序
        SortedSet<String> sortedSet = new TreeSet<>();
        childrens.forEach(child->{
            sortedSet.add(child);
        });

        System.out.println(sortedSet);
        System.out.println(sortedSet.first());

        String parentPath  = "/distributed_locks";
        String currentPath = "/distributed_locks/00003";
        //获取当前节点中所有比自己更小的节点
        SortedSet<String> lessThenMe = sortedSet.headSet(currentPath.substring(parentPath.length() + 1));
        //如果当前所有节点中有比自己更小的节点
        if (CollectionUtils.isNotEmpty(lessThenMe)){
            //获取比自己小的节点中的最后一个节点，设置为等待锁
            System.out.println(parentPath + "/" +lessThenMe.last());
        }
    }


    @Test
    public void test4() {

        List<String> childrens = new ArrayList<>();
        childrens.add("00002");
        childrens.add("00004");
        childrens.add("00001");
        childrens.add("00010");
        childrens.add("00003");
        childrens = null;

        //排序
        Collections.sort(childrens);
        String parentPath = "/distributed_locks";
        String currentPath = "/distributed_locks/00003";
        if (!currentPath.equals(childrens.get(0))) {
            int pathLength = parentPath.length();
            int wz = Collections.binarySearch(childrens, currentPath.substring(pathLength + 1));
            System.out.println(parentPath + "/" + childrens.get(wz - 1));

        }

    }


    @Test
    public void test5() {

        /** 正常 */
        List<Student> normal =  new ArrayList<>();

        /** 逾期 */
        List<Student> overdue = null;

        /** 处理中(还款已提交) */
        List<Student> inProcess = null;

        List<Student> result = new ArrayList<>();
        result.addAll(normal);

        System.out.println(result);


    }


    @Test
    public void test6(){

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
    public void test7(){

        int[] x = new int[10];
        System.out.println(x[0]);

    }

    @Test
    public void test8(){

        List<String> result = new ArrayList<>();
        result.addAll(new ArrayList<>());

        List<String> result2 = new ArrayList<>();
        result2.add("1234");
        result.addAll(result2);

        System.out.println(JSON.toJSONString(result));

    }

    @Test
    public void test9(){

        Long[] applicationIds = new Long[1];
        System.out.println(applicationIds[0]);

        Student student1 = new Student();
        student1.setName("上海");
        student1.setId("1");

        Student student2 = new Student();
        student2.setName("上海");
        student2.setId("2");

        ArrayList<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);

        Map<String, Map<String, List<Student>>> collect = list.stream().
                collect(Collectors.groupingBy(Student::getName, Collectors.groupingBy(Student::getId)));


        System.out.println(collect);

    }




}
