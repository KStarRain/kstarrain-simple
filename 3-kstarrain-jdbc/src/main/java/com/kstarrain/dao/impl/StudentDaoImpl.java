package com.kstarrain.dao.impl;

import com.kstarrain.dao.IStudentDao;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:36
 * @description:
 */
public class StudentDaoImpl implements IStudentDao {


    @Override
    public List<Student> findStudentsByName(String name) {

        List<Student> students = new ArrayList<>();

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;


        try {
            //获取连接
            conn = JDBCUtils.getConnection();

            String sql = "select * from t_student where ALIVE_FLAG = '1' and NAME = ? " ;

            //预加载
            st = conn.prepareStatement(sql);
            st.setString(1,name);

            //执行SQL语句
            rs = st.executeQuery();

            //处理结果集
            while (rs.next()){
                Student student = new Student();
                student.setId(rs.getString("ID"));
                student.setName(rs.getString("NAME"));
                student.setMoney(rs.getBigDecimal("MONEY"));
                student.setBirthday(rs.getDate("BIRTHDAY"));
                student.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                student.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
                student.setAliveFlag(rs.getString("ALIVE_FLAG"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            JDBCUtils.closeResource(conn,st,rs);
        }

        return students;

    }


    @Override
    public int insertStudent(Student student) {

        int num = 0;

        Connection conn = null;
        PreparedStatement st = null;


        try {
            //获取连接
            conn = JDBCUtils.getConnection();

            String sql = "insert into t_student(ID, NAME, BIRTHDAY, MONEY, CREATE_DATE, UPDATE_DATE, ALIVE_FLAG)values(?,?,?,?,?,?,?)";


            st = conn.prepareStatement(sql);

            st.setString(1, student.getId());
            st.setString(2, student.getName());
            st.setDate(3, new Date(student.getBirthday().getTime()));
            st.setBigDecimal(4,student.getMoney());
            st.setTimestamp(5, new Timestamp(student.getCreateDate().getTime()));
            st.setTimestamp(6, new Timestamp(student.getUpdateDate().getTime()));
            st.setString(7, student.getAliveFlag());

            num = st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,st);
        }
        return num;
    }



    @Override
    public int deleteStudentById(String id) {

        int num = 0;

        Connection conn = null;
        PreparedStatement st = null;

        try {
            //获取连接
            conn = JDBCUtils.getConnection();

            String sql = "update t_student set ALIVE_FLAG = '0',UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and Id = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, id);

            num = st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,st);
        }
        return num;
    }


    @Override
    public int insertStudent(Connection conn, Student student) throws SQLException {

        int num = 0;

        PreparedStatement st = null;

        try {
            String sql = "insert into t_student(ID, NAME, BIRTHDAY, MONEY, CREATE_DATE, UPDATE_DATE, ALIVE_FLAG)values(?,?,?,?,?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, student.getId());
            st.setString(2, student.getName());
            st.setDate(3, new Date(student.getBirthday().getTime()));
            st.setBigDecimal(4,student.getMoney());
            st.setTimestamp(5, new Timestamp(student.getCreateDate().getTime()));
            st.setTimestamp(6, new Timestamp(student.getUpdateDate().getTime()));
            st.setString(7, student.getAliveFlag());

            num = st.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtils.closeStatement(st);
        }
        return num;
    }

    @Override
    public Student findStudentById(Connection conn, String id) throws SQLException {

        Student student = null;

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "select * from t_student where ALIVE_FLAG = '1' and ID = ? " ;

            //预加载
            st = conn.prepareStatement(sql);
            st.setString(1,id);

            //执行SQL语句
            rs = st.executeQuery();

            //处理结果集
            while (rs.next()){
                student = new Student();
                student.setId(rs.getString("ID"));
                student.setName(rs.getString("NAME"));
                student.setMoney(rs.getBigDecimal("MONEY"));
                student.setBirthday(rs.getDate("BIRTHDAY"));
                student.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                student.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
                student.setAliveFlag(rs.getString("ALIVE_FLAG"));
                break;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtils.closeResultSet(rs);
            JDBCUtils.closeStatement(st);
        }

        return student;
    }


    @Override
    public int updateMoney(Connection conn, String id) throws SQLException {

        int num = 0;

        PreparedStatement st = null;

        try {
            String sql = "update t_student set MONEY = MONEY + 1,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and ID = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, id);

            num = st.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            JDBCUtils.closeStatement(st);
        }
        return num;
    }



    @Override
    public int deleteStudentById(Connection conn, String id) throws SQLException {

        int num = 0;

        PreparedStatement st = null;

        try {
            String sql = "update t_student set ALIVE_FLAG = '0',UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and Id = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, id);

            num = st.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            JDBCUtils.closeStatement(st);
        }
        return num;
    }


    @Override
    public List<Student> findStudentsByName(Connection conn, String name) throws SQLException {

        List<Student> students = new ArrayList<>();

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "select * from t_student where ALIVE_FLAG = '1' and NAME = ?" ;

            //预加载
            st = conn.prepareStatement(sql);
            st.setString(1,name);

            //执行SQL语句
            rs = st.executeQuery();

            //处理结果集
            while (rs.next()){
                Student student = new Student();
                student.setId(rs.getString("ID"));
                student.setName(rs.getString("NAME"));
                student.setMoney(rs.getBigDecimal("MONEY"));
                student.setBirthday(rs.getDate("BIRTHDAY"));
                student.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                student.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
                student.setAliveFlag(rs.getString("ALIVE_FLAG"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtils.closeResultSet(rs);
            JDBCUtils.closeStatement(st);
        }

        return students;
    }

    @Override
    public int updateMoneyByName(Connection conn, String name) throws SQLException {

        int num = 0;

        PreparedStatement st = null;

        try {
            String sql = "update t_student set MONEY = MONEY + 1,UPDATE_DATE = SYSDATE() where ALIVE_FLAG = '1' and NAME = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, name);

            num = st.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            JDBCUtils.closeStatement(st);
        }
        return num;

    }


}
