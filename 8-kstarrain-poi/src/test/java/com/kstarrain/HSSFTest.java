package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.HSSFWorkbookService;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-03-07 19:27
 * @description: 操作 .xls结尾的文件(Excel 2003之前的版本)
 */
@Slf4j
public class HSSFTest {


    @Test
    public void outputExcel_xls(){

        String path = "E:" + File.separator + "test" + File.separator + "poi"  + File.separator + "学生表.xls";


        File file = new File(path);
        if(!file.getParentFile().exists()){//如果文件的目录不存在
            file.getParentFile().mkdirs();//创建目录
        }

        OutputStream output = null;

        try {
            output = new FileOutputStream(file);

            //生成Excel表格
            HSSFWorkbook workbook = HSSFWorkbookService.createStudentHSSFWorkbook(TestDataUtils.getStudentList());

            //输出到文件上
            workbook.write(output);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            try {
                //关闭输出流
                output.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

    @Test
    public void inputExcel_xls() {


        List<Student> students = new ArrayList();

        String path = "E:" + File.separator + "test" + File.separator + "poi"  + File.separator + "学生表.xls";

        InputStream input = null;
        try {
            input = new FileInputStream(path);

            //读取excel
            HSSFWorkbook workbook = new HSSFWorkbook(input);

            //获取第一个sheet
            HSSFSheet sheet = workbook.getSheetAt(0);

            int lastRowNum = sheet.getLastRowNum();

            for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {

                HSSFRow row = sheet.getRow(rowIndex + 1);

                Student student = new Student();
                student.setId(row.getCell(0).getStringCellValue());
                student.setName(row.getCell(1).getStringCellValue());
                student.setBirthday(new SimpleDateFormat("yyyy/MM/dd").parse(row.getCell(2).getStringCellValue()));
                student.setMoney(new BigDecimal(row.getCell(3).getNumericCellValue()));
                student.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(row.getCell(4).getStringCellValue()));
                student.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(row.getCell(5).getStringCellValue()));
                student.setAliveFlag(String.valueOf((int)row.getCell(6).getNumericCellValue()));

                students.add(student);
            }


            System.out.println(students);
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
}
