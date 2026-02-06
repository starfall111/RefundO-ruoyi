package com.refund.framework.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 设置国际化资源文件路径（支持多个文件）
        messageSource.setBasename("classpath:i18n/api-messages");
        // 设置缓存时间（秒），开发环境可以设置为0以便实时更新
        messageSource.setCacheSeconds(3600);
        // 设置编码
        messageSource.setDefaultEncoding("UTF-8");
        // 设置找不到key时返回key本身
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    /**
     * APP端 LocaleResolver 配置
     * 使用 HeaderLocaleResolver 并支持法语
     */
    @Bean(name = "apiLocaleResolver")
    public LocaleResolver apiLocaleResolver() {
        HeaderLocaleResolver localeResolver = new HeaderLocaleResolver();
        return localeResolver;
    }
}
