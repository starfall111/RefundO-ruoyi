package com.refund.common.exception.business;

/**
 * 产品重复扫描异常
 * Product repetition scan exception
 */
public class ProductRepetitionScanException extends BaseException {

    public ProductRepetitionScanException(String message) {
        super(message);
    }

    /**
     * 国际化构造函数
     *
     * @param messageKey 消息键
     * @param args       消息参数
     */
    public ProductRepetitionScanException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
