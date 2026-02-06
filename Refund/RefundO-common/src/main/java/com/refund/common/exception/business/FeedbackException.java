package com.refund.common.exception.business;

/**
 * 反馈相关异常
 * Feedback exception
 */
public class FeedbackException extends BaseException {

    public FeedbackException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public FeedbackException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
