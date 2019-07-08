package com.kstarrain.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2019-03-07 19:27
 * @description: Excel 工具类  请了解清除大致用法之后再用，不然有坑
 *               HSSFWorkbook 操作 .xls 结尾的文件(Excel 2003之前的版本) 导出条数上限是65535行、256列
 *               XSSFWorkbook 操作 .xlsx结尾的文件(Excel 2007之后的版本) 导出条数上限是1048576行,16384列
 *               SXSSFWorkbook 如果数据量超过了此上限,那么可以使用SXSSFWorkbook来导出。实际上上万条数据，甚至上千条数据就可以考虑使用SXSSFWorkbook了
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
            default: throw new RuntimeException("Excel type is error. ");
        }
    }

    public static Excel create(InputStream in) throws IOException {
        return create(DEFAULT_EXCEL_TYPE, in);
    }

    public static Excel create(String fileName, InputStream in) throws IOException {
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        for (Excel.Type type : Excel.Type.values()) {
            if (type.getSuffix().equals(suffix)) {
                return create(type, in);
            }
        }
        return create(DEFAULT_EXCEL_TYPE, in);
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
        return createByBeans(DEFAULT_EXCEL_TYPE, beans, titlePropertyMap);
    }


    /**
     * @param excelType
     * @param beans
     * @param titlePropertyMap 数据标题与bean中属性的映射Map（例如： key：姓名;  value:name）
     * @param <T>
     * @return
     * @throws ReflectiveOperationException
     */
    public static <T> Excel createByBeans(Excel.Type excelType, List<T> beans, Map<String, String> titlePropertyMap) throws ReflectiveOperationException {

        if (CollectionUtils.isEmpty(beans)) {
            throw new IllegalArgumentException("Beans is empty. ");
        }

        if (MapUtils.isEmpty(titlePropertyMap)) {
            throw new IllegalArgumentException("TitlePropertyMap is empty. ");
        }



        Map<String, Method> clazzMethodMap = new HashMap<>();
        Class<?> clazz = beans.get(0).getClass();
        for (Method method : clazz.getMethods()) {
            clazzMethodMap.put(method.getName(),method);
        }


        List<Method> usefulGetMethods = new ArrayList<>();
        List<Object> rowContent = new ArrayList<>();

        for (Map.Entry<String, String> titlePropertyEntry : titlePropertyMap.entrySet()) {
            String mehtodName = "get" + StringUtils.capitalize(titlePropertyEntry.getValue());
            Method method = clazzMethodMap.get(mehtodName);
            if (method != null){
                usefulGetMethods.add(clazzMethodMap.get(mehtodName));
                rowContent.add(titlePropertyEntry.getKey());
            }else {
                throw new NoSuchMethodException("Method [" + mehtodName + "] not found in class [" + clazz.getName() + "].");
            }
        }

        Excel result = create(excelType).createSheet();
        result.writeRow(0, rowContent);

        for (int i = 0, len = beans.size(); i < len; i++) {
            rowContent.clear();
            for (Method method : usefulGetMethods) {
                Object invoke = method.invoke(beans.get(i));
                rowContent.add(invoke);
            }
            result.writeRow(i + 1, rowContent);
        }

        return result;
    }

    /*public static <T> List<T> readToBeans(Excel excel, Class<T> clazz) throws ReflectiveOperationException {
        return readToBeans(excel, clazz, null);
    }*/

    /**
     * @param excel
     * @param clazz
     * @param titlePropertyMap  读取excel
     * @param <T>
     * @return
     * @throws ReflectiveOperationException
     */
    public static <T> List<T> readToBeans(Excel excel, Class<T> clazz, Map<String, String> titlePropertyMap, Map<String, DateFormat> dateStrPropertyFormat) throws ReflectiveOperationException, ParseException {

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
        List<?> rowContent = excel.readRow(0);

        for (Object cellValue : rowContent) {
            if (StringUtils.isNotBlank(titlePropertyMap.get(cellValue))){
                String mehtodName = "set" + StringUtils.capitalize(titlePropertyMap.get(cellValue));
                Method method = clazzMethodMap.get(mehtodName);
                if (method != null){
                    usefulSetMethods.add(clazzMethodMap.get(mehtodName));
                }else {
                    throw new NoSuchMethodException("Method [" + mehtodName + "] not found in class [" + clazz.getName() + "].");
                }
            }
        }

        List<T> result = new ArrayList<>();
        for (int i = 1; i <= lastRowNum; i++) {
            rowContent = excel.readRow(i);
            if (CollectionUtils.isEmpty(rowContent) || StringUtils.isBlank(rowContent.get(0) + "")) {
                continue;
            }
            T bean = clazz.newInstance();
            for (int j = 0, size = rowContent.size(); j < size; j++) {
                Object cellValue = rowContent.get(j);
                Method usefulSetMethod = usefulSetMethods.get(j);
                Class<?> parameterType = usefulSetMethod.getParameterTypes()[0];
                if (!cellValue.getClass().equals(parameterType)) {

                    if (MapUtils.isNotEmpty(dateStrPropertyFormat)){
                        if (cellValue instanceof String){

                            DateFormat dateFormat = dateStrPropertyFormat.get(StringUtils.uncapitalize(usefulSetMethod.getName().substring(3)));
                            if (dateFormat == null){
                                cellValue = ConvertUtils.convert(cellValue, parameterType);
                            }else {
                                cellValue = dateFormat.parse((String)cellValue);
                            }
                        }else {
                            cellValue = ConvertUtils.convert(cellValue, parameterType);
                        }

                    }else {
                        cellValue = ConvertUtils.convert(cellValue, parameterType);
                    }
                }
                usefulSetMethods.get(j).invoke(bean, cellValue);
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
