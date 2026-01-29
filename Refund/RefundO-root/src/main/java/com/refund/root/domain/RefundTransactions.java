package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.refund.common.annotation.Excel;
import com.refund.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 退款交易记录对象 refund_transactions
 *
 * @author ruoyi
 * @date 2025-12-22
 */
public class RefundTransactions extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 交易ID
     */
    private Long transId;

    /**
     * 申请编号
     */
    @Excel(name = "Request ID")
    private Long requestId;

    /**
     * 交易员ID
     */
    @Excel(name = "Trader ID")
    private Long adminId;

    /**
     * 汇款凭证
     */
    @Excel(name = "Remittance Receipt")
    private String remittanceReceipt;

    /**
     * 交易状态
     */
    @Excel(name = "Transaction Status")
    private Long transStatus;

    /**
     * 交易编号
     */
    @Excel(name = "Transaction Number")
    private String refundNumber;

    /**
     * 付款方式
     */
    @Excel(name = "Payment Method")
    private Integer paymentMethod;

    /**
     * 付款账号
     */
    @Excel(name = "Payment Account")
    private String paymentNumber;

    /**
     * 金额
     */
    @Excel(name = "Amount")
    private BigDecimal amount;

    /**
     * 拒绝理由
     */
    @Excel(name = "Reject Reason")
    private String rejectReason;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getTransId() {
        return transId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setRemittanceReceipt(String remittanceReceipt) {
        this.remittanceReceipt = remittanceReceipt;
    }

    public String getRemittanceReceipt() {
        return remittanceReceipt;
    }

    public void setTransStatus(Long transStatus) {
        this.transStatus = transStatus;
    }

    public Long getTransStatus() {
        return transStatus;
    }

    public void setRefundNumber(String refundNumber) {
        this.refundNumber = refundNumber;
    }

    public String getRefundNumber() {
        return refundNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("transId", getTransId())
                .append("requestId", getRequestId())
                .append("adminId", getAdminId())
                .append("remittanceReceipt", getRemittanceReceipt())
                .append("transStatus", getTransStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("refundNumber", getRefundNumber())
                .append("paymentMethod", getPaymentMethod())
                .append("paymentNumber", getPaymentNumber())
                .append("amount", getAmount())
                .append("rejectReason", getRejectReason())
                .toString();
    }
}
