package com.refund.common.exception.business;

/**
 * 退款请求状态不符合
 * Refund request status exception
 */
public class RefundRequestStatusException extends BaseException {

    public RefundRequestStatusException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public RefundRequestStatusException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
