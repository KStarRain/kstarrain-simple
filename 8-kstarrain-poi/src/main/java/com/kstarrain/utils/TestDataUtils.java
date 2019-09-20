package com.kstarrain.utils;

import com.kstarrain.pojo.Student;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class TestDataUtils {

    public static Student createStudent1(){
        Student student1 = new Student();
        student1.setId(UUID.randomUUID().toString().replace("-", ""));
        student1.setName("貂蝉Mm");
        student1.setMoblie("15800704814");
        try {
            student1.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-09-07 23:24:51"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        student1.setMoney(new BigDecimal("1314.98"));
        student1.setCreateDate(new Date());
        student1.setUpdateDate(new Date());
        student1.setAliveFlag(1);
        student1.setIsAlive(true);
        return student1;
    }

    public static Student createStudent2(){
        Student student2 = new Student();
        student2.setId(UUID.randomUUID().toString().replace("-", ""));
        student2.setName("吕布Gg");
        student2.setMoblie("13836843570");
        try {
            student2.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1990-09-07 23:24:51"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        student2.setMoney(new BigDecimal("521.98"));
        student2.setCreateDate(new Date());
        student2.setUpdateDate(new Date());
        student2.setAliveFlag(1);
        student2.setIsAlive(false);
        return student2;
    }


    public static List<Student> getStudentList(){

        List<Student> data = new ArrayList<>();

        data.add(createStudent1());
        data.add(createStudent2());

        return data;

    }


}
