package com.kstarrain.dao;

import com.kstarrain.pojo.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:35
 * @description:
 */
public interface IStudentDao {


    List<Student> findStudentsByName(String name);

    int insertStudent(Student student);

    int deleteStudentById(String id);

    /** 通过connection 控制事务 */
    int insertStudent(Connection conn, Student student) throws SQLException;

    Student findStudentById(Connection conn, String id) throws SQLException;

    int updateMoney(Connection conn, String id) throws SQLException;

    int deleteStudentById(Connection conn, String id) throws SQLException;

    List<Student> findStudentsByName(Connection conn, String name) throws SQLException;

    int updateMoneyByName(Connection conn, String name) throws SQLException;





}
