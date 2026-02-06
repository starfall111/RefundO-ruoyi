package com.refund.i18n;

import com.refund.framework.config.HeaderLocaleResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 国际化集成测试
 * 测试完整的国际化功能流程
 *
 * @author refund
 */
@DisplayName("国际化集成测试")
public class I18nIntegrationTest
{
    private HeaderLocaleResolver localeResolver;
    private MockHttpServletRequest request;

    @BeforeEach
    public void setUp()
    {
        localeResolver = new HeaderLocaleResolver();
        request = new MockHttpServletRequest();
        // 重置为默认语言
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
    }

    @AfterEach
    public void tearDown()
    {
        // 测试结束后重置为默认语言
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
    }

    @Test
    @DisplayName("集成测试：中文请求获取中文Locale")
    public void testChineseRequestWithChineseLocale()
    {
        // 模拟中文请求
        request.addHeader("Accept-Language", "zh-CN");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 验证解析为简体中文
        assertEquals(Locale.SIMPLIFIED_CHINESE, resolvedLocale);
        assertEquals("zh", resolvedLocale.getLanguage());
        assertEquals("CN", resolvedLocale.getCountry());
    }

    @Test
    @DisplayName("集成测试：英文请求获取英文Locale")
    public void testEnglishRequestWithEnglishLocale()
    {
        // 模拟英文请求
        request.addHeader("Accept-Language", "en-US");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 验证解析为英文
        assertEquals(Locale.ENGLISH, resolvedLocale);
        assertEquals("en", resolvedLocale.getLanguage());
    }

    @Test
    @DisplayName("集成测试：LocaleContextHolder与LocaleResolver配合")
    public void testLocaleResolverWithContextHolder()
    {
        // 模拟请求获取 Locale
        request.addHeader("Accept-Language", "zh-CN");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 设置到 LocaleContextHolder
        LocaleContextHolder.setLocale(resolvedLocale);

        // 验证 LocaleContextHolder 中的 Locale
        assertEquals(resolvedLocale, LocaleContextHolder.getLocale());
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleContextHolder.getLocale());
    }

    @Test
    @DisplayName("集成测试：多语言标识解析 - 中文优先")
    public void testMultipleLanguagesChineseFirst()
    {
        // 中文优先
        request.addHeader("Accept-Language", "zh-CN,en;q=0.9");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 应返回中文（默认优先级更高）
        assertEquals(Locale.SIMPLIFIED_CHINESE, resolvedLocale);
    }

    @Test
    @DisplayName("集成测试：多语言标识解析 - 英文优先")
    public void testMultipleLanguagesEnglishFirst()
    {
        // 英文优先
        request.addHeader("Accept-Language", "en,zh-CN;q=0.9");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 应返回英文（第一个语言优先级更高）
        assertEquals(Locale.ENGLISH, resolvedLocale);
    }

    @Test
    @DisplayName("集成测试：显式指定优先级 - 英文高优先级")
    public void testExplicitPriorityEnglishHigh()
    {
        // 英文优先级 1.0，中文优先级 0.8
        request.addHeader("Accept-Language", "en;q=1.0,zh-CN;q=0.8");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 应返回英文
        assertEquals(Locale.ENGLISH, resolvedLocale);
    }

    @Test
    @DisplayName("集成测试：显式指定优先级 - 中文高优先级")
    public void testExplicitPriorityChineseHigh()
    {
        // 中文优先级 1.0，英文优先级 0.8
        request.addHeader("Accept-Language", "zh-CN;q=1.0,en;q=0.8");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 应返回中文
        assertEquals(Locale.SIMPLIFIED_CHINESE, resolvedLocale);
    }

    @Test
    @DisplayName("集成测试：未指定Accept-Language时返回默认语言")
    public void testNoAcceptLanguageHeader()
    {
        // 不设置 Accept-Language
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 应返回默认语言（简体中文）
        assertEquals(Locale.SIMPLIFIED_CHINESE, resolvedLocale);
    }

    @Test
    @DisplayName("集成测试：空的Accept-Language返回默认语言")
    public void testEmptyAcceptLanguageHeader()
    {
        // 设置空的 Accept-Language
        request.addHeader("Accept-Language", "");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 应返回默认语言
        assertEquals(Locale.SIMPLIFIED_CHINESE, resolvedLocale);
    }

    @Test
    @DisplayName("集成测试：未知语言返回默认语言")
    public void testUnknownLanguage()
    {
        // 设置不支持的语言
        request.addHeader("Accept-Language", "fr");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 应返回默认语言
        assertEquals(Locale.SIMPLIFIED_CHINESE, resolvedLocale);
    }

    @Test
    @DisplayName("集成测试：完整的请求-响应流程")
    public void testCompleteRequestResponseFlow()
    {
        // 模拟客户端发送带有 Accept-Language 的请求
        request.addHeader("Accept-Language", "en-US");

        // 1. 服务器端解析语言
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 2. 设置当前线程的语言环境
        LocaleContextHolder.setLocale(resolvedLocale);

        // 3. 验证整个流程
        assertEquals(Locale.ENGLISH, resolvedLocale);
        assertEquals(Locale.ENGLISH, LocaleContextHolder.getLocale());
    }

    @Test
    @DisplayName("集成测试：语言切换流程")
    public void testLanguageSwitchingFlow()
    {
        // 第一次请求 - 中文
        request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "zh-CN");
        Locale locale1 = localeResolver.resolveLocale(request);
        LocaleContextHolder.setLocale(locale1);
        assertEquals(Locale.SIMPLIFIED_CHINESE, locale1);

        // 第二次请求 - 英文
        request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "en");
        Locale locale2 = localeResolver.resolveLocale(request);
        LocaleContextHolder.setLocale(locale2);
        assertEquals(Locale.ENGLISH, locale2);

        // 第三次请求 - 切回中文
        request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "zh-CN");
        Locale locale3 = localeResolver.resolveLocale(request);
        LocaleContextHolder.setLocale(locale3);
        assertEquals(Locale.SIMPLIFIED_CHINESE, locale3);
    }

    @Test
    @DisplayName("集成测试：测试各种英文变体")
    public void testEnglishVariants()
    {
        // 测试各种英文变体都应解析为英文
        String[] variants = {"en", "en-US", "en-GB", "en-CA", "en-AU"};

        for (String variant : variants)
        {
            request = new MockHttpServletRequest();
            request.addHeader("Accept-Language", variant);
            Locale resolvedLocale = localeResolver.resolveLocale(request);
            assertEquals(Locale.ENGLISH, resolvedLocale,
                "Accept-Language '" + variant + "' 应解析为英文");
        }
    }

    @Test
    @DisplayName("集成测试：测试各种中文变体")
    public void testChineseVariants()
    {
        // 测试各种中文变体
        request.addHeader("Accept-Language", "zh-CN");
        assertEquals(Locale.SIMPLIFIED_CHINESE, localeResolver.resolveLocale(request));

        request.addHeader("Accept-Language", "zh_CN");
        assertEquals(Locale.SIMPLIFIED_CHINESE, localeResolver.resolveLocale(request));
    }

    @Test
    @DisplayName("集成测试：混合大小写的Accept-Language")
    public void testMixedCaseAcceptLanguage()
    {
        // 测试大小写不敏感 - 每个断言使用新的 request
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        request1.addHeader("Accept-Language", "ZH-CN");
        assertEquals(Locale.SIMPLIFIED_CHINESE, localeResolver.resolveLocale(request1));

        MockHttpServletRequest request2 = new MockHttpServletRequest();
        request2.addHeader("Accept-Language", "EN");
        assertEquals(Locale.ENGLISH, localeResolver.resolveLocale(request2));

        MockHttpServletRequest request3 = new MockHttpServletRequest();
        request3.addHeader("Accept-Language", "Zh-Cn");
        assertEquals(Locale.SIMPLIFIED_CHINESE, localeResolver.resolveLocale(request3));
    }

    @Test
    @DisplayName("集成测试：HTTP标准Accept-Language格式")
    public void testHttpStandardAcceptLanguage()
    {
        // 符合 HTTP 标准的 Accept-Language 格式
        // 优先级：en-US (1.0) > en (0.8) > zh-CN (0.5)
        request.addHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.5");
        Locale resolvedLocale = localeResolver.resolveLocale(request);

        // 应返回英文（最高优先级）
        assertEquals(Locale.ENGLISH, resolvedLocale);
    }
}
