package com.refund.framework.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

/**
 * APP端国际化配置
 */
@Configuration
public class ApiI18nConfig implements WebMvcConfigurer {

    /**
     * APP端 MessageSource 配置
     * 使用单独的 api-messages 资源文件
     */
    @Bean(name = "apiMessageSource")
    public MessageSource apiMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 设置国际化资源文件路径（不使用 classpath: 前缀）
        messageSource.setBasename("i18n.api-messages");
        // 设置编码
        messageSource.setDefaultEncoding("UTF-8");
        // 设置找不到key时返回key本身
        messageSource.setUseCodeAsDefaultMessage(true);
        // 设置默认语言为英文
        messageSource.setDefaultLocale(Locale.ENGLISH);
        // 禁用回退到系统默认 Locale
        messageSource.setFallbackToSystemLocale(false);

        // 调试：打印加载的资源文件
        System.out.println("===== ApiI18nConfig: apiMessageSource initialized");
        System.out.println("===== ApiI18nConfig: basename=i18n.api-messages");
        System.out.println("===== ApiI18nConfig: Testing fr locale...");
        try {
            String testMsg = messageSource.getMessage("error.account.not.found", null, Locale.FRENCH);
            System.out.println("===== ApiI18nConfig: fr test result = " + testMsg);
        } catch (Exception e) {
            System.out.println("===== ApiI18nConfig: fr test failed: " + e.getMessage());
        }

        return messageSource;
    }

    /**
     * APP端 LocaleResolver 配置
     * 禁用：由于 LocaleFilter 已经正确设置 Locale，避免重复解析导致冲突
     */
    // @Bean(name = "apiLocaleResolver")
    // @Primary
    // public LocaleResolver apiLocaleResolver() {
    //     HeaderLocaleResolver localeResolver = new HeaderLocaleResolver();
    //     return localeResolver;
    // }
}
