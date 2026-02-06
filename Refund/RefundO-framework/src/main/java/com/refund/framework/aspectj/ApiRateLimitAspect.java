package com.refund.framework.aspectj;

import com.refund.common.annotation.RateLimit;
import com.refund.common.enums.LimitType;
import com.refund.common.exception.business.MessageKeys;
import com.refund.common.exception.business.RateLimitException;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.common.utils.ratelimit.RateLimitUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

import static com.refund.framework.web.exception.GlobalExceptionHandler.log;

/**
 * APP端限流切面
 * 拦截带@RateLimit注解的方法，执行限流逻辑
 */
@Aspect
@Component
public class ApiRateLimitAspect {

    @Autowired
    private RateLimitUtil rateLimitUtil;

    /**
     * 环绕通知，拦截@RateLimit注解的方法
     */
    @Around("@annotation(com.refund.common.annotation.RateLimit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 获取方法签名和注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        if (rateLimit == null) {
            return joinPoint.proceed();
        }

        // 2. 获取限流参数
        int time = rateLimit.time();
        int count = rateLimit.count();
        LimitType limitType = rateLimit.limitType();
        String customKey = rateLimit.key();

        // 3. 构建限流标识符
        String identifier = buildIdentifier(limitType);

        // 4. 获取请求URI
        String requestURI = getRequestURI();

        // 5. 构建完整的限流key
        String key = rateLimitUtil.buildRateLimitKey(limitType, identifier, requestURI, customKey);

        // 6. 执行限流检查
        if (rateLimitUtil.isRateLimited(key, time, count)) {
            String messageKey = getMessageKey(limitType);
            log.warn("限流触发: key={}, limitType={}, time={}s, count={}",
                    key, limitType, time, count);
            throw new RateLimitException(messageKey);
        }

        // 7. 通过限流检查，继续执行方法
        return joinPoint.proceed();
    }

    /**
     * 根据限流类型构建标识符
     */
    private String buildIdentifier(LimitType limitType) {
        switch (limitType) {
            case IP:
                return getClientIP();
            case USER:
                Long userId = ApiSecurityUtils.getUserId();
                if (userId == null) {
                    // 如果用户未登录，降级为IP限流
                    return getClientIP();
                }
                return String.valueOf(userId);
            case GLOBAL:
                return "global";
            default:
                return "unknown";
        }
    }

    /**
     * 获取客户端IP
     */
    private String getClientIP() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return "unknown";
            }

            HttpServletRequest request = attributes.getRequest();
            String ip = request.getRemoteAddr();

            // 处理IPv6本地地址
            if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
                ip = "127.0.0.1";
            }

            // 检查反向代理传递的IP（X-Forwarded-For）
            String xForwardedFor = request.getHeader("X-Forwarded-For");
            if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
                // X-Forwarded-For格式: clientIP, proxy1IP, proxy2IP
                // 取第一个IP作为真实客户端IP
                int index = xForwardedFor.indexOf(',');
                if (index != -1) {
                    ip = xForwardedFor.substring(0, index).trim();
                } else {
                    ip = xForwardedFor.trim();
                }
            }

            // 检查X-Real-IP（某些反向代理使用）
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                String xRealIp = request.getHeader("X-Real-IP");
                if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
                    ip = xRealIp.trim();
                }
            }

            return ip != null ? ip : "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }

    /**
     * 获取请求URI
     */
    private String getRequestURI() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return "";
        }
        return attributes.getRequest().getRequestURI();
    }

    /**
     * 根据限流类型返回对应的错误消息键
     */
    private String getMessageKey(LimitType limitType) {
        switch (limitType) {
            case IP:
                return MessageKeys.RATE_LIMIT_IP;
            case USER:
                return MessageKeys.RATE_LIMIT_USER;
            case GLOBAL:
                return MessageKeys.RATE_LIMIT_GLOBAL;
            default:
                return MessageKeys.RATE_LIMIT_EXCEEDED;
        }
    }
}
