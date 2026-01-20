package com.refund.root.task;

import com.refund.root.mapper.DataReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 活跃用户统计数据更新任务
 * 
 * @author ruoyi
 */
@Component("activeUserStatisticsTask")
public class ActiveUserStatisticsTask
{
    @Autowired
    private DataReportMapper dataReportMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 更新活跃用户统计数据
     * 每天凌晨1点执行，更新连续3天都有扫码记录或退款申请记录的活跃用户数
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateActiveUserStatistics()
    {
        System.out.println("开始执行活跃用户统计数据更新任务...");
        
        try {
            // 计算连续3天都有活动记录的用户数
            Integer activeUserCount = dataReportMapper.getContinuousActiveUsersCount(3);
            
            // 更新Redis缓存
            redisTemplate.opsForValue().set("userActionCount", activeUserCount);
            
            System.out.println("活跃用户统计数据更新完成，活跃用户数：" + activeUserCount);
        } catch (Exception e) {
            System.err.println("活跃用户统计数据更新失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 更新所有统计数据
     * 每小时执行，更新注册用户数和扫码次数
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void updateAllStatistics()
    {
        System.out.println("开始执行所有统计数据更新任务...");
        
        try {
            // 更新注册用户数
            Integer userCount = dataReportMapper.userCount();
            redisTemplate.opsForValue().set("userCount", userCount);
            
            // 更新扫码次数
            Integer scanCount = dataReportMapper.scanCount();
            redisTemplate.opsForValue().set("scanCount", scanCount);
            
            System.out.println("统计数据更新完成 - 注册用户数：" + userCount + "，扫码次数：" + scanCount);
        } catch (Exception e) {
            System.err.println("统计数据更新失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}