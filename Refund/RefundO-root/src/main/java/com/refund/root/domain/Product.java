package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 产品信息实体类
 *
 * @author refund
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 退款比例
     */
    private Double refundRatio;

    /**
     * 产品哈希值
     */
    private String hash;

    /**
     * 产品价值
     */
    private BigDecimal value;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 无参构造函数
     */
    public Product() {
    }

    /**
     * 全参数构造函数
     *
     * @param productId    产品ID
     * @param refundRatio   退款比例
     * @param hash          产品哈希值
     * @param value         产品价值
     * @param originalPrice 原价
     */
    public Product(Long productId, Double refundRatio, String hash, BigDecimal value, BigDecimal originalPrice) {
        this.productId = productId;
        this.refundRatio = refundRatio;
        this.hash = hash;
        this.value = value;
        this.originalPrice = originalPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getRefundRatio() {
        return refundRatio;
    }

    public void setRefundRatio(Double refundRatio) {
        this.refundRatio = refundRatio;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("productId", productId)
                .append("refundRatio", refundRatio)
                .append("hash", hash)
                .append("value", value)
                .append("originalPrice", originalPrice)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productId != null ? !productId.equals(product.productId) : product.productId != null) return false;
        if (hash != null ? !hash.equals(product.hash) : product.hash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        return result;
    }
}
