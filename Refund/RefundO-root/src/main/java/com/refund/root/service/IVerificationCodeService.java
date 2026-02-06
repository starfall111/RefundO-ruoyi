package com.refund.root.service;

/**
 * 验证码服务接口（注册、找回密码通用）
 *
 * @author refund
 */
public interface IVerificationCodeService {

    /**
     * 发送验证码
     *
     * @param email 邮箱地址
     */
    void sendCode(String email);

    /**
     * 验证验证码
     *
     * @param email 邮箱地址
     * @param code  验证码
     */
    void verifyCode(String email, String code);
}
