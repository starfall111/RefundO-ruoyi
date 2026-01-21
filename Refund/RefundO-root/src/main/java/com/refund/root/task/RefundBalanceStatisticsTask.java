package com.refund.root.task;

import com.refund.root.mapper.DataReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 退款余额统计更新任务
 * 
 * @author ruoyi
 */

@Component("refundBalanceStatisticsTask")
public class RefundBalanceStatisticsTask
{
    @Autowired
    private DataReportMapper dataReportMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 更新退款余额统计数据
     * 每天凌晨1点执行，更新总余额和平均用户余额
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateRefundBalanceStatistics()
    {
        System.out.println("开始执行退款余额统计数据更新任务...");
        
        try {
            // 更新总余额
            BigDecimal totalBalance = dataReportMapper.getTotalBalance();
            redisTemplate.opsForValue().set("totalBalance", totalBalance);
            
            // 更新平均用户余额
            BigDecimal averageBalance = dataReportMapper.getAverageBalance();
            redisTemplate.opsForValue().set("averageBalance", averageBalance);
            
            System.out.println("退款余额统计数据更新完成，总余额：" + totalBalance + "，平均余额：" + averageBalance);
        } catch (Exception e) {
            System.err.println("退款余额统计数据更新失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 更新高频用户统计数据
     * 每天凌晨2点执行，更新7天内退款申请数超过10的高频用户
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void updateHighFrequencyUsers()
    {
        System.out.println("开始执行高频用户统计数据更新任务...");
        
        try {
            // 更新高频用户列表
            java.util.List<com.refund.root.domain.RfUsers> highFrequencyUsers = dataReportMapper.getHighFrequencyUsers(7, 10);
            redisTemplate.opsForValue().set("highFrequencyUsers", highFrequencyUsers);
            
            System.out.println("高频用户统计数据更新完成，共" + (highFrequencyUsers != null ? highFrequencyUsers.size() : 0) + "个高频用户");
        } catch (Exception e) {
            System.err.println("高频用户统计数据更新失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 统计余额分布情况
     * */
    @Scheduled(cron = "0 0 * * * ?")
    public void balanceStatus(){
        try{
            List<Integer> balanceGroups = new ArrayList<>();
            Map<String,Object> balanceMap = dataReportMapper.getBalanceGroupStats();

            balanceGroups.add(((Number) balanceMap.get("balance_first_ratio")).intValue());
            balanceGroups.add(((Number) balanceMap.get("balance_second_ratio")).intValue());
            balanceGroups.add(((Number) balanceMap.get("balance_third_ratio")).intValue());
            balanceGroups.add(((Number) balanceMap.get("balance_fourth_ratio")).intValue());
            balanceGroups.add(((Number) balanceMap.get("balance_fifth_ratio")).intValue());
            redisTemplate.opsForValue().set("balanceGroups", balanceGroups);
        }catch (Exception e){
            System.out.println("统计余额分布情况失败：" + e.getMessage());
            e.printStackTrace();
        }

    }

}