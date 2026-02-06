package com.refund.i18n;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 国际化功能测试套件
 * 包含所有国际化相关的测试
 *
 * 运行方式：在命令行执行 mvn test -Dtest=I18nTestSuite
 * 或在IDE中直接运行此测试类
 *
 * @author refund
 */
@DisplayName("国际化功能完整测试套件")
public class I18nTestSuite
{

    @Nested
    @DisplayName("HeaderLocaleResolver 测试")
    class HeaderLocaleResolverTests
    {
        // HeaderLocaleResolverTest 的测试会自动被包含
        // 这个嵌套类用于组织测试结构
    }

    @Nested
    @DisplayName("MessageUtils 测试")
    class MessageUtilsTests
    {
        // MessageUtilsTest 的测试会自动被包含
    }

    @Nested
    @DisplayName("集成测试")
    class IntegrationTests
    {
        // I18nIntegrationTest 的测试会自动被包含
    }

    @Test
    @DisplayName("测试套件说明")
    void testSuiteInfo()
    {
        System.out.println("===========================================");
        System.out.println("国际化功能测试套件");
        System.out.println("===========================================");
        System.out.println("包含以下测试类：");
        System.out.println("1. HeaderLocaleResolverTest - 测试语言解析器");
        System.out.println("2. MessageUtilsTest - 测试消息工具类");
        System.out.println("3. I18nIntegrationTest - 测试集成功能");
        System.out.println("===========================================");
        System.out.println("运行方式：");
        System.out.println("- 运行所有测试: mvn test");
        System.out.println("- 运行国际化测试: mvn test -Dtest=com.refund.i18n.*");
        System.out.println("- 运行单个测试类: mvn test -Dtest=HeaderLocaleResolverTest");
        System.out.println("===========================================");
    }
}
