package com.kstarrain.utils;

import com.alibaba.fastjson.JSON;
import com.kstarrain.utils.model.ChinaCity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: DongYu
 * @create: 2020-02-18 09:18
 * @description:
 */
@Slf4j
public class ChinaCityUtils {

    private static final String ROOT = "ROOT";

    private static final Map<String, List<ChinaCity>> pCode_cities_map = new LinkedHashMap<>();

    private static final Map<String, String> province_code_name_map = new LinkedHashMap<>();
    private static final Map<String, String> province_name_code_map = new LinkedHashMap<>();

    private static final Map<String, String> city_code_name_map = new LinkedHashMap<>();
    private static final Map<String, List<String>> city_name_codes_map = new LinkedHashMap<>();

    private static final Map<String, String> district_code_name_map = new LinkedHashMap<>();
    private static final Map<String, List<String>> district_name_codes_map = new LinkedHashMap<>();



    static {
        try (InputStream inputStream = ChinaCityUtils.class.getClassLoader().getResourceAsStream("ChinaCity.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8.name()))){
            initCityFromFile(bufferedReader);
        } catch (Exception e) {
            log.error("Failed to parse ChinaCity.txt", e);
        }
    }


    private static void initCityFromFile(BufferedReader bufferedReader) throws Exception {

        String line;
        ChinaCity province = null;
        ChinaCity city = null;
        while((line = bufferedReader.readLine()) != null) {
            Matcher codeMatcher = Pattern.compile("([0-9]+)").matcher(line);
            Matcher nameMatcher = Pattern.compile("([\u4e00-\u9fa5]+)").matcher(line);
            if (codeMatcher.find() && nameMatcher.find()) {
                String code = codeMatcher.group(1);
                String name = nameMatcher.group(1);

                if(line.indexOf(code) == 0) {
                    // 省
                    province = new ChinaCity("", code, name);
                    initPCodeCitiesMap(province);
                    initProvinceCodeNameMap(province);
                    initProvinceNameCodeMap(province);
                } else if(line.indexOf(code) == 1) {
                    // 市
                    city = new ChinaCity(province.getCode(),code, name);
                    initPCodeCitiesMap(city);
                    initCityCodeNameMap(city);
                    initCityNameCodesMap(city);
                } else if(line.indexOf(code) == 2) {
                    // 区
                    ChinaCity district = new ChinaCity(city.getCode(), code, name);
                    initPCodeCitiesMap(district);
                    initDistrictCodeNameMap(district);
                    initDistrictNameCodesMap(district);
                }
            }
        }
    }



    private static void initPCodeCitiesMap(ChinaCity chinaCity) {

        List<ChinaCity> sonChinaCities = pCode_cities_map.computeIfAbsent(chinaCity.getCode(), k -> new ArrayList<>());
        chinaCity.setSonChinaCities(sonChinaCities);

        if ("".equals(chinaCity.getPCode())){
            List<ChinaCity> chinaCities = pCode_cities_map.computeIfAbsent(ROOT, k -> new ArrayList<>());
            chinaCities.add(chinaCity);
        } else {
            sonChinaCities = pCode_cities_map.computeIfAbsent(chinaCity.getPCode(), k -> new ArrayList<>());
            sonChinaCities.add(chinaCity);
        }

    }

    private static void initProvinceCodeNameMap(ChinaCity province) {
        province_code_name_map.put(province.getCode(), province.getName());
    }

    private static void initProvinceNameCodeMap(ChinaCity province) {
        province_name_code_map.put(province.getName(), province.getCode());
    }


    private static void initCityCodeNameMap(ChinaCity city) {
        city_code_name_map.put(city.getCode(), city.getName());
    }


    private static void initCityNameCodesMap(ChinaCity city) {

        List<String> codes = city_name_codes_map.computeIfAbsent(city.getName(), k -> new ArrayList<>());
        if (!codes.contains(city.getCode())){
            codes.add(city.getCode());
        }
    }

    private static void initDistrictCodeNameMap(ChinaCity district) {
        district_code_name_map.put(district.getCode(),district.getName());
    }
    private static void initDistrictNameCodesMap(ChinaCity district) {

        List<String> codes = district_name_codes_map.computeIfAbsent(district.getName(), k -> new ArrayList<>());
        if (!codes.contains(district.getCode())){
            codes.add(district.getCode());
        }
    }


    public static List<ChinaCity> getProvinces() {
        return pCode_cities_map.get(ROOT);
    }

    public static List<ChinaCity> getSubordinateByCode(String code) {
        return pCode_cities_map.get(code);
    }


    public static ChinaCity getCityByProvinceNameAndDistrictName(String provinceName, String districtName) {

        String provinceCode = province_name_code_map.get(provinceName);
        if (StringUtils.isBlank(provinceCode)){
            log.error("根据省名【{}】未匹配到行政编码", provinceName);
            return null;
        }
        List<ChinaCity> cities = getSubordinateByCode(provinceCode);
        if (CollectionUtils.isEmpty(cities)){
            log.error("根据省级行政编码【{}】未匹配到下级市", provinceCode);
            return null;
        }

        List<String> districtCodes = district_name_codes_map.get(districtName);
        if (CollectionUtils.isEmpty(districtCodes)){
            log.error("根据区/县名【{}】未匹配到行政编码", districtName);
            return null;
        }

        List<ChinaCity> resultCities = new ArrayList<>();
        for (ChinaCity city : cities) {
            List<ChinaCity> districts = city.getSonChinaCities();
            if (CollectionUtils.isEmpty(districts)){continue;}

            List<String> tempDistrictCodes = districts.stream().map(ChinaCity::getCode).collect(Collectors.toList());

            List<String> intersection = ListUtils.intersection(tempDistrictCodes, districtCodes);
            if (CollectionUtils.isNotEmpty(intersection)){
                resultCities.add(city);
            }
        }

        if (CollectionUtils.isEmpty(resultCities)){
            log.error("根据省名【{}】，区/县名【{}】未匹配到市", provinceName, districtName);
            return null;
        }

        if (resultCities.size() > 1){
            log.error("根据省名【{}】，区/县名【{}】匹配到{}个市，分别为{}，无法确认。", provinceName, districtName, resultCities.size(), JSON.toJSONString(resultCities.stream().map(ChinaCity::getName).collect(Collectors.toList())));
            return null;
        }

        return resultCities.get(0);
    }



    public static ChinaCity getProvinceByCityName(String cityName) {

        List<ChinaCity> resultProvinces = getProvincesByCityName(cityName);

        if (CollectionUtils.isEmpty(resultProvinces)){
            log.error("根据市名【{}】未匹配到上级省", cityName);
            return null;
        }

        if (resultProvinces.size() > 1){
            log.error("根据市名【{}】匹配到{}个省，分别为{}，无法确认。", cityName, resultProvinces.size(), JSON.toJSONString(resultProvinces.stream().map(ChinaCity::getName).collect(Collectors.toList())));
            return null;
        }
        return resultProvinces.get(0);
    }

    private static List<ChinaCity> getProvincesByCityName(String cityName) {

        List<String> cityCodes = city_name_codes_map.get(cityName);
        if (CollectionUtils.isEmpty(cityCodes)){
            log.error("根据市名【{}】未匹配到行政编码", cityName);
            return null;
        }
        List<ChinaCity> provinces = getProvinces();
        if (CollectionUtils.isEmpty(provinces)){
            log.error("未获取到省级列表");
            return null;
        }

        List<ChinaCity> resultProvinces = new ArrayList<>();
        for (ChinaCity province : provinces) {
            List<ChinaCity> cities = province.getSonChinaCities();
            if (CollectionUtils.isEmpty(cities)){continue;}

            List<String> tempCityCodes = cities.stream().map(ChinaCity::getCode).collect(Collectors.toList());

            List<String> intersection = ListUtils.intersection(tempCityCodes, cityCodes);
            if (CollectionUtils.isNotEmpty(intersection)){
                resultProvinces.add(province);
            }
        }
        return resultProvinces;
    }


    public static boolean checkProvince(String provinceName) {
        if (StringUtils.isBlank(provinceName)){return false;}
        return StringUtils.isNotBlank(province_name_code_map.get(provinceName));
    }


    public static String getCityCodeByProvinceNameAndCityName(String provinceName, String cityName) {
        if (StringUtils.isBlank(provinceName) || StringUtils.isBlank(cityName)){return null;}

        String provinceCode = province_name_code_map.get(provinceName);
        if (StringUtils.isBlank(provinceCode)){return null;}

        List<ChinaCity> cities = pCode_cities_map.get(provinceCode);
        if (CollectionUtils.isEmpty(cities)){return null;}

        for (ChinaCity city : cities) {
            if (cityName.equals(city.getName())){return city.getCode();}
        }
        return null;
    }



    public static String getDistrictCodeByCityCodeAndDistrictName(String cityCode, String districtName) {
        if (StringUtils.isBlank(cityCode) || StringUtils.isBlank(districtName)){return null;}

        List<ChinaCity> districts = pCode_cities_map.get(cityCode);
        if (CollectionUtils.isEmpty(districts)){return null;}

        for (ChinaCity district : districts) {
            if (districtName.equals(district.getName())){return district.getCode();}
        }
        return null;

    }
}
