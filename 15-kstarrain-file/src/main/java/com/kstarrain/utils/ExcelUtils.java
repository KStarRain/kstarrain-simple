package com.kstarrain.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

/**
 * @author: DongYu
 * @create: 2019-09-06 14:27
 * @description: Excel 工具类  请了解清除大致用法之后再用，不然有坑
 *               HSSFWorkbook 操作 .xls 结尾的文件(Excel 2003之前的版本) 导出条数上限是65535行、256列
 *               XSSFWorkbook 操作 .xlsx结尾的文件(Excel 2007之后的版本) 导出条数上限是1048576行,16384列
 *               SXSSFWorkbook 只支持写，不支持读，写出.xlsx结尾的文件，如果数据量超过了此上限,那么可以使用SXSSFWorkbook来导出。实际上上万条数据，甚至上千条数据就可以考虑使用SXSSFWorkbook了
 */
@Slf4j
public class ExcelUtils {

    public static final Excel.Type DEFAULT_EXCEL_TYPE = Excel.Type.XLSX;

    public static Excel create() {
        return create(DEFAULT_EXCEL_TYPE);
    }

    public static Excel create(Excel.Type excelType) {
        switch (excelType) {
            case XLS: return new Excel(new HSSFWorkbook(), excelType);
            case XLSX: return new Excel(new XSSFWorkbook(), excelType);
            case LARGE_XLSX: return new Excel(new SXSSFWorkbook(new XSSFWorkbook(), 100), excelType);
            default: throw new RuntimeException("Excel type is error. ");
        }
    }

    public static Excel create(InputStream in) throws IOException {
        return create(DEFAULT_EXCEL_TYPE, in);
    }

    public static Excel create(String fileName, InputStream in) throws IOException {
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        if (Excel.Type.XLS.getSuffix().equals(suffix)){
            return create(Excel.Type.XLS, in);
        } else {
            return create(DEFAULT_EXCEL_TYPE, in);
        }
    }

    public static Excel create(Excel.Type excelType, InputStream in) throws IOException {
        switch (excelType) {
            case XLS: return new Excel(new HSSFWorkbook(in), excelType);
            case XLSX: return new Excel(new XSSFWorkbook(in), excelType);
            default: throw new RuntimeException("Excel type is error. ");
        }
    }

    public static Excel createByFile(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is null or file not exist. ");
        }
        String filePath = file.toString();
        String suffix = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
        InputStream in = null;
        Excel result = null;
        try {
            in = new FileInputStream(file);
            for (Excel.Type type : Excel.Type.values()) {
                if (type.getSuffix().equals(suffix)) {
                    result = create(type, in);
                }
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                }catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        if (result != null) return result;
        else throw new RuntimeException("Suffix in file is not excel suffix. ");
    }

    public static <T> Excel createByBeans(List<T> beans, Map<String, String> titlePropertyMap) throws ReflectiveOperationException {
        return createByBeans(DEFAULT_EXCEL_TYPE, beans, titlePropertyMap, null);
    }

    public static <T> Excel createByBeans(List<T> beans, Map<String, String> titlePropertyMap, Map<String, DateFormat> propertyDateFormatMap) throws ReflectiveOperationException {
        return createByBeans(DEFAULT_EXCEL_TYPE, beans, titlePropertyMap, propertyDateFormatMap);
    }

    public static <T> Excel createByBeans(Excel.Type excelType, List<T> beans, Map<String, String> titlePropertyMap) throws ReflectiveOperationException {
        return createByBeans(excelType, beans, titlePropertyMap, null);
    }

    /**
     * @param excelType
     * @param beans
     * @param titlePropertyMap excel中标题名字与bean中属性名字的映射Map [例如： key -- 姓名;  value -- name]
     * @param propertyDateFormatMap 对待bean中的Date转化为excel表格中的字符串时间特殊处理 [例如： key -- birthday; value -- new SimpleDateFormat("yyyy-MM-dd")]
     * @param <T>
     * @return
     * @throws ReflectiveOperationException
     */
    public static <T> Excel createByBeans(Excel.Type excelType, List<T> beans, Map<String, String> titlePropertyMap, Map<String, DateFormat> propertyDateFormatMap) throws ReflectiveOperationException {

        if (MapUtils.isEmpty(titlePropertyMap)) {
            throw new IllegalArgumentException("TitlePropertyMap is empty. ");
        }


        List<Object> rowContent = new ArrayList<>();
        List<Method> usefulGetMethods = new ArrayList<>();

        if (CollectionUtils.isEmpty(beans)) {
            rowContent = new ArrayList<>(titlePropertyMap.keySet());
        } else {
            Map<String, Method> clazzMethodMap = new HashMap<>();
            Class<?> clazz = beans.get(0).getClass();
            for (Method method : clazz.getMethods()) {
                clazzMethodMap.put(method.getName(),method);
            }

            for (Map.Entry<String, String> titlePropertyEntry : titlePropertyMap.entrySet()) {
                String mehtodName = "get" + StringUtils.capitalize(titlePropertyEntry.getValue());
                Method method = clazzMethodMap.get(mehtodName);
                if (method != null){
                    rowContent.add(titlePropertyEntry.getKey());
                    usefulGetMethods.add(method);
                }else {
                    throw new NoSuchMethodException("Method [" + mehtodName + "] not found in class [" + clazz.getName() + "].");
                }
            }
        }

        Excel result = create(excelType).createSheet();
        result.writeRow(0, rowContent, true);

        if (CollectionUtils.isEmpty(beans)){
            return result;
        }

        for (int i = 0, len = beans.size(); i < len; i++) {
            rowContent.clear();
            for (Method method : usefulGetMethods) {
                Object invoke = method.invoke(beans.get(i));

                if (MapUtils.isNotEmpty(propertyDateFormatMap) && Date.class.equals(method.getReturnType())){
                    String propertyName = StringUtils.uncapitalize(method.getName().substring(3));
                    DateFormat dateFormat = propertyDateFormatMap.get(propertyName);
                    if (dateFormat != null){
                        invoke = dateFormat.format((Date)invoke);
                    }
                }
                rowContent.add(invoke);
            }
            result.writeRow(i + 1, rowContent,false);
        }

        return result;
    }



    public static <T> List<T> readToBeans(Excel excel, Map<String, String> titlePropertyMap, Class<T> clazz) throws ReflectiveOperationException, ParseException {
        return readToBeans(excel, titlePropertyMap, null, clazz);
    }

    /**
     * 读取excel中数据转化为bean
     * @param excel
     * @param clazz                  需要转化成的bean.Class
     * @param titlePropertyMap       excel中标题名字与bean中属性名字的映射Map [例如： key -- 姓名;  value -- name]
     * @param propertyDateFormatMap  对待excel表格中的字符串时间转化为bean中的Date特殊处理 [例如： key -- birthday; value -- new SimpleDateFormat("yyyy-MM-dd")]
     * @param <T>
     * @return
     * @throws ReflectiveOperationException
     * @throws ParseException
     */
    public static <T> List<T> readToBeans(Excel excel, Map<String, String> titlePropertyMap, Map<String, DateFormat> propertyDateFormatMap, Class<T> clazz) throws ReflectiveOperationException, ParseException {

        Integer lastRowNum = excel.getLastRowNum();
        if (lastRowNum <= 0) {
            throw new IllegalArgumentException("Excel not have row or not have data. ");
        }

        if (MapUtils.isEmpty(titlePropertyMap)) {
            throw new IllegalArgumentException("TitlePropertyMap is empty. ");
        }

        Map<String, Method> clazzMethodMap = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            clazzMethodMap.put(method.getName(),method);
        }

        List<Method> usefulSetMethods = new ArrayList<>();
        HashMap<Method, Integer> usefulSetMethodsColumnNumber = new HashMap<>();
        List<?> rowContent = excel.readRow(0);

        for (int i = 0; i < rowContent.size(); i++) {
            Object cellValue = rowContent.get(i);
            if (StringUtils.isNotBlank(titlePropertyMap.get(cellValue))){
                String mehtodName = "set" + StringUtils.capitalize(titlePropertyMap.get(cellValue));
                Method method = clazzMethodMap.get(mehtodName);
                if (method != null){
                    usefulSetMethods.add(method);
                    usefulSetMethodsColumnNumber.put(method, i);
                }else {
                    throw new NoSuchMethodException("Method [" + mehtodName + "] not found in class [" + clazz.getName() + "].");
                }
            }
        }

        if (CollectionUtils.isEmpty(usefulSetMethods)){
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>();
        for (int i = 1; i <= lastRowNum; i++) {
            rowContent = excel.readRow(i);
            if (CollectionUtils.isEmpty(rowContent)) {continue;}

            T bean = clazz.newInstance();

            for (Method usefulSetMethod : usefulSetMethods) {
                Object cellValue = rowContent.get(usefulSetMethodsColumnNumber.get(usefulSetMethod));

                if (String.class.equals(cellValue.getClass()) && StringUtils.isBlank((String)cellValue)){continue;}

                Class<?> parameterType = usefulSetMethod.getParameterTypes()[0];
                if (!cellValue.getClass().equals(parameterType)) {
                    if (MapUtils.isNotEmpty(propertyDateFormatMap) && String.class.equals(cellValue.getClass()) && Date.class.equals(parameterType)){
                        String propertyName = StringUtils.uncapitalize(usefulSetMethod.getName().substring(3));
                        DateFormat dateFormat = propertyDateFormatMap.get(propertyName);
                        if (dateFormat != null){
                            cellValue = dateFormat.parse((String)cellValue);
                        }else {
                            throw new IllegalArgumentException("String convert Date failed because the dateFormat of property [" + propertyName + "] mapping could not be found in propertyDateFormatMap. ");
                        }
                    }else {
                        cellValue = ConvertUtils.convert(cellValue, parameterType);
                    }
                }
                usefulSetMethod.invoke(bean, cellValue);
            }

            result.add(bean);
        }
        return result;
    }

    public static byte[] saveToBytes(Excel excel) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excel.write(out);
        return out.toByteArray();
    }

    public static void saveToFile(Excel excel, File file) throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            excel.write(out);
        } finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {

                }
            }
        }
    }

}
