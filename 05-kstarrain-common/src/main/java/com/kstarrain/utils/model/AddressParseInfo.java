package com.kstarrain.utils.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author: DongYu
 * @create: 2020-02-17 10:18
 * @description: 地址解析信息
 */
@Data
@AllArgsConstructor
@ToString
public class AddressParseInfo {

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String district;

    /** 详细住址 */
    private String detailAddress;

    /** 解析是否完整 */
    private Boolean parseComplete;

}
