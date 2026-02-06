package com.refund.common.exception.business;

/**
 * 认证异常类
 * 用于处理用户身份验证失败的情况
 * 错误码以B开头，表示认证错误
 */
public class AuthException extends CustomException {

    /**
     * 基本构造函数
     *
     * @param message 错误消息
     */
    public AuthException(String message) {
        super(message, "AUTHENTICATION_ERROR", "B0001", 401);
    }

    /**
     * 带自定义错误码的构造函数
     *
     * @param message   错误消息
     * @param errorCode 自定义错误码
     */
    public AuthException(String message, String errorCode) {
        super(message, "AUTHENTICATION_ERROR", errorCode, 401);
    }
}
