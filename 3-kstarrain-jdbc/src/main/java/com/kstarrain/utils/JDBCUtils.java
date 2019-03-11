package com.kstarrain.utils;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author: DongYu
 * @create: 2019-01-14 17:40
 * @description:
 */
public class JDBCUtils {

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static{


        ResourceBundle bundle = ResourceBundle.getBundle("properties/jdbc");

        DRIVER = bundle.getString("mysql_driver");
        URL = bundle.getString("mysql_url");
        USERNAME = bundle.getString("mysql_username");
        PASSWORD = bundle.getString("mysql_password");

//        DRIVER = bundle.getString("oracle_driver");
//        URL = bundle.getString("oracle_url");
//        USERNAME = bundle.getString("oracle_username");
//        PASSWORD = bundle.getString("oracle_password");


        /** 注册驱动
         *  JDBC是1997年2月19日，在JDK1.1的版本中发布的，从版本就看得出，JDBC属于Java技术的一些最基础的功能点。
         *  但在JDK1.5之后，其实已经不需要去显式调用Class.forName("com.mysql.jdbc.Driver")了
         *  DriverManager会自动去加载合适的驱动，但是前提是classpath下必须有驱动jar包
         *  (驱动jar依赖必须引入，不引入驱动，会报找不到匹配的驱动异常 No suitable driver found )*/
        try{
            Class.forName(DRIVER);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取 Connetion
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    /**
     * 释放资源
     * @param conn
     * @param st
     */
    public static void closeResource(Connection conn, Statement st) {
        closeResource(conn,st,null);
    }


    /**
     * 释放资源
     * @param conn
     * @param st
     * @param rs
     */
    public static void closeResource(Connection conn, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(conn);
    }

    /**
     * 释放连接 Connection
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放语句执行者 Statement
     * @param st
     */
    public static void closeStatement(Statement st) {
        if(st !=null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放结果集 ResultSet
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if(rs !=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 释放连接 Connection
     * @param conn
     */
    public static void rollback(Connection conn) {
        if(conn !=null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
