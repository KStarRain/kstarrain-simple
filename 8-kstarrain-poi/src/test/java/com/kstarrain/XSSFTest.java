package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.utils.TestDataUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-03-07 19:27
 * @description:
 */
public class XSSFTest {


    @Test
    public void createExcel_xlsx() throws IOException {

        List<Student> data = new ArrayList<>();
        data.add(TestDataUtils.createStudent1());
        data.add(TestDataUtils.createStudent2());

        String filePath = "E:\\test1\\01\\haha.txt";

        File file = new File(filePath);


        boolean mkdirs = file.mkdirs();

        System.out.println(mkdirs);


    }

    @Test
    public void readFrom() throws IOException {

        String filePath = "E:\\test\\测试数据.xlsx";
        File file = new File(filePath);

        FileInputStream fileInputStream = new FileInputStream(file);

        //包装一个Excel文件对象
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

        //读取文件中第一个Sheet标签页
        XSSFSheet hssfSheet = workbook.getSheetAt(0);

        //遍历标签页中所有的行
        for (Row row : hssfSheet){
            for (Cell cell : row){
                String value = cell.getStringCellValue();
                System.out.print(value  + " ");
            }
        }
    }
}
