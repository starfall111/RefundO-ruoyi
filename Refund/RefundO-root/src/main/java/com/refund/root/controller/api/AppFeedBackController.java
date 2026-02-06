package com.refund.root.controller.api;

import com.refund.common.core.domain.Result;
import com.refund.common.exception.business.FeedbackException;
import com.refund.common.exception.business.MessageKeys;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.root.domain.RfFeedback;
import com.refund.root.service.IRfFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APP端反馈控制器
 */
@RestController
@RequestMapping("/api/feedback")
public class AppFeedBackController {

    @Autowired
    private IRfFeedbackService rfFeedbackService;

    /**
     * 提交反馈
     *
     * @param content       反馈内容（必填）
     * @param feedbackType  反馈类型（默认1）
     * @param contactInfo   联系方式（可选）
     * @param attachmentUrl 附件URL（可选）
     * @return 提交结果
     */
    @PostMapping("/submit")
    public Result<Void> submitFeedback(@RequestParam String content,
                                       @RequestParam(defaultValue = "1") Long feedbackType,
                                       @RequestParam(required = false) String contactInfo,
                                       @RequestParam(required = false) String attachmentUrl) {
        // 获取当前用户ID
        Long userId = ApiSecurityUtils.getUserId();

        // 创建反馈对象
        RfFeedback feedback = new RfFeedback();
        feedback.setUserId(userId);
        feedback.setFeedbackType(feedbackType);
        feedback.setContent(content);
        feedback.setContactInfo(contactInfo);
        feedback.setFeedbackStatus(0L); // 0 表示待处理

        rfFeedbackService.insertRfFeedback(feedback);
        return Result.success();
    }

    /**
     * 获取当前用户的反馈记录
     *
     * @return 反馈记录列表
     */
    @GetMapping("/my-feedbacks")
    public Result<List<RfFeedback>> getUserFeedbacks() {
        // 获取当前用户ID
        Long userId = ApiSecurityUtils.getUserId();

        // 查询条件
        RfFeedback query = new RfFeedback();
        query.setUserId(userId);

        List<RfFeedback> feedbacks = rfFeedbackService.selectRfFeedbackList(query);
        return Result.success(feedbacks);
    }

    /**
     * 根据反馈ID获取反馈详情
     *
     * @param feedbackId 反馈ID
     * @return 反馈详情
     */
    @GetMapping("/{feedbackId}")
    public Result<RfFeedback> getFeedbackById(@PathVariable Long feedbackId) {
        RfFeedback feedback = rfFeedbackService.selectRfFeedbackByFeedbackId(feedbackId);

        // 验证反馈属于当前用户
        if (feedback != null) {
            Long currentUserId = ApiSecurityUtils.getUserId();
            if (!feedback.getUserId().equals(currentUserId)) {
                throw new FeedbackException(MessageKeys.FEEDBACK_UNAUTHORIZED, null);
            }
        }

        return Result.success(feedback);
    }

    /**
     * 撤回反馈
     *
     * @param feedbackId 反馈ID
     * @return 撤回结果
     */
    @PutMapping("/withdraw/{feedbackId}")
    public Result<Void> withdrawFeedback(@PathVariable Long feedbackId) {
        // 获取反馈详情
        RfFeedback feedback = rfFeedbackService.selectRfFeedbackByFeedbackId(feedbackId);

        if (feedback == null) {
            throw new FeedbackException(MessageKeys.FEEDBACK_NOT_EXIST, null);
        }

        // 验证反馈属于当前用户
        Long currentUserId = ApiSecurityUtils.getUserId();
        if (!feedback.getUserId().equals(currentUserId)) {
            throw new FeedbackException(MessageKeys.FEEDBACK_UNAUTHORIZED, null);
        }

        // 将反馈状态设置为撤回状态（3表示已关闭/已撤回）
        feedback.setFeedbackStatus(3L);
        rfFeedbackService.updateRfFeedback(feedback);

        return Result.success();
    }
}
