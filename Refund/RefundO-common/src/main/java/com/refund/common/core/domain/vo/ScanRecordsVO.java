package com.refund.common.core.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * APP端扫描记录VO
 */
public class ScanRecordsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 扫描ID
     */
    private Long scanId;

    /**
     * 扫描号
     */
    private String scanNumber;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 扫描时间
     */
    private LocalDateTime scanTime;

    /**
     * 退款比例
     */
    private BigDecimal refundRatio;

    /**
     * 产品价值
     */
    private BigDecimal value;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 产品名称（可选，用于显示）
     */
    private String productName;

    /**
     * 退款状态
     * 0-未退款，1-已申请
     */
    private Integer refundStatus;

    public Long getScanId() {
        return scanId;
    }

    public void setScanId(Long scanId) {
        this.scanId = scanId;
    }

    public String getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(String scanNumber) {
        this.scanNumber = scanNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public LocalDateTime getScanTime() {
        return scanTime;
    }

    public void setScanTime(LocalDateTime scanTime) {
        this.scanTime = scanTime;
    }

    public BigDecimal getRefundRatio() {
        return refundRatio;
    }

    public void setRefundRatio(BigDecimal refundRatio) {
        this.refundRatio = refundRatio;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }
}
