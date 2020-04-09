package com.kstarrain;

import com.alibaba.fastjson.JSON;
import com.kstarrain.utils.ChinaCityUtils;
import com.kstarrain.utils.model.ChinaCity;
import org.junit.Test;

import java.util.List;

/**
 * @author: DongYu
 * @description:
 */
public class CityUtilsTest {

    @Test
    public void test01(){
        List<ChinaCity> allCity = ChinaCityUtils.getProvinces();
        System.out.println(JSON.toJSONString(allCity));
    }

    @Test
    public void test02(){
        List<ChinaCity> allCity = ChinaCityUtils.getSubordinateByCode("310000");
        System.out.println(JSON.toJSONString(allCity));
    }

    @Test
    public void test03(){
        ChinaCity city = ChinaCityUtils.getCityByProvinceNameAndDistrictName("安徽省", "临泉县");
        if (city!=null){
            System.out.println(city.getName());
        }
    }


    @Test
    public void test04(){
        ChinaCity province = ChinaCityUtils.getProvinceByCityName("大庆市");
        if (province!=null){
            System.out.println(province.getName());
        }
    }
}
