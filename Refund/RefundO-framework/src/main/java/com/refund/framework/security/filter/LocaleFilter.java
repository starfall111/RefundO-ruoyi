package com.refund.framework.security.filter;

import com.refund.common.utils.ApiMessageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

/**
 * APP端语言环境过滤器
 * 从 Accept-Language 请求头提取语言并设置到LocaleContextHolder
 * 必须在 ApiJwtAuthenticationFilter 之前执行
 */
@Component
public class LocaleFilter extends OncePerRequestFilter implements Ordered {

    @Override
    public int getOrder() {
        return 1; // 最高优先级，最先执行
    }

    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

    /**
     * 支持的语言列表
     */
    private static final Locale[] SUPPORTED_LOCALES = {
            Locale.SIMPLIFIED_CHINESE,  // zh-CN
            Locale.US,                   // en-US
            Locale.FRANCE                // fr-FR
    };

    /**
     * 默认语言: zh-CN
     */
    private static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 从 Accept-Language 请求头获取语言
            String acceptLanguage = request.getHeader(ACCEPT_LANGUAGE_HEADER);

            // 解析并设置语言环境
            Locale locale = fromString(acceptLanguage);
            org.springframework.context.i18n.LocaleContextHolder.setLocale(locale);

            // 继续过滤器链
            filterChain.doFilter(request, response);
        } finally {
            // 请求处理完毕后清除 LocaleContext，避免内存泄漏
            org.springframework.context.i18n.LocaleContextHolder.resetLocaleContext();
        }
    }

    /**
     * 根据语言字符串获取 Locale 对象
     *
     * @param language 语言字符串 (如: zh-CN, en-US, fr-FR)
     * @return Locale 对象
     */
    private Locale fromString(String language) {
        if (language == null || language.isEmpty()) {
            return DEFAULT_LOCALE;
        }

        String[] parts = language.split("[-_]");
        if (parts.length == 1) {
            Locale locale = new Locale(parts[0]);
            return isSupported(locale) ? locale : DEFAULT_LOCALE;
        } else if (parts.length >= 2) {
            Locale locale = new Locale(parts[0], parts[1]);
            return isSupported(locale) ? locale : DEFAULT_LOCALE;
        }

        return DEFAULT_LOCALE;
    }

    /**
     * 检查语言环境是否被支持
     *
     * @param locale 语言环境
     * @return 是否支持
     */
    private boolean isSupported(Locale locale) {
        if (locale == null) {
            return false;
        }
        for (Locale supported : SUPPORTED_LOCALES) {
            if (supported.getLanguage().equals(locale.getLanguage())) {
                return true;
            }
        }
        return false;
    }
}
