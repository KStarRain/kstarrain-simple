package com.kstarrain;


import com.kstarrain.dao.IStudentDao;
import com.kstarrain.dao.impl.StudentDaoImpl;
import com.kstarrain.pojo.Student;
import com.kstarrain.utils.JDBCUtils;
import com.kstarrain.utils.TestDataUtils;
import org.junit.Test;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class JdbcTest {

    private static String url = "jdbc:mysql://localhost:3306/db_kstarrain?useSSL=false";
    private static String userName = "root";
    private static String password = "1234qwer";

    private static String nameParam = "貂蝉' or 1 ='1";


    /**sql 注入*/
    @Test
    public void findStudentsByNameSimple() {

        List<Student> students = new ArrayList<>();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;


        try {
            /**1 注册驱动
             *  JDBC是1997年2月19日，在JDK1.1的版本中发布的，从版本就看得出，JDBC属于Java技术的一些最基础的功能点。
             *  但在JDK1.5之后，其实已经不需要去显式调用Class.forName("com.mysql.jdbc.Driver")了
             *  DriverManager会自动去加载合适的驱动，但是前提是classpath下必须有驱动jar包
             *  (驱动jar依赖必须引入，不引入驱动，会报找不到匹配的驱动异常 No suitable driver found )*/
            Class.forName("com.mysql.jdbc.Driver");

            //手动注册mysql驱动（这么写会注册两次，不支持）
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            /** 2 获得连接 */
            conn = DriverManager.getConnection(url, userName,password);

            String sql = "select * from t_student where ALIVE_FLAG = '1' and NAME = '" + nameParam + "'";

            /** 3 获得语句执行者 */
            st = conn.createStatement();

            /**4 执行SQL语句 */
            rs = st.executeQuery(sql);

            /** 5 处理结果集 */
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if(rs!=null){
                try{
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(st!=null){
                try{
                    st.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        System.out.println(students.size());

    }

    /**
     * 解释说明:
     * 		 获得驱动
     * 		  1. JDBC规范规定，如果需要连接数据库，必须提供驱动接口的实现类
     * 		  		驱动接口：java.sql.Driver
     * 		  		每一个数据库提供驱动jar 都实现该接口
     * 		  2. MySQL 提供实现类：com.mysql.jdbc.Driver
     * 		  		源码：public class com.mysql.jdbc.Driver implements java.sql.Driver {
     * 		  3. JDBC规范提供了，注册实现方式
     * 		  		DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
     * 		  		但，如果遵循上面语句，Java代码与 mysql实现类耦合(直接关系)，之后切换数据库将不能进行。
     * 		  		希望提供方案时，只要切换数据驱动，就可以切换使用数据库
     * 		  4. 通常注册驱动使用标准写法
     * 		  		Class.forName("com.mysql.jdbc.Driver")
     * 		  		1) 使用反射的方式加载指定的类
     * 		  		2) 具体加载的类以字符串(类的全限定名称)体现，内容就可以存放到配置文件中，通过修改配置文件方便切换数据库
     * 		  		3) 一个类被加载到内存，静态代码块将执行，static{ ... }
     * 		  		4) com.mysql.jdbc.Driver 源码分析
     * 		  			static{
     * 		  				java.sql.DriverManager.registerDriver(new Driver());
     *                        }
     *         注册驱动注意事项
     * 		  	手动注册驱动，驱动注册了几次？
     * 		  		DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
     * 		   注册了2次
     * 		   	第一次：new Driver() 时，Driver类加载，静态代码块执行，注册一次
     * 		   	第二次：手动注册
     *
     * 		//结论：注册驱动
     * 		Class.forName("com.mysql.jdbc.Driver");
     *
     */


    /** 防止sql注入 */
    @Test
    public void findStudentsByNameSimple2() {

        List<Student> students = new ArrayList<>();

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            /** 1 注册驱动 */
            Class.forName("com.mysql.jdbc.Driver");

            /** 2 获得连接 */
            conn = DriverManager.getConnection(url, userName,password);

            String sql = "select * from t_student where ALIVE_FLAG = '1' and NAME = ? " ;

            /** 3 预加载 */
            st = conn.prepareStatement(sql);
            st.setString(1,nameParam);

            /** 4 执行SQL语句 */
            rs = st.executeQuery();

            /** 5 处理结果集 */
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if(rs!=null){
                try{
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(st!=null){
                try{
                    st.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }


        System.out.println(students.size());
    }

    @Test
    public void findStudentsByName() {

        try {
            IStudentDao studentDao = new StudentDaoImpl();

            List<Student> students = studentDao.findStudentsByName(nameParam);

            System.out.println(students.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Test
    public void insertStudent() {

        try {
            IStudentDao studentDao = new StudentDaoImpl();

            int num = studentDao.insertStudent(TestDataUtils.createStudent1());

            System.out.println(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Test
    public void deleteStudent() {
        try {
            IStudentDao studentDao = new StudentDaoImpl();

            int num = studentDao.deleteStudentById("73665fd3cd4a415692aeb9387db42260");

            System.out.println(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** 编程式事务 */
    @Test
    public void programmingTransaction(){

        Connection conn = null;
        //保存点
        Savepoint savepoint = null;

        try {
            conn = JDBCUtils.getConnection();
            boolean autoCommit = conn.getAutoCommit();
            //设置禁止自动提交(开启事务)
            conn.setAutoCommit(false);

            IStudentDao studentDao = new StudentDaoImpl();

            int num1 = studentDao.insertStudent(conn,TestDataUtils.createStudent1());

            //设置保存点
//            savepoint = conn.setSavepoint();

//            int a = 1/0;
            int num2 = studentDao.insertStudent(conn,TestDataUtils.createStudent2());

            //事务提交
            conn.commit();

        } catch (Exception e) {
            try {

                if (savepoint != null){

                    //如果事务提交失败 ，回滚到保存点位置
                    conn.rollback(savepoint);

                    //提交保存点之前的执行结果
                    conn.commit();

                }else {
                    conn.rollback();
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            JDBCUtils.closeConnection(conn);
        }
    }


}
