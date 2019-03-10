package com.kstarrain;

import com.kstarrain.service.HSSFWorkbookService;
import com.kstarrain.utils.TestDataUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

/**
 * @author: DongYu
 * @create: 2019-03-07 19:27
 * @description: 操作 .xlsx结尾的文件(Excel 2007之后版本)
 */
public class XSSFTest {


    @Test
    public void outputExcel_xlsx(){

        String path = "E:" + File.separator + "test" + File.separator + "poi"  + File.separator + "outputExcel.xls";


        File file = new File(path);
        if(!file.getParentFile().exists()){//如果文件的目录不存在
            file.getParentFile().mkdirs();//创建目录
        }

        OutputStream output = null;

        try {
            output = new FileOutputStream(file);

            //生成Excel表格
            HSSFWorkbook workbook = HSSFWorkbookService.createStudentHSSFWorkbook(TestDataUtils.getStudentList());

            workbook.write(output);
//            output.write(workbook.getBytes());
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
