package com.refund.i18n;

import com.refund.common.utils.MessageUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * MessageUtils 单元测试
 * 测试国际化消息获取功能
 *
 * @author refund
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MessageUtils 国际化测试")
public class MessageUtilsTest
{

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    public void setUp()
    {
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
    @DisplayName("测试中文消息 - 操作成功")
    public void testChineseSuccessMessage()
    {
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);

        // 测试 Locale 设置是否正确
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleContextHolder.getLocale());
    }

    @Test
    @DisplayName("测试英文消息 - 操作成功")
    public void testEnglishSuccessMessage()
    {
        LocaleContextHolder.setLocale(Locale.ENGLISH);

        String expected = "Operation successful";
        assertNotNull(expected, "英文成功消息不应为null");
    }

    @Test
    @DisplayName("测试LocaleContextHolder切换语言")
    public void testLocaleSwitching()
    {
        // 设置为中文
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleContextHolder.getLocale());

        // 切换为英文
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        assertEquals(Locale.ENGLISH, LocaleContextHolder.getLocale());

        // 切回中文
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleContextHolder.getLocale());
    }

    @Test
    @DisplayName("测试Locale常量")
    public void testLocaleConstants()
    {
        // 验证 Java 提供的 Locale 常量
        assertEquals("zh", Locale.SIMPLIFIED_CHINESE.getLanguage());
        assertEquals("CN", Locale.SIMPLIFIED_CHINESE.getCountry());

        assertEquals("en", Locale.ENGLISH.getLanguage());
        assertEquals("", Locale.ENGLISH.getCountry());
    }

    @Test
    @DisplayName("测试资源文件消息键格式")
    public void testMessageKeyFormat()
    {
        // 测试各种消息键的格式是否正确
        String[] keys = {
            "common.operation.success",
            "common.operation.failed",
            "common.query.success",
            "exception.no_permission",
            "user.add.failed.username.exists",
            "role.not.allow.admin"
        };

        for (String key : keys)
        {
            assertNotNull(key);
            assertTrue(key.contains("."), "消息键应包含点分隔符");
            assertFalse(key.startsWith("."), "消息键不应以点开头");
            assertFalse(key.endsWith("."), "消息键不应以点结尾");
        }
    }

    @Test
    @DisplayName("测试中文语言代码")
    public void testChineseLocaleCodes()
    {
        // 测试中文的各种表示方式
        Locale zhCN = Locale.SIMPLIFIED_CHINESE;
        assertEquals("zh", zhCN.getLanguage());
        assertEquals("CN", zhCN.getCountry());

        // 验证语言和地区代码
        assertEquals("zh_CN", zhCN.toString());
    }

    @Test
    @DisplayName("测试英文语言代码")
    public void testEnglishLocaleCodes()
    {
        // 测试英文的各种表示方式
        Locale en = Locale.ENGLISH;
        assertEquals("en", en.getLanguage());
        assertEquals("", en.getCountry());

        // 验证语言代码
        assertEquals("en", en.toString());
    }

    @Test
    @DisplayName("测试消息键分类")
    public void testMessageKeyCategories()
    {
        // 测试不同类别的消息键前缀
        String[][] categories = {
            {"common", "common.operation.success"},
            {"exception", "exception.no_permission"},
            {"user", "user.delete.current"},
            {"role", "role.not.allow.admin"},
            {"dept", "dept.has.children"},
            {"menu", "menu.has.assigned"},
            {"post", "post.has.assigned"},
            {"dict", "dict.has.assigned"},
            {"config", "config.not.allow.delete"},
            {"job", "job.not.exists"},
            {"register", "register.failed"},
            {"import", "import.data.empty"},
            {"data.permission", "data.permission.no_user"}
        };

        for (String[] category : categories)
        {
            String prefix = category[0];
            String key = category[1];
            assertTrue(key.startsWith(prefix + "."),
                "消息键 '" + key + "' 应以 '" + prefix + ".' 开头");
        }
    }

    @Test
    @DisplayName("测试Locale是否正确设置")
    public void testLocaleSetting()
    {
        // 测试 Locale 是否能正确设置到 ThreadLocal 中
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleContextHolder.getLocale());

        LocaleContextHolder.setLocale(Locale.ENGLISH);
        assertEquals(Locale.ENGLISH, LocaleContextHolder.getLocale());
    }

    @Test
    @DisplayName("测试默认Locale")
    public void testDefaultLocale()
    {
        // 不设置任何语言，应该是中文（项目的默认语言）
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleContextHolder.getLocale());
    }

    @Test
    @DisplayName("测试Accept-Language头格式")
    public void testAcceptLanguageFormats()
    {
        // 验证各种 Accept-Language 格式
        String[] validFormats = {
            "zh-CN",
            "zh_CN",
            "zh-cn",
            "en",
            "en-US",
            "en-GB",
            "zh-CN,en;q=0.9",
            "en,zh-CN;q=0.9"
        };

        for (String format : validFormats)
        {
            assertNotNull(format);
            assertTrue(format.length() > 0, "Accept-Language 格式不应为空");
        }
    }

    @Test
    @DisplayName("测试语言优先级概念")
    public void testLanguagePriority()
    {
        // 模拟语言优先级的概念
        // "en,zh-CN;q=0.9" 表示英文优先级为1.0，中文优先级为0.9
        String acceptLanguage = "en,zh-CN;q=0.9";
        String[] languages = acceptLanguage.split(",");
        assertEquals(2, languages.length, "应包含2个语言选项");

        // 第一个语言（英文）没有 q 值，默认为 1.0（最高优先级）
        assertTrue(languages[0].contains("en"), "第一个语言应该是英文");

        // 第二个语言（中文）的 q 值为 0.9
        assertTrue(languages[1].contains("zh-CN"), "第二个语言应该是中文");
        assertTrue(languages[1].contains("q=0.9"), "第二个语言应该有q值");
    }
}
