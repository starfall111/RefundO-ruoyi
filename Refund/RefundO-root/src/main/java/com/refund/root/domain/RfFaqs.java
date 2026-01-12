package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.refund.common.annotation.Excel;
import com.refund.common.core.domain.BaseEntity;

/**
 * FAQ内容对象 rf_faqs
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
public class RfFaqs extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** FAQ ID */
    @Excel(name = "FAQ ID")
    private Long id;

    /** 所属分类ID */
    @Excel(name = "所属分类ID")
    private Long categoryId;

    /** 问题 */
    @Excel(name = "问题")
    private String question;

    /** 答案 */
    @Excel(name = "答案")
    private String answer;

    /** 浏览次数 */
    @Excel(name = "浏览次数")
    private Long viewCount;

    /** 是否置顶 */
    @Excel(name = "是否置顶")
    private Long isTop;

    /**  */
    private Long isFaq;

    /** 排序 */
    private Long sortOrder;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setQuestion(String question) 
    {
        this.question = question;
    }

    public String getQuestion() 
    {
        return question;
    }

    public void setAnswer(String answer) 
    {
        this.answer = answer;
    }

    public String getAnswer() 
    {
        return answer;
    }

    public void setViewCount(Long viewCount) 
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount() 
    {
        return viewCount;
    }

    public void setIsTop(Long isTop) 
    {
        this.isTop = isTop;
    }

    public Long getIsTop() 
    {
        return isTop;
    }

    public void setIsFaq(Long isFaq) 
    {
        this.isFaq = isFaq;
    }

    public Long getIsFaq() 
    {
        return isFaq;
    }

    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryId", getCategoryId())
            .append("question", getQuestion())
            .append("answer", getAnswer())
            .append("viewCount", getViewCount())
            .append("isTop", getIsTop())
            .append("isFaq", getIsFaq())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
