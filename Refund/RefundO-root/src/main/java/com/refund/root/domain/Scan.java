package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 扫描记录实体类
 *
 * @author refund
 */
public class Scan implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 扫描ID
     */
    private Long scanId;

    /**
     * 扫描编号
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
     * 退款状态
     */
    private Integer refundStatus;

    /**
     * 无参构造函数
     */
    public Scan() {
    }

    /**
     * 全参数构造函数
     *
     * @param scanId       扫描ID
     * @param scanNumber   扫描编号
     * @param userId       用户ID
     * @param productId    产品ID
     * @param scanTime     扫描时间
     * @param refundStatus 退款状态
     */
    public Scan(Long scanId, String scanNumber, Long userId, Long productId, LocalDateTime scanTime, Integer refundStatus) {
        this.scanId = scanId;
        this.scanNumber = scanNumber;
        this.userId = userId;
        this.productId = productId;
        this.scanTime = scanTime;
        this.refundStatus = refundStatus;
    }

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

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("scanId", scanId)
                .append("scanNumber", scanNumber)
                .append("userId", userId)
                .append("productId", productId)
                .append("scanTime", scanTime)
                .append("refundStatus", refundStatus)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scan scan = (Scan) o;

        return scanId != null ? scanId.equals(scan.scanId) : scan.scanId == null;
    }

    @Override
    public int hashCode() {
        return scanId != null ? scanId.hashCode() : 0;
    }
}
