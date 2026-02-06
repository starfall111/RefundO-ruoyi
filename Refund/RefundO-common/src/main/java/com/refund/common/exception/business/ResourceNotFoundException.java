package com.refund.common.exception.business;

/**
 * 资源未找到异常类
 * 用于处理请求的资源不存在的情况
 * 错误码以C开头，表示资源错误
 */
public class ResourceNotFoundException extends CustomException {

    /**
     * 基本构造函数
     *
     * @param message 错误消息
     */
    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND", "C0001", 404);
    }

    /**
     * 带自定义错误码的构造函数
     *
     * @param message   错误消息
     * @param errorCode 自定义错误码
     */
    public ResourceNotFoundException(String message, String errorCode) {
        super(message, "RESOURCE_NOT_FOUND", errorCode, 404);
    }
}
