package com.kstarrain.service;

import com.kstarrain.annotation.Transactional;

public interface IStudentService {


    void insertStudent01();

    @Transactional
    void insertStudent02();


}
