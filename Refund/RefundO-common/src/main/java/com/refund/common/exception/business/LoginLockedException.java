package com.refund.common.exception.business;

import org.springframework.http.HttpStatus;

/**
 * 登录锁定异常
 * 当IP或账号因多次登录失败被锁定时抛出
 */
public class LoginLockedException extends BaseException {

    public LoginLockedException(String messageKey, Object[] args) {
        super(messageKey, args, HttpStatus.LOCKED); // 423 Locked
    }
}
