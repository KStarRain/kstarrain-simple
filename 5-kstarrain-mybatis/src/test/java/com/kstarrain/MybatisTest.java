package com.kstarrain;

import com.kstarrain.mapper.StudentMapper;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.TestDataUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class MybatisTest {


    @Test
    public void findStudentById() {
        try {

            InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = factory.openSession();

            StudentMapper userMapper = session.getMapper(StudentMapper.class);
            Student student = userMapper.findStudentById("董宇2\" or 1=\"1");

            if (student != null){
                System.out.println(student.toString());
            }else {
                System.out.println("null");
            }

            session.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void findAllStudent() {
        try {

            InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = factory.openSession();

            StudentMapper userMapper = session.getMapper(StudentMapper.class);
            List<Student> allStudent = userMapper.findAllStudent();

            Student student = allStudent.get(0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            System.out.println(sdf.format(student.getBirthday()));
            System.out.println(sdf.format(student.getCreateDate()));

            session.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertStudent() {
        try {

            InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = factory.openSession();
            //---------------
            StudentMapper userMapper = session.getMapper(StudentMapper.class);

            int count = userMapper.insertStudent(TestDataUtils.createStudent1());
            session.commit();   //增删改，一定一定要加上commit操作
            System.out.println(count);
            //--------------
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteStudentById() {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = factory.openSession();
            //---------------
            StudentMapper userMapper = session.getMapper(StudentMapper.class);


            int count = userMapper.deleteStudentById("6df339b6e6c6484b9f530ca1d18da8a7");
            session.commit();   //增删改，一定一定要加上commit操作
            System.out.println(count);
            //--------------
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /** 编程式事务 */
    @Test
    public void programmingTransaction() {

        try {

            InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");


            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

            /** factory.openSession()默认为false 不开启自动提交(开启事务)*/
            SqlSession session = factory.openSession(false);

            StudentMapper userMapper = session.getMapper(StudentMapper.class);


            userMapper.insertStudent(TestDataUtils.createStudent1());


            int a = 5/0;

            userMapper.insertStudent(TestDataUtils.createStudent2());


            session.commit();   //事务提交

            session.close();

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }


}
