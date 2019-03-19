package com.kstarrain;

import com.alibaba.fastjson.JSON;
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

        Student student1 = TestDataUtils.createStudent1();
        String s = JSON.toJSONString(student1);
        System.out.println(s);

        Student student = JSON.parseObject(s, Student.class);
        System.out.println(student);
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
}
