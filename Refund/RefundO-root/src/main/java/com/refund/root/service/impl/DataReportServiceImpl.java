package com.refund.root.service.impl;

import com.refund.root.domain.*;
import com.refund.root.mapper.DataReportMapper;
import com.refund.root.service.DataReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataReportServiceImpl implements DataReportService {

    @Autowired
    private DataReportMapper dataReportMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private List<LocalDate> getDateList(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        return dateList;
    }

    /**
     * 查询注册用户总数、活跃用户数、总扫码次数
     */
    @Override
    public UserReport userCount() {
//      用户注册数  在redis中查找，若不存在，则查询数据库，并保存到redis中
        Integer userCount = (Integer) redisTemplate.opsForValue().get("userCount");
        if (userCount == null) {
            userCount = dataReportMapper.userCount();
            redisTemplate.opsForValue().set("userCount", userCount);
        }

//      总扫码次数  在redis中查找，若不存在，则查询数据库，并保存到redis中
        Integer scanCount = (Integer) redisTemplate.opsForValue().get("scanCount");
        if (scanCount == null) {
            scanCount = dataReportMapper.scanCount();
            redisTemplate.opsForValue().set("scanCount", scanCount);
        }

//        活跃用户数  在redis中查找，若不存在，则查询数据库，并保存到redis中
//        规定连续3天都有扫码记录或退款申请记录，则认为该用户活跃
        Integer userActionCount = (Integer) redisTemplate.opsForValue().get("userActionCount");
        if (userActionCount == null) {
            // 使用连续3天活跃的逻辑进行计算
            userActionCount = dataReportMapper.getContinuousActiveUsersCount(3);
            redisTemplate.opsForValue().set("userActionCount", userActionCount);
        }

        return new UserReport(userCount, scanCount, userActionCount);
    }

    /**
     * 总退款申请数、审批率、平均审批时间
     */
    @Override
    public RefundRequestReport refundCount() {
        // 总退款申请数 - 从Redis缓存中获取，若不存在则查询数据库并缓存
        Integer refundCount = (Integer) redisTemplate.opsForValue().get("refundCount");
        if (refundCount == null) {
            refundCount = dataReportMapper.getTotalRefundRequestsCount();
            redisTemplate.opsForValue().set("refundCount", refundCount);
        }

        // 审批率 - 从Redis缓存中获取，若不存在则查询数据库并缓存
        double approvalRatio = (double) redisTemplate.opsForValue().get("approvalRatio");
        if (approvalRatio == 0.0d) {
//            获取是所有status==4的申请，并计算审批率
            Integer totalApprovedCount = dataReportMapper.getTotalApprovedCount();
            double approvedRatio = (double) totalApprovedCount / refundCount;

            redisTemplate.opsForValue().set("approvalRatio", approvedRatio);
        }

        // 平均审批时间 - 从Redis缓存中获取，若不存在则查询数据库并缓存
        Double approvalTime = (Double) redisTemplate.opsForValue().get("approvalTime");
        if (approvalTime == null) {
            approvalTime = dataReportMapper.getAverageApprovalTime();
            redisTemplate.opsForValue().set("approvalTime", approvalTime);
        }

        return new RefundRequestReport(refundCount, approvalRatio * 100, approvalTime);
    }

    /**
     * 高频用户信息
     * 规定在7天内，退款申请数超过10的用户为高频用户
     */
    @Override
    public List<RfUsers> highFrequency() {
        // 从Redis缓存中获取，若不存在则查询数据库并缓存
        List<RfUsers> highFrequencyUsers = (List<RfUsers>) redisTemplate.opsForValue().get("highFrequencyUsers");
        if (highFrequencyUsers == null) {
            highFrequencyUsers = dataReportMapper.getHighFrequencyUsers(7, 10);
            redisTemplate.opsForValue().set("highFrequencyUsers", highFrequencyUsers);
        }

        return highFrequencyUsers;
    }

    /**
     * 退款余额统计：总余额、平均用户余额
     */
    @Override
    public RefundBalanceReport refundBalance() {
        // 总余额 - 从Redis缓存中获取，若不存在则查询数据库并缓存
        BigDecimal totalBalance = (BigDecimal) redisTemplate.opsForValue().get("totalBalance");
        if (totalBalance == null) {
            totalBalance = dataReportMapper.getTotalBalance();
            redisTemplate.opsForValue().set("totalBalance", totalBalance);
        }

        // 平均用户余额 - 从Redis缓存中获取，若不存在则查询数据库并缓存
        BigDecimal averageBalance = (BigDecimal) redisTemplate.opsForValue().get("averageBalance");
        if (averageBalance == null) {
            averageBalance = dataReportMapper.getAverageBalance();
            redisTemplate.opsForValue().set("averageBalance", averageBalance);
        }

        return new RefundBalanceReport(totalBalance, averageBalance);
    }

    /**
     * 退款申请统计
     */
    @Override
    public RefundReports refund(int days) {
//        获取现在的时间，向前推days天
        LocalDate now = LocalDate.now();
        LocalDate startTime = now.minusDays(days);

        List<LocalDate> dateList = getDateList(startTime, now);

        List<Integer> requestCountList = new ArrayList();
//        开始计算订单数
        dateList.forEach(date -> {
            RefundReports refundReport = new RefundReports();
            LocalDateTime Begin = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime End = LocalDateTime.of(date, LocalTime.MAX);
            Integer requestCount = dataReportMapper.getRefundRequestCount(Begin, End);
            requestCountList.add(requestCount);
        });


        RefundReports refundReports = new RefundReports(StringUtils.join(dateList, ","), StringUtils.join(requestCountList, ","));

        return refundReports;
    }


    /**
     * 按时间 扫码统计
     */
    @Override
    public ScanReports scan(int days) {
        LocalDate now = LocalDate.now();
        LocalDate startTime = now.minusDays(days);
        List<LocalDate> dateList = getDateList(startTime, now);
        List<Integer> scanCountList = new ArrayList();

        dateList.forEach(date -> {
            ScanReports scanReport = new ScanReports();
            LocalDateTime Begin = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime End = LocalDateTime.of(date, LocalTime.MAX);
            Integer scanCount = dataReportMapper.getScanCount(Begin, End);
            scanCountList.add(scanCount);
        });

        ScanReports scanReports = new ScanReports(StringUtils.join(dateList, ","), StringUtils.join(scanCountList, ","));

        return scanReports;
    }

    /**
     * 退款余额统计
     */
    @Override
    public BalanceReport balance() {
//      用户注册数  在redis中查找，若不存在，则查询数据库，并保存到redis中
        Integer userCount = (Integer) redisTemplate.opsForValue().get("userCount");
        if (userCount == null) {
            userCount = dataReportMapper.userCount();
            redisTemplate.opsForValue().set("userCount", userCount);
        }
//      获取退款余额分组
//       0-10000、10000-20000、20000-50000、50000-100000、100000+
        List<Integer> balanceGroups = (List<Integer>) redisTemplate.opsForValue().get("balanceGroups");
        if (balanceGroups == null) {
            balanceGroups = new ArrayList<>();
            Map<String,Object> balanceMap = dataReportMapper.getBalanceGroupStats();

            balanceGroups.add(((Number) balanceMap.get("balance_first_ratio")).intValue());
            balanceGroups.add(((Number) balanceMap.get("balance_second_ratio")).intValue());
            balanceGroups.add(((Number) balanceMap.get("balance_third_ratio")).intValue());
            balanceGroups.add(((Number) balanceMap.get("balance_fourth_ratio")).intValue());
            balanceGroups.add(((Number) balanceMap.get("balance_fifth_ratio")).intValue());
            redisTemplate.opsForValue().set("balanceGroups", balanceGroups);
        }
        BalanceReport balanceReport = new BalanceReport(
            Double.parseDouble(String.format("%.1f", (balanceGroups.get(0) / (double)userCount) * 100)),
            Double.parseDouble(String.format("%.1f", (balanceGroups.get(1) / (double)userCount) * 100)),
            Double.parseDouble(String.format("%.1f", (balanceGroups.get(2) / (double)userCount) * 100)),
            Double.parseDouble(String.format("%.1f", (balanceGroups.get(3) / (double)userCount) * 100)),
            Double.parseDouble(String.format("%.1f", (balanceGroups.get(4) / (double)userCount) * 100))
        );

        return balanceReport;
    }
}