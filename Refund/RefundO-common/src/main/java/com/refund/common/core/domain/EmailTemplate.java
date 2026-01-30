package com.refund.common.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 邮件模板实体
 *
 * @author ruoyi
 */
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收件人邮箱
     */
    private String toEmail;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 用户名
     */
    private String username;

    /**
     * 请求编号
     */
    private String requestNumber;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 模板类型 (1:审批通过, 2:审批拒绝, 5:交易失败)
     */
    private Integer templateType;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }
}
