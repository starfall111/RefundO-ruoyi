package com.refund.i18n;

import com.refund.common.constant.Constants;
import com.refund.framework.config.HeaderLocaleResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * HeaderLocaleResolver 单元测试
 * 测试从请求头读取语言标识并解析为对应Locale的功能
 *
 * @author refund
 */
@DisplayName("HeaderLocaleResolver 测试")
public class HeaderLocaleResolverTest
{
    private HeaderLocaleResolver resolver;
    private MockHttpServletRequest request;

    @BeforeEach
    public void setUp()
    {
        resolver = new HeaderLocaleResolver();
        request = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("测试中文语言标识 - zh-CN")
    public void testResolveLocaleWithZhCN()
    {
        // 设置请求头为简体中文
        request.addHeader("Accept-Language", "zh-CN");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.SIMPLIFIED_CHINESE, locale, "应解析为简体中文");
    }

    @Test
    @DisplayName("测试中文语言标识 - zh_CN")
    public void testResolveLocaleWithZhCNUnderscore()
    {
        // 设置请求头为简体中文（下划线格式）
        request.addHeader("Accept-Language", "zh_CN");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.SIMPLIFIED_CHINESE, locale, "应解析为简体中文");
    }

    @Test
    @DisplayName("测试英文语言标识 - en")
    public void testResolveLocaleWithEn()
    {
        // 设置请求头为英文
        request.addHeader("Accept-Language", "en");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.ENGLISH, locale, "应解析为英文");
    }

    @Test
    @DisplayName("测试英文语言标识 - en")
    public void testResolveLocaleWithEnUS()
    {
        // 设置请求头为美式英文
        request.addHeader("Accept-Language", "en");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.ENGLISH, locale, "应解析为英文");
    }

    @Test
    @DisplayName("测试英文语言标识 - en")
    public void testResolveLocaleWithEnGB()
    {
        // 设置请求头为英式英文
        request.addHeader("Accept-Language", "en");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.ENGLISH, locale, "应解析为英文");
    }

    @Test
    @DisplayName("测试空Accept-Language头")
    public void testResolveLocaleWithEmptyHeader()
    {
        // 不设置Accept-Language头
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Constants.DEFAULT_LOCALE, locale, "应返回默认语言（简体中文）");
    }

    @Test
    @DisplayName("测试null Accept-Language头")
    public void testResolveLocaleWithNullHeader()
    {
        // 设置Accept-Language为空字符串
        request.addHeader("Accept-Language", "");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Constants.DEFAULT_LOCALE, locale, "应返回默认语言（简体中文）");
    }

    @Test
    @DisplayName("测试未知语言标识")
    public void testResolveLocaleWithUnknownLanguage()
    {
        // 设置未知语言
        request.addHeader("Accept-Language", "fr");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Constants.DEFAULT_LOCALE, locale, "应返回默认语言（简体中文）");
    }

    @Test
    @DisplayName("测试不区分大小写")
    public void testResolveLocaleCaseInsensitive()
    {
        // 设置大写的Accept-Language
        request.addHeader("Accept-Language", "ZH-CN");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.SIMPLIFIED_CHINESE, locale, "应解析为简体中文（不区分大小写）");
    }

    @Test
    @DisplayName("测试包含多个语言标识")
    public void testResolveLocaleWithMultipleLanguages()
    {
        // 设置多个语言，应使用第一个匹配的语言
        request.addHeader("Accept-Language", "zh-CN,en;q=0.9");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.SIMPLIFIED_CHINESE, locale, "应解析为简体中文");
    }

    @Test
    @DisplayName("测试英文优先的多个语言标识")
    public void testResolveLocaleWithMultipleLanguagesEnFirst()
    {
        // 设置多个语言，英文优先
        request.addHeader("Accept-Language", "en,zh-CN;q=0.9");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.ENGLISH, locale, "应解析为英文");
    }
}
