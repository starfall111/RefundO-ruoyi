package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退款请求实体类
 *
 * @author refund
 */
public class RefundRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求ID
     */
    private Long requestId;

    /**
     * 请求编号
     */
    private String requestNumber;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 扫描记录IDs（逗号分隔）
     */
    private String scanIds;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 凭证URL
     */
    private String voucherUrl;

    /**
     * 请求状态
     */
    private Integer requestStatus;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 支付方式
     */
    private Integer paymentMethod;

    /**
     * 支付账号
     */
    private String paymentNumber;

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
    public RefundRequest() {
    }

    /**
     * 全参数构造函数
     */
    public RefundRequest(Long requestId, String requestNumber, Long userId, String scanIds,
                          BigDecimal amount, String voucherUrl, Integer requestStatus,
                          String rejectReason, Integer paymentMethod, String paymentNumber,
                          LocalDateTime createTime, LocalDateTime updateTime) {
        this.requestId = requestId;
        this.requestNumber = requestNumber;
        this.userId = userId;
        this.scanIds = scanIds;
        this.amount = amount;
        this.voucherUrl = voucherUrl;
        this.requestStatus = requestStatus;
        this.rejectReason = rejectReason;
        this.paymentMethod = paymentMethod;
        this.paymentNumber = paymentNumber;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getScanIds() {
        return scanIds;
    }

    public void setScanIds(String scanIds) {
        this.scanIds = scanIds;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getVoucherUrl() {
        return voucherUrl;
    }

    public void setVoucherUrl(String voucherUrl) {
        this.voucherUrl = voucherUrl;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
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
                .append("requestId", requestId)
                .append("requestNumber", requestNumber)
                .append("userId", userId)
                .append("scanIds", scanIds)
                .append("amount", amount)
                .append("voucherUrl", voucherUrl)
                .append("requestStatus", requestStatus)
                .append("rejectReason", rejectReason)
                .append("paymentMethod", paymentMethod)
                .append("paymentNumber", paymentNumber)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundRequest that = (RefundRequest) o;

        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return requestId != null ? requestId.hashCode() : 0;
    }
}
