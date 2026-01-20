package com.refund.root.domain;


import java.math.BigDecimal;

public class BalanceReport {
    //    0-10000
    private Double balanceFirstRatio;
    //10000-20000
    private Double balanceSecondRatio;
    //20000-50000
    private Double balanceThirdRatio;
    //50000-100000
    private Double balanceFourthRatio;
    //100000+
    private Double balanceFifthRatio;

    public BalanceReport() {
    }

    public BalanceReport(Double balanceFirstRatio, Double balanceSecondRatio, Double balanceThirdRatio, Double balanceFourthRatio, Double balanceFifthRatio) {
        this.balanceFirstRatio = balanceFirstRatio;
        this.balanceSecondRatio = balanceSecondRatio;
        this.balanceThirdRatio = balanceThirdRatio;
        this.balanceFourthRatio = balanceFourthRatio;
        this.balanceFifthRatio = balanceFifthRatio;
    }

    public Double getBalanceFirstRatio() {
        return balanceFirstRatio;
    }

    public void setBalanceFirstRatio(Double balanceFirstRatio) {
        this.balanceFirstRatio = balanceFirstRatio;
    }

    public Double getBalanceSecondRatio() {
        return balanceSecondRatio;
    }

    public void setBalanceSecondRatio(Double balanceSecondRatio) {
        this.balanceSecondRatio = balanceSecondRatio;
    }

    public Double getBalanceThirdRatio() {
        return balanceThirdRatio;
    }

    public void setBalanceThirdRatio(Double balanceThirdRatio) {
        this.balanceThirdRatio = balanceThirdRatio;
    }

    public Double getBalanceFourthRatio() {
        return balanceFourthRatio;
    }

    public void setBalanceFourthRatio(Double balanceFourthRatio) {
        this.balanceFourthRatio = balanceFourthRatio;
    }

    public Double getBalanceFifthRatio() {
        return balanceFifthRatio;
    }

    public void setBalanceFifthRatio(Double balanceFifthRatio) {
        this.balanceFifthRatio = balanceFifthRatio;
    }

    @Override
    public String toString() {
        return "BalanceReport{" +
                "balanceFirstRatio=" + balanceFirstRatio +
                ", balanceSecondRatio=" + balanceSecondRatio +
                ", balanceThirdRatio=" + balanceThirdRatio +
                ", balanceFourthRatio=" + balanceFourthRatio +
                ", balanceFifthRatio=" + balanceFifthRatio +
                '}';
    }
}
