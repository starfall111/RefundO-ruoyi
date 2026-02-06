package com.refund.common.exception.business;

/**
 * 验证码错误异常
 * Code error exception
 */
public class CodeErrorException extends BaseException {

    public CodeErrorException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public CodeErrorException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
