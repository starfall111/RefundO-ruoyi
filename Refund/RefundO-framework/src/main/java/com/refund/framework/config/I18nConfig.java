package com.refund.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.refund.common.constant.Constants;

/**
 * 资源文件配置加载
 * 支持从请求头读取语言标识（Accept-Language: zh-CN 或 en-US）
 *
 * @author ruoyi
 * @author refund
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer
{
    @Bean
    public LocaleResolver localeResolver()
    {
        HeaderLocaleResolver resolver = new HeaderLocaleResolver();
        // 默认语言
        // 从请求头 Accept-Language 读取语言标识
        // zh-CN 或 zh_CN: 简体中文
        // zh-TW 或 zh_TW: 繁体中文
        // en: 英文
        return resolver;
    }
}
