package com.refund.common.exception.business;

/**
 * 密码错误异常
 * Password error exception
 */
public class PasswordErrorException extends BaseException {

    public PasswordErrorException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public PasswordErrorException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
