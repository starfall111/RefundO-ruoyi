package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户反馈实体类
 *
 * @author refund
 */
public class FeedBack implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 反馈ID
     */
    private Long feedBackId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 反馈类型
     */
    private Integer feedBackType;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 联系信息
     */
    private String contactInfo;

    /**
     * 反馈状态
     */
    private Integer feedBackStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 附件URL
     */
    private String attachmentUrl;

    /**
     * 无参构造函数
     */
    public FeedBack() {
    }

    /**
     * 全参数构造函数
     */
    public FeedBack(Long feedBackId, Long userId, Integer feedBackType, String content,
                     String contactInfo, Integer feedBackStatus, LocalDateTime createTime, String attachmentUrl) {
        this.feedBackId = feedBackId;
        this.userId = userId;
        this.feedBackType = feedBackType;
        this.content = content;
        this.contactInfo = contactInfo;
        this.feedBackStatus = feedBackStatus;
        this.createTime = createTime;
        this.attachmentUrl = attachmentUrl;
    }

    public Long getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Long feedBackId) {
        this.feedBackId = feedBackId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getFeedBackType() {
        return feedBackType;
    }

    public void setFeedBackType(Integer feedBackType) {
        this.feedBackType = feedBackType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Integer getFeedBackStatus() {
        return feedBackStatus;
    }

    public void setFeedBackStatus(Integer feedBackStatus) {
        this.feedBackStatus = feedBackStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("feedBackId", feedBackId)
                .append("userId", userId)
                .append("feedBackType", feedBackType)
                .append("content", content)
                .append("contactInfo", contactInfo)
                .append("feedBackStatus", feedBackStatus)
                .append("createTime", createTime)
                .append("attachmentUrl", attachmentUrl)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedBack feedBack = (FeedBack) o;

        return feedBackId != null ? feedBackId.equals(feedBack.feedBackId) : feedBack.feedBackId == null;
    }

    @Override
    public int hashCode() {
        return feedBackId != null ? feedBackId.hashCode() : 0;
    }
}
