package com.refund.root.domain;

import java.math.BigDecimal;

public class RefundBalanceReport {
    private BigDecimal totalBalance;
    private BigDecimal averageBalance;

    public RefundBalanceReport() {
    }

    public RefundBalanceReport(BigDecimal totalBalance, BigDecimal averageBalance) {
        this.totalBalance = totalBalance;
        this.averageBalance = averageBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getAverageBalance() {
        return averageBalance;
    }

    public void setAverageBalance(BigDecimal averageBalance) {
        this.averageBalance = averageBalance;
    }
}
