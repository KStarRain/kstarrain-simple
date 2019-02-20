package com.kstarrain.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-02-20 16:59
 * @description: 内存分页工具类
 */
public class PageUtils {

    /**
     * 内存分页
     * @param pageSize
     * @param pageNum
     * @param resList
     * @param <T>
     * @return
     */
    //对结果集进行内存分页
    public static  <T> PageInfo<List<T>> pagingList(Integer pageNum, Integer pageSize, List<T> resList) {

        if (CollectionUtils.isEmpty(resList)){
            return new PageInfo(pageNum, pageSize, new ArrayList<T>());
        }


        //总条数
        int total = resList.size();
        //总页数
        int pages = total % pageSize == 0 ? total/pageSize : total/pageSize + 1;

        //起始索引
        int startIndex = pageNum > 0 ? (pageNum - 1) * pageSize : 0 ;
        //结束索引
        int endIndex = Math.min(startIndex + pageSize, total);


        if (startIndex < endIndex) {
            return new PageInfo(pageNum, pageSize, pages, total, resList.subList(startIndex, endIndex));
        }else {
            return new PageInfo(pageNum, pageSize, new ArrayList<T>());
        }

    }


}
