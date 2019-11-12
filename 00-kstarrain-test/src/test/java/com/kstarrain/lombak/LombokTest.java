package com.kstarrain.lombak;

import com.kstarrain.pojo.Student;
import org.junit.Test;

/**
 * @author: Dong Yu
 * @create: 2019-04-28 09:24
 * @description:
 */
public class LombokTest {

    @Test
    public void test1() {

        Student student = new Student();
        System.out.println(student.getName());

    }
}
