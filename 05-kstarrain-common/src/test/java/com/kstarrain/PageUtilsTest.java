package com.kstarrain;

import com.kstarrain.utils.PageUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-02-20 16:05
 * @description:
 */
public class PageUtilsTest {

    @Test
    public void packet(){

        List<String> data = new ArrayList<>();
        data.add("貂蝉1");
        data.add("貂蝉2");
        data.add("貂蝉3");
        data.add("貂蝉4");
        data.add("貂蝉5");


        PageUtils.PageInfo<List<String>> result = PageUtils.pagingList(2, 4, data);
        System.out.println("每页显示行数：" + result.getPageSize());
        System.out.println("上一页     ：" + result.getPrePage());
        System.out.println("当前页     ：" + result.getPageNum());
        System.out.println("下一页     ：" + result.getNextPage());
        System.out.println("总页数     ：" + result.getPages());
        System.out.println("data      ：" + result.getData());
        System.out.println("总计数     ：" + result.getTotal());

    }


}
