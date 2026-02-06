package com.refund.common.core.page;

import java.util.List;

/**
 * APP端分页结果类
 */
public class PageResult<T> {
    /**
     * 当前页数据
     */
    private List<T> records;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    public PageResult() {
    }

    public PageResult(List<T> records, Long total, Integer pageNum, Integer pageSize, Integer pages) {
        this.records = records;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
    }

    /**
     * 创建分页结果
     *
     * @param records  当前页数据
     * @param total    总记录数
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    public static <T> PageResult<T> of(List<T> records, Long total, Integer pageNum, Integer pageSize) {
        int pages = (int) Math.ceil((double) total / pageSize);
        return new PageResult<>(records, total, pageNum, pageSize, pages);
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
