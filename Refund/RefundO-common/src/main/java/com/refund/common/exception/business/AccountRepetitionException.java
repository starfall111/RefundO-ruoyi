package com.refund.common.exception.business;

/**
 * 账号重复异常
 * Account repetition exception
 */
public class AccountRepetitionException extends BaseException {

    public AccountRepetitionException() {
    }

    public AccountRepetitionException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public AccountRepetitionException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
