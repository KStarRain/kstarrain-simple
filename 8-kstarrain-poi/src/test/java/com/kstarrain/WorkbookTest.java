package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.WorkbookService;
import com.kstarrain.utils.Excel;
import com.kstarrain.utils.ExcelUtils;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2019-03-07 19:27
 * @description: 操作 excel
 */
@Slf4j
public class WorkbookTest {

//    String path = "E:" + File.separator + "test" + File.separator + "poi"  + File.separator + "学生表.xls";

    String path = "E:" + File.separator + "test" + File.separator + "poi"  + File.separator + "学生表.xlsx";

    @Test
    public void outputExcel_xls(){

        File file = new File(path);
        if(!file.getParentFile().exists()){ //如果文件的目录不存在
            file.getParentFile().mkdirs(); //创建目录
        }

        OutputStream output = null;

        try {
            output = new FileOutputStream(file);

            List<Student> data = TestDataUtils.getStudentList();

            //生成Excel表格
//            Workbook workbook = WorkbookService.createHSSFWorkbook(data);
//            Workbook workbook = WorkbookService.createXSSFWorkbook(data);
            Workbook workbook = WorkbookService.createSXSSFWorkbook(data);

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

        InputStream input = null;
        try {
            input = new FileInputStream(path);

            //读取excel
//            Workbook workbook = new HSSFWorkbook(input);
            Workbook workbook = new XSSFWorkbook(input);

            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {

                Row row = sheet.getRow(rowIndex + 1);

                Student student = new Student();
                student.setId(row.getCell(0).getStringCellValue());
                student.setName(row.getCell(1).getStringCellValue());
                student.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(row.getCell(2).getStringCellValue()));
                student.setMoney(new BigDecimal(row.getCell(3).getNumericCellValue()));
                student.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(row.getCell(4).getStringCellValue()));
                student.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(row.getCell(5).getStringCellValue()));
                student.setAliveFlag((int)row.getCell(6).getNumericCellValue());

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


    @Test
    public void writeExcelByBeans() {

        OutputStream output = null;

        try {
            output = new FileOutputStream(path);

            List<Student> data = TestDataUtils.getStudentList();

            Map<String, String> titlePropertyMap = new LinkedHashMap<>();
            titlePropertyMap.put("主键","id");
            titlePropertyMap.put("姓名","name");
            titlePropertyMap.put("生日","birthday");
            titlePropertyMap.put("余额","money");
            titlePropertyMap.put("创建时间","createDate");
            titlePropertyMap.put("更新时间","updateDate");
            titlePropertyMap.put("删除标记","aliveFlag");

            Map<String, DateFormat> propertyDateFormatMap = new LinkedHashMap<>();
            propertyDateFormatMap.put("birthday",new SimpleDateFormat("yyyy-MM-dd"));
            propertyDateFormatMap.put("createDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            propertyDateFormatMap.put("updateDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

//            ExcelUtils.createByBeans(Excel.Type.XLS, data , titlePropertyMap, propertyDateFormatMap).write(output);
//            ExcelUtils.createByBeans(Excel.Type.XLSX, data , titlePropertyMap, propertyDateFormatMap).write(output);
            ExcelUtils.createByBeans(Excel.Type.LARGE_XLSX, data , titlePropertyMap, propertyDateFormatMap).write(output);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }



    @Test
    public void readExcelToBean() {

        InputStream input = null;
        try {
            input = new FileInputStream(path);

            Map<String, String> titlePropertyMap = new LinkedHashMap<>();
            titlePropertyMap.put("主键","id");
            titlePropertyMap.put("姓名","name");
            titlePropertyMap.put("生日","birthday");
            titlePropertyMap.put("余额","money");
            titlePropertyMap.put("创建时间","createDate");
            titlePropertyMap.put("更新时间","updateDate");
            titlePropertyMap.put("删除标记","aliveFlag");

            Map<String, DateFormat> propertyDateFormatMap = new LinkedHashMap<>();
            propertyDateFormatMap.put("birthday",new SimpleDateFormat("yyyy-MM-dd"));
            propertyDateFormatMap.put("createDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            propertyDateFormatMap.put("updateDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


            Excel excel = ExcelUtils.create(path, input).selectSheet(0);
            List<Student> students = ExcelUtils.readToBeans(excel, Student.class, titlePropertyMap, propertyDateFormatMap);

            System.out.println(students);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }




}
