package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.service.WorkbookService;
import com.kstarrain.utils.Excel;
import com.kstarrain.utils.ExcelUtils;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
public class ExcelTest {

    String path = "E:" + File.separator + "test" + File.separator + "excel"  + File.separator + "学生表.xlsx";

    @Test
    public void writeExcel(){

        File file = new File(path);
        if(!file.getParentFile().exists()){ //如果文件的目录不存在
            file.getParentFile().mkdirs(); //创建目录
        }


        try (OutputStream outputStream = new FileOutputStream(file)){

            List<Student> data = TestDataUtils.getStudentList();

            //生成Excel表格
//            Workbook workbook = WorkbookService.createHSSFWorkbook(data);
//            Workbook workbook = WorkbookService.createXSSFWorkbook(data);
            Workbook workbook = WorkbookService.createSXSSFWorkbook(data);

            //输出到文件上
            workbook.write(outputStream);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    @Test
    public void readExcel() {

        List<Student> students = new ArrayList();


        try (InputStream inputStream = new FileInputStream(path)){
            //读取excel
//            Workbook workbook = new HSSFWorkbook(input);
            Workbook workbook = new XSSFWorkbook(inputStream);

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
        }
    }


    @Test
    public void writeExcelByUtils() throws IOException, ReflectiveOperationException {

        List<Student> data = TestDataUtils.getStudentList();

        long start = System.currentTimeMillis();

        Map<String, String> titlePropertyMap = new LinkedHashMap<>();
        titlePropertyMap.put("主键","id");
        titlePropertyMap.put("姓名","name");
        titlePropertyMap.put("手机","moblie");
        titlePropertyMap.put("生日","birthday");
        titlePropertyMap.put("余额","money");
        titlePropertyMap.put("创建时间","createDate");
        titlePropertyMap.put("更新时间","updateDate");
        titlePropertyMap.put("删除标记","aliveFlag");
        titlePropertyMap.put("删除标记2","isAlive");


        Map<String, DateFormat> propertyDateFormatMap = new LinkedHashMap<>();
        propertyDateFormatMap.put("birthday",new SimpleDateFormat("yyyy-MM-dd"));
        propertyDateFormatMap.put("createDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        propertyDateFormatMap.put("updateDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


//        ExcelUtils.createByBeans(Excel.Type.XLS, data , titlePropertyMap, propertyDateFormatMap).write(path);
//        ExcelUtils.createByBeans(Excel.Type.XLSX, data , titlePropertyMap, propertyDateFormatMap).write(path);
        ExcelUtils.createByBeans(Excel.Type.LARGE_XLSX, data , titlePropertyMap, propertyDateFormatMap).write(path);

        long end = System.currentTimeMillis();
        System.out.println("导出耗时 " + (end - start)/1000 + "秒" );
    }



    @Test
    public void readExcelByUtils() {

        try (InputStream inputStream = new FileInputStream(path)){

            long start = System.currentTimeMillis();

            Map<String, String> titlePropertyMap = new LinkedHashMap<>();
            titlePropertyMap.put("主键","id");
            titlePropertyMap.put("姓名","name");
            titlePropertyMap.put("手机","moblie");
            titlePropertyMap.put("生日","birthday");
            titlePropertyMap.put("余额","money");
            titlePropertyMap.put("创建时间","createDate");
            titlePropertyMap.put("更新时间","updateDate");
            titlePropertyMap.put("删除标记","aliveFlag");
            titlePropertyMap.put("删除标记2","isAlive");

            Map<String, DateFormat> propertyDateFormatMap = new LinkedHashMap<>();
            propertyDateFormatMap.put("birthday",new SimpleDateFormat("yyyy-MM-dd"));
            propertyDateFormatMap.put("createDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            propertyDateFormatMap.put("updateDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


            Excel excel = ExcelUtils.create(path, inputStream).selectSheet(0);
            List<Student> students = ExcelUtils.readToBeans(excel, titlePropertyMap, propertyDateFormatMap, Student.class);

            long end = System.currentTimeMillis();

            System.out.println("导入耗时 " + (end - start)/1000 + "秒" );

            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
