package com.refund.framework.config;

import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;
import com.refund.common.constant.Constants;

/**
 * 自定义LocaleResolver，从请求头读取语言标识
 * 支持解析 Accept-Language 头的优先级（q 值）
 *
 * @author refund
 */
public class HeaderLocaleResolver implements LocaleResolver
{
    /**
     * 请求头中语言标识的参数名，默认为 Accept-Language
     */
    private String languageHeaderName = "Accept-Language";

    /**
     * 设置请求头中语言标识的参数名
     *
     * @param languageHeaderName 请求头参数名
     */
    public void setLanguageHeaderName(String languageHeaderName)
    {
        this.languageHeaderName = languageHeaderName;
    }

    /**
     * 从请求头中解析语言标识
     * 按照 Accept-Language 的优先级解析，支持 q 值
     *
     * @param request HTTP请求
     * @return 解析后的Locale对象
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request)
    {
        String language = request.getHeader(languageHeaderName);

        if (language == null || language.isEmpty())
        {
            return Constants.DEFAULT_LOCALE;
        }

        // 检查是否为APP端请求，如果是则支持法语
        String requestURI = request.getRequestURI();
        boolean isApiRequest = requestURI != null && requestURI.startsWith("/api/");

        // 解析 Accept-Language 头，按优先级排序
        return parseAcceptLanguage(language, isApiRequest);
    }

    /**
     * 解析 Accept-Language 头
     * 按照 HTTP 规范解析，支持优先级 q 值
     *
     * @param acceptLanguage Accept-Language 头的值
     * @param supportFrench 是否支持法语（APP端）
     * @return 解析后的Locale对象
     */
    private Locale parseAcceptLanguage(String acceptLanguage, boolean supportFrench)
    {
        // 按逗号分割各个语言项
        String[] languageTokens = acceptLanguage.split(",");
        String bestLanguage = null;
        double bestQuality = 0.0;

        for (String languageToken : languageTokens)
        {
            String token = languageToken.trim();
            String language = token;
            double quality = 1.0;

            // 解析 q 值（优先级）
            int semiColonIndex = token.indexOf(";q=");
            if (semiColonIndex >= 0)
            {
                language = token.substring(0, semiColonIndex).trim();
                try
                {
                    quality = Double.parseDouble(token.substring(semiColonIndex + 3).trim());
                }
                catch (NumberFormatException e)
                {
                    quality = 1.0;
                }
            }

            // 检查是否支持该语言
            String languageLower = language.toLowerCase();
            if (isSupportedLanguage(languageLower, supportFrench))
            {
                if (quality > bestQuality)
                {
                    bestQuality = quality;
                    bestLanguage = languageLower;
                }
            }
        }

        // 根据最佳匹配返回对应的 Locale
        if (bestLanguage != null)
        {
            if (bestLanguage.contains("zh-cn") || bestLanguage.contains("zh_cn"))
            {
                return Locale.SIMPLIFIED_CHINESE;
            }
            else if (bestLanguage.contains("zh-tw") || bestLanguage.contains("zh_tw"))
            {
                return Locale.TRADITIONAL_CHINESE;
            }
            else if (bestLanguage.contains("en"))
            {
                return Locale.ENGLISH;
            }
            else if (supportFrench && bestLanguage.contains("fr"))
            {
                return Locale.FRANCE;
            }
        }

        // 默认返回中文
        return Constants.DEFAULT_LOCALE;
    }

    /**
     * 检查是否支持该语言
     *
     * @param language 语言标识（应该是小写的）
     * @param supportFrench 是否支持法语
     * @return 是否支持
     */
    private boolean isSupportedLanguage(String language, boolean supportFrench)
    {
        boolean isSupported = language.contains("zh-cn") || language.contains("zh_cn") ||
                language.contains("zh-tw") || language.contains("zh_tw") ||
                language.contains("en");

        if (supportFrench) {
            isSupported = isSupported || language.contains("fr");
        }

        return isSupported;
    }

    /**
     * 设置Locale（此方法在HeaderLocaleResolver中不需要实现，因为语言由客户端请求头决定）
     *
     * @param request HTTP请求
     * @param response HTTP响应
     * @param locale 要设置的Locale
     */
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale)
    {
        // 从请求头读取语言，不需要设置
    }
}
