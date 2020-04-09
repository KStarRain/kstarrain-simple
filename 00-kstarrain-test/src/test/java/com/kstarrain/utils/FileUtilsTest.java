package com.kstarrain.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author: Dong Yu
 * @create: 2019-08-21 16:15
 * @description:
 */
@Slf4j
public class FileUtilsTest {

    String fileDirectoryPath = "E:" + File.separator + "test" + File.separator + "file";

    String writeFilePath = fileDirectoryPath + File.separator + "outputFile.txt";

    String readFilePath = fileDirectoryPath + File.separator + "cat.jpg";

    String copyFilePath = fileDirectoryPath + File.separator + "cat_copy.jpg";

    @Test
    public void mkdir(){

        //创建文件夹
        try {
            FileUtils.forceMkdir(new File(fileDirectoryPath));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

    }



    @Test
    public void writeFile() throws IOException {

        File file = new File(writeFilePath);

        String data1 = JSON.toJSONString(TestDataUtils.createStudent1()) + System.getProperty("line.separator");
        String data2 = JSON.toJSONString(TestDataUtils.createStudent2()) + System.getProperty("line.separator");

       /* FileUtils.writeStringToFile(file, data1, Charset.forName("UTF-8"), true);
        FileUtils.writeStringToFile(file, data2, Charset.forName("UTF-8"), true);*/

        FileUtils.writeByteArrayToFile(file,data1.getBytes());
    }


    @Test
    public void readFile() throws IOException {

        File file = new File(writeFilePath);

        String text = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
        System.out.println(text);
    }



    @Test
    public void copyFile() throws IOException {

        File readFile = new File(readFilePath);

        File copyFile = new File(copyFilePath);

        FileUtils.copyFile(readFile, copyFile);
    }

}
