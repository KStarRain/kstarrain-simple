package com.kstarrain.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-07-20 15:49
 * @description: 内存分页工具类
 */
public class PageUtils {

    /**
     * 内存分页
     *
     * @param pageNum
     * @param pageSize
     * @param resList
     * @param <T>
     * @return
     */
    //对结果集进行内存分页
    public static <T> PageInfo<List<T>> pagingList(int pageNum, int pageSize, List<T> resList) {

        // 总条数
        int total = 0;
        if (CollectionUtils.isNotEmpty(resList)) {
            total = resList.size();
        }

        // 总页数
        int pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        // 上一页
        int prePage = 1;
        if (pageNum > 1) {
            prePage = pageNum - 1;
        }

        // 下一页
        int nextPage = pageNum;
        if (pageNum < pages) {
            nextPage = pageNum + 1;
        }

        PageInfo<List<T>> pageInfo = new PageInfo<>();
        pageInfo.setData(new ArrayList<>());
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPages(pages);
        pageInfo.setPrePage(prePage);
        pageInfo.setNextPage(nextPage);
        pageInfo.setTotal(total);
        pageInfo.setIsFirstPage(pageNum == 1);
        pageInfo.setIsLastPage(pageNum == pages);
        pageInfo.setHasPreviousPage(pageNum > 1);
        pageInfo.setHasNextPage(pageNum < pages);

        if (CollectionUtils.isEmpty(resList)) {
            return pageInfo;
        }

        //起始索引
        int startIndex = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        //结束索引
        int endIndex = Math.min(startIndex + pageSize, total);

        if (startIndex < endIndex) {
            pageInfo.setData(resList.subList(startIndex, endIndex));
        }
        return pageInfo;
    }


    public static class PageInfo<T> {

        //当前页
        private int pageNum;
        //每页显示条数
        private int pageSize;
        //总页数
        private int pages = 0;
        //上一页
        private int prePage;
        //下一页
        private int nextPage;
        //总条数
        private long total = 0;

        // 是否为首页
        private boolean isFirstPage;
        // 是否为末页
        private boolean isLastPage;
        // 是否有上一页
        private boolean hasPreviousPage;
        // 是否有下一页
        private boolean hasNextPage;

        //分页后数据
        private T data;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public boolean getIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean getIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }


        public boolean getHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean getHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }




    }
}