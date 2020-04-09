package com.kstarrain.utils;


import com.kstarrain.utils.model.AddressParseInfo;
import com.kstarrain.utils.model.ChinaCity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: DongYu
 * @create: 2020-02-17 09:36
 * @description:
 */
@Slf4j
public class AddressUtils {

    private static final Map<String, String> provinceShortMap = new HashMap<>();

    static {
        provinceShortMap.put("内蒙古", "内蒙古自治区");
        provinceShortMap.put("广西",  "广西壮族自治区");
        provinceShortMap.put("西藏",  "西藏自治区");
        provinceShortMap.put("宁夏",  "宁夏回族自治区");
        provinceShortMap.put("新疆",  "新疆维吾尔自治区");
    }


    /**
     * 解析地址
     * @author lin
     * @param address
     * @return
     */
    public static AddressParseInfo parsAddressAndCheck(String address){

        AddressParseInfo addressParseInfo = parsAddress(address);
        if (addressParseInfo == null){return null;}

        // 省
        String provinceName = addressParseInfo.getProvince();
        if (StringUtils.isBlank(provinceName)){
            log.error("地址字符串【{}】解析结果校验失败，未解析出省级单位", address);
            return null;
        }

        if (!ChinaCityUtils.checkProvince(provinceName)){
            log.error("地址字符串【{}】解析结果校验失败，解析出的省【{}】不存在", address, provinceName);
            return null;
        }

        // 市
        String cityName = addressParseInfo.getCity();
        if (StringUtils.isBlank(cityName)){
            log.error("地址字符串【{}】解析结果校验失败，未解析出市级单位", address);
            return new AddressParseInfo(provinceName, null, null, null,false);
        }
        String cityCode = ChinaCityUtils.getCityCodeByProvinceNameAndCityName(provinceName, cityName);
        if (StringUtils.isBlank(cityCode)){
            log.error("地址字符串【{}】解析结果校验失败，解析出的市【{}】不为【{}】的下辖机构", address, cityName, provinceName);
            return new AddressParseInfo(provinceName, null, null, null,false);
        }

        // 区
        String districtName = addressParseInfo.getDistrict();
        if (StringUtils.isBlank(districtName)){
            if (cityName.matches("东莞市|中山市|三沙市|儋州市|嘉峪关市")) {
                return new AddressParseInfo(provinceName, cityName, null, addressParseInfo.getDetailAddress(),true);
            } else {
                log.error("地址字符串【{}】解析结果校验失败，未解析出区/县级单位", address);
                return new AddressParseInfo(provinceName, cityName, null, null,false);
            }
        }

        String districtCode = ChinaCityUtils.getDistrictCodeByCityCodeAndDistrictName(cityCode, districtName);
        if (StringUtils.isBlank(districtCode)){
            log.error("地址字符串【{}】解析结果校验失败，解析出的区/县【{}】不为【{}】的下辖机构", address, districtName, provinceName+cityName);
            return new AddressParseInfo(provinceName, cityName, null, null,false);
        }
        return new AddressParseInfo(provinceName, cityName, districtName, addressParseInfo.getDetailAddress(),true);
    }


    /**
     * 解析地址
     * @author lin
     * @param address
     * @return
     */
    private static AddressParseInfo parsAddress(String address){

        if (StringUtils.isBlank(address)){return null;}

        /**
         * 港澳台暂不支持
         */
        if (address.startsWith("香港") || address.startsWith("澳門") || address.startsWith("澳门") || address.startsWith("台湾")){
            log.error("地址字符串【{}】解析规则暂不支持港澳台地区",address);
            return null;
        }

        // 纠正地址
        String newAddress = correctAddress(address);


        /**
         * 标准规格地址（全地址）
         *  例如：黑龙江省大庆市龙凤区101街道12-1栋
         *       黑龙江省大兴安岭地区加格达奇区101街道12-1栋
         *       内蒙古自治区兴安盟乌兰浩特市101街道12-1栋
         *       内蒙古自治区呼和浩特市土默特左旗101街道12-1栋
         *       内蒙古自治区呼和浩特市新城区101街道12-1栋
         *       内蒙古自治区呼和浩特市清水河县101街道12-1栋
         *       湖北省恩施土家族苗族自治州恩施市101街道12-1栋
         *       湖北省省直辖行政单位仙桃市101街道12-1栋
         *       西藏自治区阿里地区普兰县101街道12-1栋
         */
        String regex1 = "(?<province>[^省].*?自治区|.*?省|.*?行政区)(?<city>[^市].*?市|.*?自治州|.*?地区|.*?行政单位|.?盟)(?<district>[^县].*?县|.*?区|.*?市|.*?旗)(?<detailAddress>.*)";
        Matcher m1 = Pattern.compile(regex1).matcher(newAddress);
        if(m1.find()){
            return new AddressParseInfo(m1.group("province"),m1.group("city"),m1.group("district"),m1.group("detailAddress"), true);
        }

        /**
         * 省+市
         */
        String regex2 = "((?<provinceAndCity>[^市]+市|.*?自治州|.*?地区|.*?行政单位|.*?盟)(?<district>[^县].*?县|.*?区|.*?市|.*?旗)(?<detailAddress>.*))";
        Matcher m2 = Pattern.compile(regex2).matcher(newAddress);
        if(m2.find()){
            String provinceAndCity = m2.group("provinceAndCity");

            /**
             * 直辖市
             *  例如：上海市浦东新区鹤洁路xxx弄x号xxx室
             *       上海上海市浦东新区鹤洁路xxx弄x号xxx室
             */
            String regex2_1 = "((?<province>[^省]+自治区|.*?省|.*?行政区|北京市|北京|上海市|上海|天津市|天津|重庆市|重庆)(?<city>.*))";
            Matcher m2_1 = Pattern.compile(regex2_1).matcher(provinceAndCity);

            if(m2_1.find()){
                String province = m2_1.group("province");
                String city = m2_1.group("city");
                String district = m2.group("district");
                if (province.matches("北京|上海|天津|重庆")){
                    province = province + "市";
                }
                if (province.matches("北京市|上海市|天津市|重庆市")){
                    city = province;
                    if (district.startsWith(city)){
                        district = district.substring(city.length());
                    }
                }
                return new AddressParseInfo(province, city, district, m2.group("detailAddress"), true);
            }


            /**
             * 例如：广州市海珠区南坑街30号303房
             */
            ChinaCity province = ChinaCityUtils.getProvinceByCityName(provinceAndCity);
            if (province != null) {
                return new AddressParseInfo(province.getName(), provinceAndCity, m2.group("district"),m2.group("detailAddress"), true);
            } else {
                return new AddressParseInfo(null, provinceAndCity, m2.group("district"),m2.group("detailAddress"), false);
            }
        }

        /**
         * 东莞市/中山市/三沙市/儋州市/嘉峪关市 为 不设市辖区的地级市
         */
        String regex3 = "(?<province>广东省|海南省|甘肃省)(?<city>东莞市|中山市|三沙市|儋州市|嘉峪关市)(?<detailAddress>.*)";
        Matcher m3 = Pattern.compile(regex3).matcher(newAddress);
        if(m3.find()){
            return new AddressParseInfo(m3.group("province"),m3.group("city"), null, m3.group("detailAddress"), true);
        }


        AddressParseInfo addressParseInfo = null;
        /**
         * 省+区/县
         *  针对一些身份证上没有市级单位的地址
         *  例如：安徽省临泉县xxx弄x号xxx室
         */
        String regex4 = "(?<province>[^省]+自治区|.*?省|.*?行政区)(?<district>[^县].*?县|.*?区|.*?市|.*?旗)(?<detailAddress>.*)";
        Matcher m4 = Pattern.compile(regex4).matcher(newAddress);
        if(m4.find()){
            String provinceName = m4.group("province");
            String district = m4.group("district");

            ChinaCity city = ChinaCityUtils.getCityByProvinceNameAndDistrictName(provinceName, district);
            if (city != null) {
                return new AddressParseInfo(provinceName, city.getName(), district, m4.group("detailAddress"), true);
            } else {
                addressParseInfo = new AddressParseInfo(provinceName,null, district, m4.group("detailAddress"), false);
            }
        }


        /**
         * 省+市/自治州/盟/地区
         *  例如：贵州省毕节市双树路33410号
         */
        String regex5 = "(?<province>[^省]+自治区|.*?省|.*?行政区)(?<city>[^市].*?市|.*?自治州|.*?地区|.*?行政单位|.?盟)(?<detailAddress>.*)";
        Matcher m5 = Pattern.compile(regex5).matcher(newAddress);
        if(m5.find()){
            String provinceName = m5.group("province");
            String cityName = m5.group("city");

            ChinaCity province = ChinaCityUtils.getProvinceByCityName(cityName);
            if (province != null && provinceName.equals(province.getName())) {
                log.error("地址字符串【{}】中不包含区/县名", address);
                return new AddressParseInfo(provinceName, cityName, null, m5.group("detailAddress"), false);
            } else {
                addressParseInfo = new AddressParseInfo(provinceName, null, null, m5.group("detailAddress"), false);
            }
        }


        if (addressParseInfo != null){
            return addressParseInfo;
        } else {
            log.error("地址字符串【{}】未能解析", address);
            return null;
        }

    }

    private static String correctAddress(String address) {

        /**
         * 完善缩写的自治区  内蒙古/广西/西藏/宁夏/新疆
         *  例如：新疆乌鲁木齐市水磨沟区南湖东路南一巷26号
         *       广西田阳县坡洪镇古美村古美街37号
         */
        for (Map.Entry<String, String> entry : provinceShortMap.entrySet()) {
            if (address.startsWith(entry.getKey()) && !address.startsWith(entry.getValue())){
                return entry.getValue() + address.substring(entry.getKey().length());
            }
        }

        if (address.startsWith("褔建省")){
            return "福建省" + address.substring(3);
        }
        return address;
    }

}
