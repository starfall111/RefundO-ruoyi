package com.refund.root.controller;

import com.refund.common.core.domain.AjaxResult;
import com.refund.root.domain.*;
import com.refund.root.service.DataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/data_report/report")
public class DataReportController {

    @Autowired
    private DataReportService dataReportService;

    /**
     * 查询注册用户总数、活跃用户数、总扫码次数
     */
    @GetMapping("/user_report")
    public AjaxResult userCount() {
        UserReport userReport = dataReportService.userCount();
        return AjaxResult.success(userReport);
    }

    /**
     * 总退款申请数、审批率、平均审批时间
     * */
    @GetMapping("/refund_report")
    public AjaxResult refundCount() {
        RefundRequestReport refundRequestReport = dataReportService.refundCount();
        return AjaxResult.success(refundRequestReport);
    }

    /**
     * 高频用户信息
     * 规定在7天内，退款申请数超过10的用户为高频用户
     * */
    @GetMapping("/high_frequency_report")
    public AjaxResult highFrequency() {
        List<RfUsers> users = dataReportService.highFrequency();
        return AjaxResult.success(users);
    }

    /**
     * 退款余额统计：总余额、平均用户余额
     * */

    @GetMapping("/refund_balance_report")
    public AjaxResult refundBalance() {
        RefundBalanceReport refundBalanceReport = dataReportService.refundBalance();
        return AjaxResult.success(refundBalanceReport);
    }

    /**
     * 按时间统计退款申请数
     * */
    @GetMapping("/refund/{days}")
    public AjaxResult refund(@PathVariable int days) {
        RefundReports refundRequests = dataReportService.refund(days);
        return AjaxResult.success(refundRequests);
    }

    /**
     * 按时间统计扫码次数
     * */
    @GetMapping("/scan/{days}")
    public AjaxResult scan(@PathVariable int days) {
        ScanReports scanReports = dataReportService.scan(days);
        return AjaxResult.success(scanReports);
    }

    /**
     * 根据账户余额分组
     * */
    @GetMapping("/balance")
    public AjaxResult balance() {
        BalanceReport balanceReports = dataReportService.balance();
        return AjaxResult.success(balanceReports);
    }
}
