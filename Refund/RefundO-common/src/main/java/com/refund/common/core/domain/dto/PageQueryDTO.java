package com.refund.common.core.domain.dto;

import java.io.Serializable;

/**
 * APP端分页查询DTO
 */
public class PageQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    // 排序字段：create_time（创建时间）或 amount（金额）
    private String orderBy = "create_time";

    // 排序方向：desc（降序）或 asc（升序）
    private String orderDirection = "desc";

    public PageQueryDTO() {
        this.pageNum = 1;
        this.pageSize = 10;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
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
}
