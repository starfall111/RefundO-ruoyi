package com.refund.common.enums;

/**
 * 限流类型
 *
 * @author ruoyi
 */
public enum LimitType
{
    /**
     * 默认策略全局限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP,

    /**
     * 按用户ID限流
     * 适用于已登录用户的接口
     */
    USER,

    /**
     * 全局限流
     * 适用于保护整个系统
     */
    GLOBAL
}
