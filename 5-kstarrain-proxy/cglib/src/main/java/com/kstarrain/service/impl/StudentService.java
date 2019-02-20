package com.kstarrain.service.impl;


import com.kstarrain.annotation.Transactional;

public class StudentService{

    public void insertStudent01(){
        System.out.println("新增学生数据");
    }

    @Transactional
    public void insertStudent02() {
        System.out.println("新增学生数据");
    }

}
