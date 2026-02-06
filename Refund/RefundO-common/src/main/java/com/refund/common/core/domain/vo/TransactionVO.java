package com.refund.common.core.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * APP端交易记录VO
 */
public class TransactionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 交易ID
     */
    private Long transactionId;

    /**
     * 交易号
     */
    private String transactionNumber;

    /**
     * 交易类型
     */
    private String transactionType;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

    /**
     * 交易状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
