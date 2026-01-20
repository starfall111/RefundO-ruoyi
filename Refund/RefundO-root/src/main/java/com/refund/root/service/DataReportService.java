package com.refund.root.service;

import com.refund.root.domain.*;

import java.util.List;

public interface DataReportService {

    /**
     * 查询注册用户总数、活跃用户数、总扫码次数
     * */
    UserReport userCount();
    
    /**
     * 总退款申请数、审批率、平均审批时间
     * */
    RefundRequestReport refundCount();
    
    /**
     * 高频用户信息
     * 规定在7天内，退款申请数超过10的用户为高频用户
     * */
    List<RfUsers> highFrequency();
    
    /**
     * 退款余额统计：总余额、平均用户余额
     * */
    RefundBalanceReport refundBalance();

    /**
     * 按照时间退款申请统计
     * */
    RefundReports refund(int days);

    /**
     * 按时间统计扫码次数
     * */
    ScanReports scan(int days);

    /**
     * 退款余额占比
     * */
    BalanceReport balance();
}