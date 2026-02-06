package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * FAQ分类实体类
 *
 * @author refund
 */
public class FaqCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String categoryDescription;

    /**
     * 父分类ID
     */
    private Integer parentId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 无参构造函数
     */
    public FaqCategory() {
    }

    /**
     * 全参数构造函数
     */
    public FaqCategory(Integer id, String categoryName, String categoryDescription, Integer parentId,
                       Integer status, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parentId = parentId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("categoryName", categoryName)
                .append("categoryDescription", categoryDescription)
                .append("parentId", parentId)
                .append("status", status)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FaqCategory faqCategory = (FaqCategory) o;

        return id != null ? id.equals(faqCategory.id) : faqCategory.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
