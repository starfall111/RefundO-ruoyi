package com.refund.common.exception.business;

/**
 * 产品无效异常
 * Product invalid exception
 */
public class ProductInvalidException extends BaseException {

    public ProductInvalidException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public ProductInvalidException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
