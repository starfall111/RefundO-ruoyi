package com.refund.common.service;

import com.refund.common.core.domain.EmailTemplate;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * 邮件服务接口
 *
 * @author ruoyi
 */
public interface IMailService {

    /**
     * 发送退款审批通过邮件
     *
     * @param toEmail 收件人邮箱
     * @param username 用户名
     * @param requestNumber 请求编号
     * @param amount 金额
     */
    void sendRefundApprovedEmail(String toEmail, String username, String requestNumber, BigDecimal amount);

    /**
     * 发送退款审批拒绝邮件
     *
     * @param toEmail 收件人邮箱
     * @param username 用户名
     * @param requestNumber 请求编号
     * @param rejectReason 拒绝原因
     */
    void sendRefundRejectedEmail(String toEmail, String username, String requestNumber, String rejectReason);

    /**
     * 发送交易失败邮件
     *
     * @param toEmail 收件人邮箱
     * @param username 用户名
     * @param requestNumber 请求编号
     * @param rejectReason 拒绝原因
     */
    void sendTransactionFailedEmail(String toEmail, String username, String requestNumber, String rejectReason);

    /**
     * 异步发送邮件
     *
     * @param template 邮件模板
     */
    void sendEmailAsync(EmailTemplate template);

    /**
     * 发送验证码邮件
     *
     * @param toEmail 收件人邮箱
     * @param verificationCode 验证码
     * @param locale 国际化Locale
     */
    void sendVerificationCodeEmail(String toEmail, String verificationCode, Locale locale);

    /**
     * 异步发送验证码邮件
     *
     * @param toEmail 收件人邮箱
     * @param verificationCode 验证码
     * @param locale 国际化Locale
     */
    void sendVerificationCodeEmailAsync(String toEmail, String verificationCode, Locale locale);
}
