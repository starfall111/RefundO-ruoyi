package com.refund.common.exception.business;

/**
 * 账号不存在异常
 * Account not found exception
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public AccountNotFoundException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
