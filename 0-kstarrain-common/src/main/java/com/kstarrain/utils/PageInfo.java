package com.kstarrain.utils;

/**
 * @author: DongYu
 * @create: 2019-02-20 17:09
 * @description:
 */
public class PageInfo<T> {

    //当前页
    private int pageNum;
    //每页显示条数
    private int pageSize;
    //总页数
    private int pages = 0;
    //总条数
    private long total = 0;
    //分页后数据
    private T data;

    public PageInfo(int pageNum, int pageSize, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.data = data;
    }

    public PageInfo(int pageNum, int pageSize, int pages, long total, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
        this.data = data;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPages() {
        return pages;
    }

    public long getTotal() {
        return total;
    }

    public T getData() {
        return data;
    }
}
