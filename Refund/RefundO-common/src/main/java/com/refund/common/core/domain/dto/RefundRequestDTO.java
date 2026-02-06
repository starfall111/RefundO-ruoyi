package com.refund.common.core.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * APP端退款请求DTO
 */
public class RefundRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 扫描ID
     */
    private String scanIds;

    /**
     * 退款账号
     * */
    private String paymentNumber;

    /**
     * 退款方式
     * */
    private Integer paymentMethod;

    /**
     * 优惠凭证
     * */
    private String voucherUrl;


    public RefundRequestDTO() {
    }

    public RefundRequestDTO(String scanIds, String paymentNumber, Integer paymentMethod, String voucherUrl) {
        this.scanIds = scanIds;
        this.paymentNumber = paymentNumber;
        this.paymentMethod = paymentMethod;
        this.voucherUrl = voucherUrl;
    }

    public String getScanIds() {
        return scanIds;
    }

    public void setScanIds(String scanIds) {
        this.scanIds = scanIds;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getVoucherUrl() {
        return voucherUrl;
    }

    public void setVoucherUrl(String voucherUrl) {
        this.voucherUrl = voucherUrl;
    }

    @Override
    public String toString() {
        return "RefundRequestDTO{" +
                "scanIds='" + scanIds + '\'' +
                ", paymentNumber='" + paymentNumber + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", voucherUrl='" + voucherUrl + '\'' +
                '}';
    }
}
