package com.refund.common.exception.business;

/**
 * 邮箱发送错误异常
 * Email send error exception
 * 注：类名保留原有拼写（Eamil而非Email）
 */
public class EamilSendErrorException extends BaseException {

    public EamilSendErrorException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public EamilSendErrorException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
