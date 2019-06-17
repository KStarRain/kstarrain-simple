package com.kstarrain;

import com.alibaba.fastjson.JSON;
import com.kstarrain.utils.TestDataUtils;
import org.junit.Test;

import java.io.*;

/**
 * @author: DongYu
 * @create: 2019-03-07 19:27
 * @description:
 */
public class FileTest {


    /** OutputStream：它是抽象类，不能创建对象，这里我们需要使用OutputStream类的子类FileOutputStream的对象把数据写到文件中 */
    @Test
    public void outputStream(){

//        String filePath = "E:\\test\\file";
        String filePath = "E:" + File.separator + "test" + File.separator + "file"  + File.separator + "outputFile.txt";

        //1:使用File类创建一个要操作的文件路径
        File file = new File(filePath);

        System.out.println(file.length());

        if(!file.getParentFile().exists()){//如果文件的目录不存在
            file.getParentFile().mkdirs();//创建目录
        }

        OutputStream output = null;

        try {
            //2: 实例化OutputStream 对象
            //如果不想覆盖源文件，还想在后面追加的话，添加参数true
            output = new FileOutputStream(file);

            //获得系统中的行分隔符
            String separator = System.getProperty("line.separator");

            //3.输出到文件上
            output.write((JSON.toJSONString(TestDataUtils.createStudent1()) + separator).getBytes());

            output.write((JSON.toJSONString(TestDataUtils.createStudent2()) + separator).getBytes());
            System.out.println(file.length());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输出流
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /** InputStream：它是抽象类，不能创建对象，这里我们需要使用InputStream类的子类FileInputStream的对象把文件读到内存中 */
    @Test
    public void inputStream(){

//        String filePath = "E:\\test\\file";
        String filePath = "E:" + File.separator + "test" + File.separator + "file"  + File.separator + "outputFile.txt";

        InputStream input = null;
        try {
            //2: 实例化InputStream 对象
            input = new FileInputStream(filePath);

            //定义一个变量接收读取的数据
            int b = 0;
            //fis.read()表示使用输入流对象读取字节数据保存到变量b中，
            //如果b等于-1说明已经读取到文件末尾，否则文件中还有数据
            while((b = input.read()) != -1)
            {
                //输出字节数据
                System.out.print((char)b);
            }


//            int i = input.read();
//            System.out.println(i);
//            System.out.println((char)i);
//
//            int j = input.read();
//            System.out.println(j);
//            System.out.println((char)j);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输入流
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void copy(){

        String fromPath = "E:" + File.separator + "test" + File.separator + "file"  + File.separator + "outputFile.txt";
        String toPath = "E:" + File.separator + "test" + File.separator + "file"  + File.separator + "outputFile_copy.txt";

        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(fromPath);
            output = new FileOutputStream(toPath);

            byte[] bytes = new byte[1024];

            int len;
            while((len = input.read(bytes)) != -1){
                output.write(bytes,0,len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                //关闭输入流
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                //关闭输出流
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
