package com.refund.framework.security.filter;

import com.refund.common.core.domain.Result;
import com.refund.common.enums.LimitType;
import com.refund.common.exception.business.MessageKeys;
import com.refund.common.utils.ratelimit.RateLimitUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.refund.framework.web.exception.GlobalExceptionHandler.log;

/**
 * APP端 IP限流过滤器
 * 实现IP级别的全局限流和登录接口特殊限流
 * 在 LocaleFilter (Order=1) 之后，ApiJwtAuthenticationFilter 之前执行
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter implements Ordered {

    @Autowired
    private RateLimitUtil rateLimitUtil;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 全局IP限流：每60秒100次请求
     */
    private static final int GLOBAL_IP_TIME = 60;
    private static final int GLOBAL_IP_COUNT = 100;

    /**
     * 登录接口限流配置
     */
    private static final int LOGIN_IP_TIME = 300; // 5分钟
    private static final int LOGIN_IP_COUNT = 10;
    private static final int LOGIN_FAIL_THRESHOLD = 5; // 失败5次锁定
    private static final int LOGIN_LOCK_DURATION_MINUTES = 30; // 锁定30分钟

    @Override
    public int getOrder() {
        return 2; // 在 LocaleFilter 之后
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 只处理API路径
        if (!requestURI.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String ip = getClientIP(request);

        try {
            // 1. 检查IP是否被封禁
            if (rateLimitUtil.isIPBlocked(ip)) {
                String message = getI18nMessage(MessageKeys.RATE_LIMIT_BLOCKED);
                sendErrorResponse(response, HttpStatus.LOCKED.value(), message);
                return;
            }

            // 2. 登录接口特殊限流
            if ("/api/user/login".equals(requestURI)) {
                boolean shouldBlock = handleLoginRateLimit(request, response, ip);
                if (shouldBlock) {
                    return; // 被限流拦截，不继续执行
                }
                // 如果通过限流检查，继续执行
                filterChain.doFilter(request, response);
                return;
            }

            // 3. 验证码接口限流（除已有的邮件发送频率限制外，增加IP限制）
            if (requestURI.startsWith("/api/verification-code")) {
                String key = rateLimitUtil.buildRateLimitKey(LimitType.IP, ip, requestURI, "code");
                if (rateLimitUtil.isRateLimited(key, 60, 5)) {
                    sendRateLimitResponse(response, MessageKeys.RATE_LIMIT_IP);
                    return;
                }
            }

            // 4. 全局IP限流（对所有请求生效）
            String globalKey = rateLimitUtil.buildRateLimitKey(LimitType.IP, ip, null, "global");
            if (rateLimitUtil.isRateLimited(globalKey, GLOBAL_IP_TIME, GLOBAL_IP_COUNT)) {
                sendRateLimitResponse(response, MessageKeys.RATE_LIMIT_IP);
                return;
            }

            // 通过限流检查，继续执行
            filterChain.doFilter(request, response);

        } finally {
            // 清理工作（如果需要）
        }
    }

    /**
     * 处理登录接口限流
     * 检查IP和账号是否被锁定
     *
     * @return true 表示应该阻止请求，false 表示应该继续处理
     */
    private boolean handleLoginRateLimit(HttpServletRequest request, HttpServletResponse response, String ip) throws IOException {
        String account = request.getParameter("username");

        // 检查IP是否被锁定
        if (rateLimitUtil.isLocked(ip, true)) {
            String message = getI18nMessage(MessageKeys.LOGIN_IP_LOCKED, LOGIN_LOCK_DURATION_MINUTES);
            sendErrorResponse(response, HttpStatus.LOCKED.value(), message);
            return true; // 阻止请求
        }

        // 检查账号是否被锁定
        if (account != null && rateLimitUtil.isLocked(account, false)) {
            String message = getI18nMessage(MessageKeys.LOGIN_ACCOUNT_LOCKED, LOGIN_LOCK_DURATION_MINUTES);
            sendErrorResponse(response, HttpStatus.LOCKED.value(), message);
            return true; // 阻止请求
        }

        // 检查IP登录频率
        String ipKey = rateLimitUtil.buildRateLimitKey(LimitType.IP, ip, "/api/user/login", "login");
        if (rateLimitUtil.isRateLimited(ipKey, LOGIN_IP_TIME, LOGIN_IP_COUNT)) {
            sendRateLimitResponse(response, MessageKeys.RATE_LIMIT_IP);
            return true; // 阻止请求
        }

        // 通过所有检查
        return false; // 不阻止请求
    }

    /**
     * 获取客户端真实IP
     */
    private String getClientIP(HttpServletRequest request) {
        try {
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
            } else {
                // 只有在没有 X-Forwarded-For 时才检查 X-Real-IP（某些反向代理使用）
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
     * 发送限流响应
     */
    private void sendRateLimitResponse(HttpServletResponse response, String messageKey) throws IOException {
        String message = getI18nMessage(messageKey);
        sendErrorResponse(response, HttpStatus.TOO_MANY_REQUESTS.value(), message);
    }

    /**
     * 发送错误响应
     */
    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        Result<?> result = Result.error(message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    /**
     * 获取国际化消息
     */
    private String getI18nMessage(String messageKey, Object... args) {
        try {
            Locale locale = org.springframework.context.i18n.LocaleContextHolder.getLocale();
            if (args != null && args.length > 0) {
                return messageSource.getMessage(messageKey, args, locale);
            } else {
                return messageSource.getMessage(messageKey, null, locale);
            }
        } catch (Exception e) {
            log.warn("消息键未找到: {}", messageKey);
            return messageKey;
        }
    }
}
