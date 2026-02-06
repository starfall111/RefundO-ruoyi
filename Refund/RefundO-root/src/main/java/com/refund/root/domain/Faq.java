package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * FAQ实体类
 *
 * @author refund
 */
public class Faq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * FAQ ID
     */
    private Integer id;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 问题
     */
    private String question;

    /**
     * 答案
     */
    private String answer;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 是否置顶
     */
    private Integer isTop;

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
    public Faq() {
    }

    /**
     * 全参数构造函数
     */
    public Faq(Integer id, Integer categoryId, String question, String answer,
               Integer viewCount, Integer isTop, Integer status,
               LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.question = question;
        this.answer = answer;
        this.viewCount = viewCount;
        this.isTop = isTop;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
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
                .append("categoryId", categoryId)
                .append("question", question)
                .append("answer", answer)
                .append("viewCount", viewCount)
                .append("isTop", isTop)
                .append("status", status)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faq faq = (Faq) o;

        return id != null ? id.equals(faq.id) : faq.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
