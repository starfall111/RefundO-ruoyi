package com.refund.root.task;
import com.refund.root.mapper.DataReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("refundRequestStatisticsTask")
public class RefundRequestStatisticsTask {

    @Autowired
    private DataReportMapper dataReportMapper;

    @Autowired
    private RedisTemplate redisTemplate;


//    每小时更新退款申请统计数据
    @Scheduled(cron = "0 0 * * * ?")
    public void updateRefundRequestStatistics() {
//        总退款申请
        Integer totalRefundRequestsCount = dataReportMapper.getTotalRefundRequestsCount();
        redisTemplate.opsForValue().set("totalRefundRequestsCount", totalRefundRequestsCount);

//        审批率
        Integer totalApprovedCount = dataReportMapper.getTotalApprovedCount();
        double approvalRatio = (double) totalApprovedCount / totalRefundRequestsCount;
        redisTemplate.opsForValue().set("approvalRatio", approvalRatio);

//        平均审批时间
        Double averageApprovalTime = dataReportMapper.getAverageApprovalTime();
        redisTemplate.opsForValue().set("averageApprovalTime", averageApprovalTime);

    }
}
