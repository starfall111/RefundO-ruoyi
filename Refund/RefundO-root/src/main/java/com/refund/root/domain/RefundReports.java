package com.refund.root.domain;

public class RefundReports {
    private String dateList;
    private String refundList;

    public RefundReports() {
    }

    public RefundReports(String dateList, String refundList) {
        this.dateList = dateList;
        this.refundList = refundList;
    }

    public String getDateList() {
        return dateList;
    }

    public void setDateList(String dateList) {
        this.dateList = dateList;
    }

    public String getRefundList() {
        return refundList;
    }

    public void setRefundList(String refundList) {
        this.refundList = refundList;
    }

    @Override
    public String toString() {
        return "RefundReports{" +
                "dateList='" + dateList + '\'' +
                ", refundList='" + refundList + '\'' +
                '}';
    }
}
