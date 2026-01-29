package com.refund.root.domain;

import java.util.Date;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.refund.common.annotation.Excel;
import com.refund.common.core.domain.BaseEntity;

/**
 * 用户扫码记录对象 rf_scan_records
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public class RfScanRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 扫描ID */
    @Excel(name = "Scan ID")
    private Long scanId;

    /** 扫码时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Scan Time", width = 30, dateFormat = "yyyy-MM-dd")
    private Date scanTime;

    /** 是否离线扫码: 1-是 0-否 */
    private Integer isOffline;

    /** 用户ID */
    @Excel(name = "User ID")
    private Long userId;

    /** 产品ID */
    @Excel(name = "Product ID")
    private Long productId;

    /** 产品状态 */
    @Excel(name = "Product Status")
    private Integer refundStatus;

    /** 扫描编号 */
    @Excel(name = "Scan Number")
    private String scanNumber;

    /** 退款金额 */
    @Excel(name = "Refund Amount")
    private BigDecimal value;

    /** 退款比例 */
    @Excel(name = "Refund Ratio")
    private BigDecimal refundRatio;

    /** 原价 */
    @Excel(name = "Original Price")
    private BigDecimal originalPrice;

    public void setScanId(Long scanId) 
    {
        this.scanId = scanId;
    }

    public Long getScanId() 
    {
        return scanId;
    }

    public void setScanTime(Date scanTime) 
    {
        this.scanTime = scanTime;
    }

    public Date getScanTime() 
    {
        return scanTime;
    }

    public void setIsOffline(Integer isOffline) 
    {
        this.isOffline = isOffline;
    }

    public Integer getIsOffline() 
    {
        return isOffline;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setRefundStatus(Integer refundStatus) 
    {
        this.refundStatus = refundStatus;
    }

    public Integer getRefundStatus() 
    {
        return refundStatus;
    }

    public void setScanNumber(String scanNumber) 
    {
        this.scanNumber = scanNumber;
    }

    public String getScanNumber() 
    {
        return scanNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getRefundRatio() {
        return refundRatio;
    }

    public void setRefundRatio(BigDecimal refundRatio) {
        this.refundRatio = refundRatio;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("scanId", getScanId())
            .append("scanTime", getScanTime())
            .append("isOffline", getIsOffline())
            .append("userId", getUserId())
            .append("productId", getProductId())
            .append("refundStatus", getRefundStatus())
            .append("scanNumber", getScanNumber())
            .append("value", getValue())
            .append("refundRatio", getRefundRatio())
            .append("originalPrice", getOriginalPrice())
            .toString();
    }
}