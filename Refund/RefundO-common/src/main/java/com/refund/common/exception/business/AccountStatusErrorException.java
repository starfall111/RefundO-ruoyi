package com.refund.common.exception.business;

/**
 * 账号状态异常
 * Account status exception
 */
public class AccountStatusErrorException extends BaseException {

    public AccountStatusErrorException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public AccountStatusErrorException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
