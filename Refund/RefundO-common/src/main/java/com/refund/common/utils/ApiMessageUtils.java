package com.refund.common.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * APP端国际化消息工具类
 * 用于获取国际化消息
 */
@Component
public class ApiMessageUtils {

    private static MessageSource messageSource;

    public ApiMessageUtils(@Qualifier("apiMessageSource") MessageSource messageSource) {
        ApiMessageUtils.messageSource = messageSource;
    }

    /**
     * 获取国际化消息
     *
     * @param code 消息键
     * @return 消息内容
     */
    public static String getMessage(String code) {
        return getMessage(code, (Object[]) null);
    }

    /**
     * 获取国际化消息（带参数）
     *
     * @param code 消息键
     * @param args 消息参数
     * @return 消息内容
     */
    public static String getMessage(String code, Object[] args) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            return code;
        }
    }

    /**
     * 获取国际化消息（带默认值）
     *
     * @param code          消息键
     * @param defaultMessage 默认消息
     * @return 消息内容
     */
    public static String getMessage(String code, String defaultMessage) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(code, null, defaultMessage, locale);
        } catch (Exception e) {
            return defaultMessage;
        }
    }

    /**
     * 获取国际化消息（指定Locale）
     *
     * @param code  消息键
     * @param locale 国际化Locale
     * @return 消息内容
     */
    public static String getMessage(String code, Locale locale) {
        return getMessage(code, locale, (Object[]) null);
    }

    /**
     * 获取国际化消息（指定Locale，带参数）
     *
     * @param code  消息键
     * @param locale 国际化Locale
     * @param args   消息参数
     * @return 消息内容
     */
    public static String getMessage(String code, Locale locale, Object[] args) {
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            return code;
        }
    }
}
