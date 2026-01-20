package com.refund.root.domain;

public class ScanReports {
    private String dateList;
    private String scanList;

    public ScanReports() {
    }

    public ScanReports(String dateList, String scanList) {
        this.dateList = dateList;
        this.scanList = scanList;
    }

    public String getDateList() {
        return dateList;
    }

    public void setDateList(String dateList) {
        this.dateList = dateList;
    }

    public String getScanList() {
        return scanList;
    }

    public void setScanList(String scanList) {
        this.scanList = scanList;
    }

    @Override
    public String toString() {
        return "ScanReports{" +
                "dateList='" + dateList + '\'' +
                ", scanList='" + scanList + '\'' +
                '}';
    }
}
