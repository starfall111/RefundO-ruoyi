package com.refund.root.service.impl;

import com.refund.common.exception.business.BaseException;
import com.refund.common.exception.business.CodeErrorException;
import com.refund.common.exception.business.EamilSendErrorException;
import com.refund.common.exception.business.MessageKeys;
import com.refund.common.service.IMailService;
import com.refund.common.utils.VerificationCodeUtil;
import com.refund.root.service.IVerificationCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现（注册、找回密码通用）
 *
 * @author refund
 */
@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService {

    private static final Logger log = LoggerFactory.getLogger(VerificationCodeServiceImpl.class);

    @Autowired
    private IMailService mailService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Value("${verification.code.redis-prefix:email_code:}")
    private String redisPrefix;

    @Value("${verification.code.expire-minutes:3}")
    private int expireMinutes;

    private static final String RATE_LIMIT_PREFIX = "email_rate_limit:";

    @Override
    public void sendCode(String email) {
        // 检查发送频率限制（60秒内不能重复发送）
        String rateLimitKey = RATE_LIMIT_PREFIX + email;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(rateLimitKey))) {
            throw new CodeErrorException(MessageKeys.CODE_SEND_TOO_FREQUENTLY);
        }

        try {
            // 生成验证码
            String code = VerificationCodeUtil.generateCode(6);

            // 存储到Redis
            String key = redisPrefix + email;
            redisTemplate.opsForValue().set(key, code, expireMinutes, TimeUnit.MINUTES);

            // 获取当前请求的Locale并传递给邮件服务
            Locale currentLocale = LocaleContextHolder.getLocale();
            mailService.sendVerificationCodeEmailAsync(email, code, currentLocale);

            // 验证邮件发送成功（检查Redis中是否有验证码）
            String storedCode = (String) redisTemplate.opsForValue().get(key);
            if (storedCode == null) {
                throw new EamilSendErrorException(MessageKeys.EMAIL_SEND_FAILED);
            }

            // 设置频率限制标记（60秒）
            redisTemplate.opsForValue().set(rateLimitKey, "1", 60, TimeUnit.SECONDS);

            log.info("Verification code sent successfully to: {}", email);
        } catch (CodeErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to send verification code to: {}, error: {}", email, e.getMessage(), e);
            throw new EamilSendErrorException(MessageKeys.EMAIL_SEND_FAILED);
        }
    }

    @Override
    public void verifyCode(String email, String code) {
        String key = redisPrefix + email;
        String storedCode = (String) redisTemplate.opsForValue().get(key);

        if (storedCode == null) {
            throw new CodeErrorException(MessageKeys.CODE_EXPIRED);
        }

        if (!code.equals(storedCode)) {
            throw new CodeErrorException(MessageKeys.CODE_INCORRECT);
        }

        // 验证成功后删除验证码
        redisTemplate.delete(key);
        log.info("Verification code verified successfully for: {}", email);
    }
}
