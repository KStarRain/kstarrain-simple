package com.kstarrain;

import com.kstarrain.pojo.Student;
import com.kstarrain.utils.CsvUtils;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.*;
import org.apache.commons.io.input.BOMInputStream;
import org.junit.Test;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2019-03-07 19:27
 * @description: 操作 csv
 */
@Slf4j
public class CsvTest {


    String path = "E:" + File.separator + "test" + File.separator + "csv"  + File.separator + "学生表.txt";


    @Test
    public void writeCsv1() {

        try (OutputStream out = new FileOutputStream(path);
             Writer writer = new OutputStreamWriter(out,"UTF-8"); //如果是UTF-8时，WPS打开是正常显示，而微软的excel打开是乱码
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)){

            List<Student> students = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                students.add(TestDataUtils.createStudent1());
            }



            for(Student student : students){
                List<String> record = new ArrayList<>();
                record.add(student.getId());
                record.add(student.getName());
                record.add(student.getMoblie());
                record.add(new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").format(student.getBirthday()));
//                record.add(student.getMoney().toString());
                record.add(student.getMoney().toString());
                record.add(new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").format(student.getCreateDate()));
                record.add(new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").format(student.getUpdateDate()));
                record.add(student.getAliveFlag().toString());
                record.add(student.getIsAlive().toString());
                csvPrinter.printRecord(record);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void writeCsv2() {

        String[] title ={"主键","姓名","手机","生日","余额","创建时间","更新时间","删除标记","删除标记2",};

        try (OutputStream out = new FileOutputStream(path);
             Writer writer = new OutputStreamWriter(out,"UTF-8"); //如果是UTF-8时，WPS打开是正常显示，而微软的excel打开是乱码
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL).withHeader(title))){

            List<Student> students = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                students.add(TestDataUtils.createStudent1());
            }
            Student student1 = TestDataUtils.createStudent1();
            student1.setId(null);
            students.add(student1);

            long start = System.currentTimeMillis();

            for(Student student : students){
                List<String> record = new ArrayList<>();
                record.add(student.getId());
                record.add(student.getName());
                record.add(student.getMoblie()+"说\"哈哈哈!\"");
                record.add(new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").format(student.getBirthday()));
//                record.add(student.getMoney().toString());
                record.add(new DecimalFormat("#,##0.00").format(student.getMoney()));
                record.add(new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").format(student.getCreateDate()));
                record.add(new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").format(student.getUpdateDate()));
                record.add(student.getAliveFlag().toString());
                record.add(student.getIsAlive().toString());
                csvPrinter.printRecord(record);
            }


            long end = System.currentTimeMillis();
            System.out.println("导出耗时 " + (end - start)/1000 + "秒" );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void readCsv(){

        try(InputStream in  = new FileInputStream(path);
            InputStream bomIn = new BOMInputStream(in);
            Reader reader = new InputStreamReader(bomIn)){

            long start = System.currentTimeMillis();

            CSVParser parse = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL).withHeader().parse(reader);

            Iterable<CSVRecord> records = parse.getRecords();

            for(CSVRecord record:records){

                System.out.print(record.get("主键") + "  ");
                System.out.print(record.get("姓名") + "  ");
                System.out.print(record.get("手机") + "  ");
                System.out.print(record.get("生日") + "  ");
                System.out.print(record.get("余额") + "  ");
                System.out.print(record.get("创建时间") + "  ");
                System.out.print(record.get("更新时间") + "  ");
                System.out.print(record.get("删除标记") + "  ");
                System.out.println(record.get("删除标记2") + "  ");
            }

            long end = System.currentTimeMillis();
            System.out.println("导入耗时 " + (end - start)/1000 + "秒" );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void readCsv2() {

        try(InputStream in  = new FileInputStream(path);
            InputStream bomIn = new BOMInputStream(in);
            Reader reader = new InputStreamReader(bomIn)){

            long start = System.currentTimeMillis();

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL).parse(reader).getRecords();
            for(CSVRecord record:records){

                System.out.print(record.get(0) + "  ");
                System.out.print(record.get(1) + "  ");
                System.out.print(record.get(2) + "  ");
                System.out.print(record.get(3) + "  ");
                System.out.print(record.get(4) + "  ");
                System.out.print(record.get(5) + "  ");
                System.out.print(record.get(6) + "  ");
                System.out.print(record.get(7) + "  ");
                System.out.println(record.get(8) + "  ");
            }

            long end = System.currentTimeMillis();
            System.out.println("导入耗时 " + (end - start)/1000 + "秒" );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void writeCsvByUtils() throws IOException, ReflectiveOperationException {

        List<Student> result = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            result.add(TestDataUtils.createStudent1());
        }


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

        long start = System.currentTimeMillis();

        CsvUtils.writeByBeans(result, titlePropertyMap, propertyDateFormatMap, path);

        long end = System.currentTimeMillis();
        System.out.println("导出耗时 " + (end - start)/1000 + "秒" );
    }


    @Test
    public void readCsvByUtils(){

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


            List<Student> students = CsvUtils.readToBeans(inputStream, titlePropertyMap, propertyDateFormatMap, Student.class);

            long end = System.currentTimeMillis();

            System.out.println("导入耗时 " + (end - start)/1000 + "秒" );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
