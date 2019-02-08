package com.kstarrain.mapper;

import com.kstarrain.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    Student findStudentById(@Param("id") String id);
    List<Student> findAllStudent();
    int insertStudent(Student student);
    int deleteStudentById(@Param("id") String id);

}
