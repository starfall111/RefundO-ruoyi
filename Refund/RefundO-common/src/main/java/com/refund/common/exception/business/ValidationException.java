package com.refund.common.exception.business;

/**
 * 数据验证异常类
 * 用于处理请求参数验证失败的情况
 * 错误码以A开头，表示验证错误
 */
public class ValidationException extends CustomException {

    /**
     * 基本构造函数
     *
     * @param message 错误消息
     */
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", "A0001", 400);
    }

    /**
     * 带自定义错误码的构造函数
     *
     * @param message   错误消息
     * @param errorCode 自定义错误码
     */
    public ValidationException(String message, String errorCode) {
        super(message, "VALIDATION_ERROR", errorCode, 400);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param errorCode  自定义错误码
     * @param args       消息参数
     */
    public ValidationException(String messageKey, String errorCode, Object... args) {
        super(messageKey, "VALIDATION_ERROR", errorCode, 400, args);
    }
}
