package com.refund.common.exception.business;

import org.springframework.http.HttpStatus;

/**
 * APP端业务异常基类
 * 所有APP端业务异常都应该继承此类
 */
public class BaseException extends RuntimeException {

    // 国际化消息键
    private String messageKey;

    // 消息参数
    private Object[] args;

    // 是否启用国际化
    private boolean i18nEnabled = false;

    // HTTP 状态码
    private HttpStatus httpStatus;

    BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public BaseException(String messageKey, Object... args) {
        super(messageKey); // 默认使用消息键作为消息，由处理器解析
        this.messageKey = messageKey;
        this.args = args;
        this.i18nEnabled = true;
    }

    /**
     * 国际化构造函数（带HTTP状态码）
     *
     * @param messageKey 消息键
     * @param args       消息参数
     * @param httpStatus HTTP状态码
     */
    public BaseException(String messageKey, Object[] args, HttpStatus httpStatus) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
        this.i18nEnabled = true;
        this.httpStatus = httpStatus;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getArgs() {
        return args;
    }

    public boolean isI18nEnabled() {
        return i18nEnabled;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
