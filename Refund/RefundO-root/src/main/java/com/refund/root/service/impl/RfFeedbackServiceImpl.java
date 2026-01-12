package com.refund.root.service.impl;

import java.util.List;
import com.refund.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RfFeedbackMapper;
import com.refund.root.domain.RfFeedback;
import com.refund.root.service.IRfFeedbackService;

/**
 * 用户反馈Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
@Service
public class RfFeedbackServiceImpl implements IRfFeedbackService 
{
    @Autowired
    private RfFeedbackMapper rfFeedbackMapper;

    /**
     * 查询用户反馈
     * 
     * @param feedbackId 用户反馈主键
     * @return 用户反馈
     */
    @Override
    public RfFeedback selectRfFeedbackByFeedbackId(Long feedbackId)
    {
        return rfFeedbackMapper.selectRfFeedbackByFeedbackId(feedbackId);
    }

    /**
     * 查询用户反馈列表
     * 
     * @param rfFeedback 用户反馈
     * @return 用户反馈
     */
    @Override
    public List<RfFeedback> selectRfFeedbackList(RfFeedback rfFeedback)
    {
        return rfFeedbackMapper.selectRfFeedbackList(rfFeedback);
    }

    /**
     * 新增用户反馈
     * 
     * @param rfFeedback 用户反馈
     * @return 结果
     */
    @Override
    public int insertRfFeedback(RfFeedback rfFeedback)
    {
        rfFeedback.setCreateTime(DateUtils.getNowDate());
        return rfFeedbackMapper.insertRfFeedback(rfFeedback);
    }

    /**
     * 修改用户反馈
     * 
     * @param rfFeedback 用户反馈
     * @return 结果
     */
    @Override
    public int updateRfFeedback(RfFeedback rfFeedback)
    {
        rfFeedback.setUpdateTime(DateUtils.getNowDate());
        return rfFeedbackMapper.updateRfFeedback(rfFeedback);
    }

    /**
     * 批量删除用户反馈
     * 
     * @param feedbackIds 需要删除的用户反馈主键
     * @return 结果
     */
    @Override
    public int deleteRfFeedbackByFeedbackIds(Long[] feedbackIds)
    {
        return rfFeedbackMapper.deleteRfFeedbackByFeedbackIds(feedbackIds);
    }

    /**
     * 删除用户反馈信息
     * 
     * @param feedbackId 用户反馈主键
     * @return 结果
     */
    @Override
    public int deleteRfFeedbackByFeedbackId(Long feedbackId)
    {
        return rfFeedbackMapper.deleteRfFeedbackByFeedbackId(feedbackId);
    }
}
