package com.refund.common.exception.business;

import org.springframework.http.HttpStatus;

/**
 * 限流异常
 * 当请求超过限流阈值时抛出
 */
public class RateLimitException extends BaseException {

    public RateLimitException(String messageKey, Object... args) {
        super(messageKey, args, HttpStatus.TOO_MANY_REQUESTS); // 429 Too Many Requests
    }
}
