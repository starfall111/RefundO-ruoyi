package com.refund.common.exception.business;

/**
 * APP端自定义异常基类
 * 所有APP端业务异常都应该继承此类
 */
public class CustomException extends RuntimeException {
    // HTTP状态码
    private int code;

    // 错误类型标识
    private String error;

    // 错误码
    private String errorCode;

    // 国际化消息键
    private String messageKey;

    // 消息参数
    private Object[] args;

    // 是否启用国际化
    private boolean i18nEnabled = false;

    /**
     * 默认构造函数
     *
     * @param message 错误消息
     */
    public CustomException(String message) {
        super(message);
        this.code = 500;
        this.error = "INTERNAL_ERROR";
        this.errorCode = "D0001";
    }

    /**
     * 带状态码的构造函数
     *
     * @param message 错误消息
     * @param code    HTTP状态码
     */
    public CustomException(String message, int code) {
        super(message);
        this.code = code;
        this.error = "CUSTOM_ERROR";
        this.errorCode = "D0002";
    }

    /**
     * 带错误类型和状态码的构造函数
     *
     * @param message 错误消息
     * @param error   错误类型标识
     * @param code    HTTP状态码
     */
    public CustomException(String message, String error, int code) {
        super(message);
        this.code = code;
        this.error = error;
        this.errorCode = "D0003";
    }

    /**
     * 带完整参数的构造函数
     *
     * @param message   错误消息
     * @param error     错误类型标识
     * @param errorCode 错误码
     * @param code      HTTP状态码
     */
    public CustomException(String message, String error, String errorCode, int code) {
        super(message);
        this.code = code;
        this.error = error;
        this.errorCode = errorCode;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param error      错误类型标识
     * @param errorCode  错误码
     * @param code       HTTP状态码
     * @param args       消息参数
     */
    public CustomException(String messageKey, String error, String errorCode, int code, Object... args) {
        super(messageKey);
        this.code = code;
        this.error = error;
        this.errorCode = errorCode;
        this.messageKey = messageKey;
        this.args = args;
        this.i18nEnabled = true;
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
}
