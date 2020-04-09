package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.utils.TestDataUtils;
import org.junit.Test;

import java.io.*;

/**
 * @author: DongYu
 * @create: 2020-01-07 09:42
 * @description:
 */
public class SerializableTest{


    String filePath = "E:" + File.separator + "test" + File.separator + "student.txt";

    @Test
    public void test1() throws IOException {

        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filePath))){

            Student student1 = TestDataUtils.createStudent1();
            os.writeObject(student1);
        }


    }

    @Test
    public void test2() throws IOException, ClassNotFoundException {

        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(filePath));
        Student student = (Student) oi.readObject();
        System.out.println(student);

    }
}
