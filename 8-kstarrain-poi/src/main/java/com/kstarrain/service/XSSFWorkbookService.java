package com.kstarrain.service;

import com.kstarrain.pojo.Student;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-03-10 12:09
 * @description:
 */
public class XSSFWorkbookService {

    public static XSSFWorkbook createStudentXSSFWorkbook(List<Student> data) {

        /** 创建一个workbook，对应一个Excel文件 */
        XSSFWorkbook workbook = new XSSFWorkbook();

        /** 在workbook中添加一个sheet,对应Excel文件中的sheet */
        Sheet sheet = workbook.createSheet("学生表一");

        /** 设置列宽 */
        sheet.setColumnWidth(0,33*256+184);
        sheet.setColumnWidth(1,8*256+184);
        sheet.setColumnWidth(2,10*256+184);
        sheet.setColumnWidth(3,8*256+184);
        sheet.setColumnWidth(4,18*256+184);
        sheet.setColumnWidth(5,18*256+184);
        sheet.setColumnWidth(6,10*256+184);


        /** 创建单元格样式 */
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);              // 设置单元格水平方向对齐方式：居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);   // 设置单元格垂直方向对齐方式：居中

        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);      // 设置单元格填充背景颜色：天蓝色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);        // 设置单元格填充样式：

        style.setBorderTop(HSSFCellStyle.BORDER_THIN);               // 设置单元格顶部边框线条：细线
        style.setTopBorderColor(IndexedColors.RED.getIndex());       // 设置单元格顶部边框颜色：红

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);            // 设置单元格底部边框线条：细线
        style.setBottomBorderColor(IndexedColors.RED.getIndex());    // 设置单元格底部边框颜色：红

        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);              // 设置单元格左边边框线条：细线
        style.setLeftBorderColor(IndexedColors.RED.getIndex());      // 设置单元格左边边框颜色：红

        style.setBorderRight(HSSFCellStyle.BORDER_THIN);             // 设置单元格右边边框线条：细线
        style.setRightBorderColor(IndexedColors.RED.getIndex());     // 设置单元格右边边框颜色：红


        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);                       // 设置字体颜色
        font.setFontHeightInPoints((short) 12);                      // 设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);                // 设置字体粗细
        // 把字体应用到当前的样式
        style.setFont(font);


        //设置标题文字
        List<String> head = new ArrayList<>();
        head.add("主键");
        head.add("姓名");
        head.add("生日");
        head.add("余额");
        head.add("创建时间");
        head.add("更新时间");
        head.add("删除标记");


        /** 在sheet中生成标题行 */
        Row headRow = sheet.createRow(0);
        for (int column = 0; column < head.size(); column++) {
            Cell cell = headRow.createCell(column);
            cell.setCellStyle(style);
            cell.setCellValue(head.get(column));
        }



        /** 在sheet中生成数据行 */
        for (int i = 0; i < data.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);

            Student student = data.get(i);
            dataRow.createCell(0).setCellValue(student.getId());
            dataRow.createCell(1).setCellValue(student.getName());
            dataRow.createCell(2).setCellValue(new SimpleDateFormat("yyyy/MM/dd").format(student.getBirthday()));
            dataRow.createCell(3).setCellValue(student.getMoney().doubleValue());
            dataRow.createCell(4).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(student.getCreateDate()));
            dataRow.createCell(5).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(student.getUpdateDate()));
            dataRow.createCell(6).setCellValue(Integer.valueOf(student.getAliveFlag()));
        }

        return workbook;
    }
}
