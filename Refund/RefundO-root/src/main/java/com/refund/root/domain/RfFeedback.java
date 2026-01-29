package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.refund.common.annotation.Excel;
import com.refund.common.core.domain.BaseEntity;

/**
 * 用户反馈对象 rf_feedback
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
public class RfFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 反馈ID */
    private Long feedbackId;

    /** 用户ID */
    @Excel(name = "User ID")
    private Long userId;

    /** 类型 */
    @Excel(name = "Type")
    private Long feedbackType;

    /** 详细内容 */
    @Excel(name = "Content")
    private String content;

    /** 联系方式 */
    @Excel(name = "Contact Info")
    private String contactInfo;

    /** 反馈状态：0-未处理 1-处理中 2-已处理 3-已关闭 */
    private Long feedbackStatus;

    /** 处理人ID（关联管理员表，预留字段） */
    private Long adminId;

    /** 处理备注/结果说明 */
    private String handleRemark;

    public void setFeedbackId(Long feedbackId) 
    {
        this.feedbackId = feedbackId;
    }

    public Long getFeedbackId() 
    {
        return feedbackId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setFeedbackType(Long feedbackType) 
    {
        this.feedbackType = feedbackType;
    }

    public Long getFeedbackType() 
    {
        return feedbackType;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setContactInfo(String contactInfo) 
    {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() 
    {
        return contactInfo;
    }

    public void setFeedbackStatus(Long feedbackStatus) 
    {
        this.feedbackStatus = feedbackStatus;
    }

    public Long getFeedbackStatus() 
    {
        return feedbackStatus;
    }

    public void setAdminId(Long adminId) 
    {
        this.adminId = adminId;
    }

    public Long getAdminId() 
    {
        return adminId;
    }

    public void setHandleRemark(String handleRemark) 
    {
        this.handleRemark = handleRemark;
    }

    public String getHandleRemark() 
    {
        return handleRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("feedbackId", getFeedbackId())
            .append("userId", getUserId())
            .append("feedbackType", getFeedbackType())
            .append("content", getContent())
            .append("contactInfo", getContactInfo())
            .append("feedbackStatus", getFeedbackStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("adminId", getAdminId())
            .append("handleRemark", getHandleRemark())
            .toString();
    }
}
