package com.refund.root.domain;

public class UserReport {
    private Integer userCount;
    private Integer scanCount;
    private Integer userActionCount;

    public UserReport() {
    }

    public UserReport(Integer userCount, Integer scanCount, Integer userActionCount) {
        this.userCount = userCount;
        this.scanCount = scanCount;
        this.userActionCount = userActionCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
    }

    public Integer getUserActionCount() {
        return userActionCount;
    }

    public void setUserActionCount(Integer userActionCount) {
        this.userActionCount = userActionCount;
    }

    @Override
    public String toString() {
        return "UserReport{" +
                "userCount=" + userCount +
                ", scanCount=" + scanCount +
                ", userActionCount=" + userActionCount +
                '}';
    }
}
