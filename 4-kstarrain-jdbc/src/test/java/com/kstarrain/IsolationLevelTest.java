package com.kstarrain;


import com.kstarrain.dao.IStudentDao;
import com.kstarrain.dao.impl.StudentDaoImpl;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.JDBCUtils;
import com.kstarrain.utils.TestDataUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description: 数据库隔离级别测试
 */


public class IsolationLevelTest {


    private static String ID = "d7fbbfd87a08408ba994dea6be435ada";



    /** 隔离级别测试 新增数据(测试脏读)*/
    @Test
    public void isolationLevel_insert(){
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            studentDao.insertStudent(conn,TestDataUtils.createStudent1());

//            int a = 5/0;

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


    /** 隔离级别测试 修改数据(测试脏读) */
    @Test
    public void isolationLevel_update(){
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // update t_student set MONEY = MONEY + 1,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and Id = ?
            studentDao.updateMoney(conn,ID);

            int a = 5/0;

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


    /** 隔离级别测试 查询同一条数据 (测试不可重复读 针对update)*/
    @Test
    public void isolationLevel_repeatable_read() {
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Student student1 = studentDao.findStudentById(conn, ID);
            System.out.println(student1.getMoney());

            System.out.println("=============");

            //select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Student student2 = studentDao.findStudentById(conn, ID);
            System.out.println(student2.getMoney());

            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }



    /** 隔离级别测试 查询数据行数(测试幻读 针对insert/delete)*/
    @Test
    public void isolationLevel_phantom_read(){
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and NAME = ?
            List<Student> students1 = studentDao.findStudentsByName(conn,"貂蝉Mm");
            System.out.println(students1.size());

            System.out.println("=============");

            // select * from t_student where ALIVE_FLAG = '1' and NAME = ?
            List<Student> students2 = studentDao.findStudentsByName(conn,"貂蝉Mm");
            System.out.println(students2.size());

            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }



    /** 隔离级别测试 测试幻读 01 */
    @Test
    public void isolationLevel_phantom_read_01(){
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and NAME = ?
            List<Student> students1 = studentDao.findStudentsByName(conn,"貂蝉Mm");
            System.out.println(students1.size());

            System.out.println("=============");

            // select * from t_student where ALIVE_FLAG = '1' and NAME = ?
            List<Student> students2 = studentDao.findStudentsByName(conn,"貂蝉Mm");
            System.out.println(students2.size());


            Student student = new Student();
            student.setId(ID);
            student.setName("貂蝉Mm");
            student.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-09-07 23:24:51"));
            student.setMoney(new BigDecimal("0.00"));
            student.setCreateDate(new Date());
            student.setUpdateDate(new Date());
            student.setAliveFlag("1");

            int num = studentDao.insertStudent(conn, student);
            System.out.println("一共影响行数：" + num);

            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


    /** 隔离级别测试 测试幻读 02 */
    @Test
    public void isolationLevel_phantom_read_02(){
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and NAME = ?
            List<Student> students1 = studentDao.findStudentsByName(conn,"貂蝉Mm");
            System.out.println(students1.size());

            System.out.println("=============");

            // select * from t_student where ALIVE_FLAG = '1' and NAME = ?
            List<Student> students2 = studentDao.findStudentsByName(conn,"貂蝉Mm");
            System.out.println(students2.size());

            // update t_student set MONEY = MONEY + 1,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and NAME = ?
            int num = studentDao.updateMoneyByName(conn, "貂蝉Mm");
            System.out.println("一共影响行数：" + num);

            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }



    /** 隔离级别测试 一个事务内2次查询数据 一次insert */
    @Test
    public void isolationLevel_readInTransaction01() throws ParseException {
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
//            conn.setReadOnly(true);

            // select * from t_student where ALIVE_FLAG = '1' and NAME = ?
            List<Student> students1 = studentDao.findStudentsByName(conn,"貂蝉Mm");
            System.out.println(students1.size());

            // insert 貂蝉Mm
            studentDao.insertStudent(conn,TestDataUtils.createStudent1());

            // select * from t_student where ALIVE_FLAG = '1' and NAME = ?
            List<Student> students2 = studentDao.findStudentsByName(conn,"貂蝉Mm");
            System.out.println(students2.size());
            System.out.println("=============");
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }



    /** 隔离级别测试 一个事务内2次查询数据 一次update */
    @Test
    public void isolationLevel_readInTransaction02() throws ParseException {
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Student student1 = studentDao.findStudentById(conn, ID);
            System.out.println(student1.getMoney());

            // update t_student set MONEY = MONEY + 1,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and Id = ?
            studentDao.updateMoney(conn,ID);

            //select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Student student2 = studentDao.findStudentById(conn, ID);
            System.out.println(student2.getMoney());
            System.out.println("=============");
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }



    /** 隔离级别测试 一个事务内2次查询数据 一次insert */
    @Test
    public void isolationLevel_readInTransaction03() throws ParseException {
        IStudentDao studentDao = new StudentDaoImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Student student1 = studentDao.findStudentById(conn, ID);
            System.out.println(student1);

            // update t_student set ALIVE_FLAG = '0',UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and Id = ?
            studentDao.deleteStudentById(conn,ID);

            //select * from t_student where ALIVE_FLAG = '1' and ID = ?
            Student student2 = studentDao.findStudentById(conn, ID);
            System.out.println(student2);
            System.out.println("=============");
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }













}
