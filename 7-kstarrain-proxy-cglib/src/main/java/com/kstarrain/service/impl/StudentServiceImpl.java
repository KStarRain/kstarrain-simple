package com.kstarrain.service.impl;


import com.kstarrain.annotation.Transactional;
import com.kstarrain.service.IStudentService;


public class StudentServiceImpl implements IStudentService {


    @Override
    public void insertStudent01(){
        System.out.println("新增学生数据");
    }

    @Override
    @Transactional
    public void insertStudent02() {
        System.out.println("新增学生数据");
    }

}
