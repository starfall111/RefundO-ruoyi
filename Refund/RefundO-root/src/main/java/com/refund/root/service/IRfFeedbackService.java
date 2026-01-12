package com.refund.root.service;

import java.util.List;
import com.refund.root.domain.RfFeedback;

/**
 * 用户反馈Service接口
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
public interface IRfFeedbackService 
{
    /**
     * 查询用户反馈
     * 
     * @param feedbackId 用户反馈主键
     * @return 用户反馈
     */
    public RfFeedback selectRfFeedbackByFeedbackId(Long feedbackId);

    /**
     * 查询用户反馈列表
     * 
     * @param rfFeedback 用户反馈
     * @return 用户反馈集合
     */
    public List<RfFeedback> selectRfFeedbackList(RfFeedback rfFeedback);

    /**
     * 新增用户反馈
     * 
     * @param rfFeedback 用户反馈
     * @return 结果
     */
    public int insertRfFeedback(RfFeedback rfFeedback);

    /**
     * 修改用户反馈
     * 
     * @param rfFeedback 用户反馈
     * @return 结果
     */
    public int updateRfFeedback(RfFeedback rfFeedback);

    /**
     * 批量删除用户反馈
     * 
     * @param feedbackIds 需要删除的用户反馈主键集合
     * @return 结果
     */
    public int deleteRfFeedbackByFeedbackIds(Long[] feedbackIds);

    /**
     * 删除用户反馈信息
     * 
     * @param feedbackId 用户反馈主键
     * @return 结果
     */
    public int deleteRfFeedbackByFeedbackId(Long feedbackId);
}
