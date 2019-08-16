package com.kstarrain.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.TestDataUtils;
import org.junit.Test;

/**
 * @author: Dong Yu
 * @create: 2019-03-19 17:08
 * @description:
 */
public class JSONTest {

    @Test
    public void test1(){

        System.out.println(JSON.toJSONString("1234"));

        Student student = new Student();
        Object o = JSON.toJSON(student);

       /* Student student2 = JSON.parseObject(" ", Student.class);

        String s1 = JSON.toJSONString(null);


        Student student1 = TestDataUtils.createStudent1();
        String s = JSON.toJSONString(student1);
        System.out.println(s);

        Student student = JSON.parseObject(s, Student.class);
        System.out.println(student);*/
    }

    @Test
    public void test2(){

        Student student = TestDataUtils.createStudent1();

        AfterFilter afterFilter = new AfterFilter() {
            @Override
            public void writeAfter(Object paramObject) {
                if (paramObject instanceof Student) {
                    Student student = (Student) paramObject;
                    if (student.getId() != null) {
                        writeKeyValue("id", student.getId());
                    }
                }
            }
        };

        String s = JSON.toJSONString(student, afterFilter);
        System.out.println(s);
    }


    @Test
    public void test3(){

        String supplierInfo = "{\"supplier\": \"1345\"}";

        JSONObject jsonObject = JSON.parseObject(supplierInfo);
        System.out.println(jsonObject);

        Student student = JSON.parseObject(supplierInfo, Student.class);
        System.out.println(student);

    }
}
