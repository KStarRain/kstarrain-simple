package com.kstarrain.utils.model;

import lombok.Data;

import java.util.List;

/**
 * @author: DongYu
 * @create: 2020-02-18 09:44
 * @description:
 */
@Data
public class ChinaCity {


    /** 父编码 */
    private String pCode;

    /** 编码 */
    private String code;

    /** 名字 */
    private String name;

    /** 下级地区 */
    private List<ChinaCity> sonChinaCities;

    public ChinaCity(String pCode, String code, String name) {
        this.pCode = pCode;
        this.code = code;
        this.name = name;
    }


}
