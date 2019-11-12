package com.kstarrain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 置换标签
 */
@Data
@AllArgsConstructor
public class Hotel {

    private String name;    //旅店名字

    private Boolean enable;  //是否启用

    private int star ;      // 星级


}
