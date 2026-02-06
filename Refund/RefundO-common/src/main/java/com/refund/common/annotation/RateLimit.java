package com.refund.common.annotation;

import com.refund.common.enums.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流注解
 * 用于标记需要进行限流的接口方法
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 时间窗口（秒）
     * 默认60秒
     */
    int time() default 60;

    /**
     * 允许的最大请求数
     * 默认10次
     */
    int count() default 10;

    /**
     * 限流类型
     * 默认按IP限流
     */
    LimitType limitType() default LimitType.IP;

    /**
     * 限流key前缀（可选）
     * 用于区分不同的接口或业务场景
     */
    String key() default "";
}
