package com.refund.root.mapper;

import com.refund.root.domain.RfUsers;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface DataReportMapper {
    
    /**
     * 查询注册用户总数
     * */
    Integer userCount();


    /**
     * 查询扫码次数
     * */
    Integer scanCount();

    /**
     * 查询活跃用户数 - 连续3天都有扫码记录或退款申请记录
     * */
    Integer userActionCount();

    /**
     * 获取最近几天有活动记录的用户ID
     * */
    List<Long> getUserIdsWithActivityInDays(int days);

    /**
     * 获取连续指定天数都有活动记录的用户数量
     * */
    Integer getContinuousActiveUsersCount(int days);
    
    /**
     * 获取总退款申请数
     * */
    Integer getTotalRefundRequestsCount();

    /**
     * 获取平均审批时间
     * */
    Double getAverageApprovalTime();
    
    /**
     * 获取高频用户
     * @param days 天数
     * @param threshold 阈值
     * */
    List<RfUsers> getHighFrequencyUsers(@Param("days") int days, @Param("threshold") int threshold);
    
    /**
     * 获取总余额
     * */
    BigDecimal getTotalBalance();
    
    /**
     * 获取平均用户余额
     * */
    BigDecimal getAverageBalance();

    /**
     * 获取总通过申请数
     * */
    Integer getTotalApprovedCount();

    /**
     * 根据时间 获取退款申请数
     * */
    Integer getRefundRequestCount(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);

    /**
     * 根据时间 获取扫码次数
     *
     *  */
    Integer getScanCount(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);
    
    /**
     * 获取余额分组统计
     * */
    Map<String, Object> getBalanceGroupStats();
}