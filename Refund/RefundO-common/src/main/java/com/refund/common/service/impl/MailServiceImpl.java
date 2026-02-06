package com.refund.common.service.impl;

import com.refund.common.core.domain.EmailTemplate;
import com.refund.common.service.IMailService;
import com.refund.common.utils.ApiMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * 邮件服务实现
 *
 * @author ruoyi
 */
@Service
public class MailServiceImpl implements IMailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${verification.code.expire-minutes:3}")
    private int defaultExpireMinutes;

    /**
     * 发送退款审批通过邮件
     */
    @Override
    public void sendRefundApprovedEmail(String toEmail, String username, String requestNumber, BigDecimal amount) {
        EmailTemplate template = new EmailTemplate();
        template.setToEmail(toEmail);
        template.setUsername(username);
        template.setRequestNumber(requestNumber);
        template.setAmount(amount);
        template.setTemplateType(1);

        String subject = "Refund Request Approved - " + requestNumber;
        String content = buildApprovedEmailContent(username, requestNumber, amount);

        template.setSubject(subject);
        template.setContent(content);

        sendEmailAsync(template);
    }

    /**
     * 发送退款审批拒绝邮件
     */
    @Override
    public void sendRefundRejectedEmail(String toEmail, String username, String requestNumber, String rejectReason) {
        EmailTemplate template = new EmailTemplate();
        template.setToEmail(toEmail);
        template.setUsername(username);
        template.setRequestNumber(requestNumber);
        template.setRejectReason(rejectReason);
        template.setTemplateType(2);

        String subject = "Refund Request Rejected - " + requestNumber;
        String content = buildRejectedEmailContent(username, requestNumber, rejectReason);

        template.setSubject(subject);
        template.setContent(content);

        sendEmailAsync(template);
    }

    /**
     * 发送交易失败邮件
     */
    @Override
    public void sendTransactionFailedEmail(String toEmail, String username, String requestNumber, String rejectReason) {
        EmailTemplate template = new EmailTemplate();
        template.setToEmail(toEmail);
        template.setUsername(username);
        template.setRequestNumber(requestNumber);
        template.setRejectReason(rejectReason);
        template.setTemplateType(5);

        String subject = "Transaction Failed - " + requestNumber;
        String content = buildTransactionFailedEmailContent(username, requestNumber, rejectReason);

        template.setSubject(subject);
        template.setContent(content);

        sendEmailAsync(template);
    }

    /**
     * 发送验证码邮件
     */
    @Override
    public void sendVerificationCodeEmail(String toEmail, String verificationCode, Locale locale) {
        sendVerificationCodeEmailAsync(toEmail, verificationCode, locale);
    }

    /**
     * 异步发送验证码邮件
     */
    @Async("mailExecutor")
    @Override
    public void sendVerificationCodeEmailAsync(String toEmail, String verificationCode, Locale locale) {
        try {
            EmailTemplate template = new EmailTemplate();
            template.setToEmail(toEmail);
            template.setVerificationCode(verificationCode);
            template.setExpireMinutes(defaultExpireMinutes);
            template.setTemplateType(3);

            // 使用传入的 locale 获取国际化邮件主题和内容
            String subject = ApiMessageUtils.getMessage("email.verification.subject", locale);
            String content = ApiMessageUtils.getMessage("email.verification.content", locale,
                    new Object[]{verificationCode, defaultExpireMinutes});

            template.setSubject(subject);
            template.setContent(content);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            log.info("Verification code email sent successfully to: {}, expire minutes: {}, locale: {}",
                    toEmail, defaultExpireMinutes, locale);
        } catch (Exception e) {
            log.error("Failed to send verification code email to: {}, error: {}", toEmail, e.getMessage(), e);
        }
    }

    /**
     * 异步发送邮件
     */
    @Async("mailExecutor")
    @Override
    public void sendEmailAsync(EmailTemplate template) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(template.getToEmail());
            message.setSubject(template.getSubject());
            message.setText(template.getContent());

            mailSender.send(message);
            log.info("Email sent successfully to: {}, subject: {}", template.getToEmail(), template.getSubject());
        } catch (Exception e) {
            log.error("Failed to send email to: {}, subject: {}, error: {}",
                    template.getToEmail(), template.getSubject(), e.getMessage(), e);
        }
    }

    /**
     * 构建审批通过邮件内容
     */
    private String buildApprovedEmailContent(String username, String requestNumber, BigDecimal amount) {
        return StringBuilderContent.newBuilder()
                .appendLine("Dear " + username + ",")
                .appendLine("")
                .appendLine("Your refund request has been APPROVED.")
                .appendLine("")
                .appendLine("Request Details:")
                .appendLine("- Request Number: " + requestNumber)
                .appendLine("- Amount: " + amount)
                .appendLine("")
                .appendLine("We will process your refund as soon as possible. You will receive another notification once the transaction is completed.")
                .appendLine("")
                .appendLine("If you have any questions, please contact our customer service.")
                .appendLine("")
                .appendLine("Best regards,")
                .appendLine("Refund Management Team")
                .build();
    }

    /**
     * 构建审批拒绝邮件内容
     */
    private String buildRejectedEmailContent(String username, String requestNumber, String rejectReason) {
        return StringBuilderContent.newBuilder()
                .appendLine("Dear " + username + ",")
                .appendLine("")
                .appendLine("Your refund request has been REJECTED.")
                .appendLine("")
                .appendLine("Request Details:")
                .appendLine("- Request Number: " + requestNumber)
                .appendLine("- Reason: " + (rejectReason != null && !rejectReason.isEmpty() ? rejectReason : "No reason provided"))
                .appendLine("")
                .appendLine("If you believe this is a mistake or have any questions, please contact our customer service.")
                .appendLine("")
                .appendLine("Best regards,")
                .appendLine("Refund Management Team")
                .build();
    }

    /**
     * 构建交易失败邮件内容
     */
    private String buildTransactionFailedEmailContent(String username, String requestNumber, String rejectReason) {
        return StringBuilderContent.newBuilder()
                .appendLine("Dear " + username + ",")
                .appendLine("")
                .appendLine("We regret to inform you that your refund transaction has FAILED.")
                .appendLine("")
                .appendLine("Transaction Details:")
                .appendLine("- Request Number: " + requestNumber)
                .appendLine("- Reason: " + (rejectReason != null && !rejectReason.isEmpty() ? rejectReason : "No reason provided"))
                .appendLine("")
                .appendLine("You may submit a new refund request. If you have any questions, please contact our customer service.")
                .appendLine("")
                .appendLine("Best regards,")
                .appendLine("Refund Management Team")
                .build();
    }

    /**
     * 内部辅助类用于构建邮件内容
     */
    private static class StringBuilderContent {
        private final StringBuilder sb = new StringBuilder();

        private StringBuilderContent() {
        }

        public static StringBuilderContent newBuilder() {
            return new StringBuilderContent();
        }

        public StringBuilderContent appendLine(String line) {
            sb.append(line);
            sb.append("\n");
            return this;
        }

        public String build() {
            return sb.toString();
        }
    }
}
