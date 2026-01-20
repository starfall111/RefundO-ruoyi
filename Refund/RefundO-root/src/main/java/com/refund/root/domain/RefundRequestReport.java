package com.refund.root.domain;


public class RefundRequestReport {
    private Integer refundCount;
    private Double approvalRatio;
    private Double approvalTime;

    public RefundRequestReport() {
    }

    public RefundRequestReport(Integer refundCount, Double approvalRatio, Double approvalTime) {
        this.refundCount = refundCount;
        this.approvalRatio = approvalRatio;
        this.approvalTime = approvalTime;
    }

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public Double getApprovalRatio() {
        return approvalRatio;
    }

    public void setApprovalRatio(Double approvalRatio) {
        this.approvalRatio = approvalRatio;
    }

    public Double getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Double approvalTime) {
        this.approvalTime = approvalTime;
    }

    @Override
    public String toString() {
        return "RefundRequestReport{" +
                "refundCount=" + refundCount +
                ", approvalRatio=" + approvalRatio +
                ", approvalTime=" + approvalTime +
                '}';
    }
}
