package com.refund.root.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.refund.common.annotation.Excel;
import com.refund.common.core.domain.BaseEntity;

/**
 * 退款申请对象 refund_requests
 *
 * @author ruoyi
 * @date 2025-12-22
 */
public class RefundRequests extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 申请ID
     */
    private Long requestId;

    /**
     * 申请状态
     */
    @Excel(name = "Request Status")
    private Long requestStatus;

    /**
     * 申请金额
     */
    @Excel(name = "Request Amount")
    private BigDecimal amount;

    /**
     * 凭证图片
     */
    private String voucherUrl;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 用户ID
     */
    @Excel(name = "User ID")
    private Long userId;

    /**
     * 处理员ID
     */
    @Excel(name = "Handler ID")
    private Long adminId;

    /**
     * 支付方式 0为手机 1为sangke 2为wave
     */
    @Excel(name = "Payment Method (0=Mobile, 1=Sangke, 2=Wave)")
    private Integer paymentMethod;

    /**
     * 支付账号
     */
    private String paymentNumber;

    /**
     * 请求编号
     */
    @Excel(name = "Request Number")
    private String requestNumber;

    /**
     * 扫描ID
     */
    @Excel(name = "Scan IDs")
    private String scanIds;

    public String getScanId() {
        return scanIds;
    }

    public void setScanId(String scanIds) {
        this.scanIds = scanIds;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestStatus(Long requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Long getRequestStatus() {
        return requestStatus;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setVoucherUrl(String voucherUrl) {
        this.voucherUrl = voucherUrl;
    }

    public String getVoucherUrl() {
        return voucherUrl;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("requestId", getRequestId())
                .append("requestStatus", getRequestStatus())
                .append("amount", getAmount())
                .append("voucherUrl", getVoucherUrl())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("rejectReason", getRejectReason())
                .append("userId", getUserId())
                .append("adminId", getAdminId())
                .append("paymentMethod", getPaymentMethod())
                .append("paymentNumber", getPaymentNumber())
                .append("requestNumber", getRequestNumber())
                .append("scanId", getScanId())
                .toString();
    }
}
