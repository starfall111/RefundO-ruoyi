package com.refund.common.utils;

import com.refund.common.core.domain.model.ApiLoginUser;

/**
 * APP端安全服务工具类
 * 用于获取当前APP端登录用户信息
 */
public class ApiSecurityUtils {

    private static final ThreadLocal<ApiLoginUser> API_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前APP端登录用户
     *
     * @param apiLoginUser APP端登录用户
     */
    public static void setApiLoginUser(ApiLoginUser apiLoginUser) {
        API_USER_THREAD_LOCAL.set(apiLoginUser);
    }

    /**
     * 获取当前APP端登录用户
     *
     * @return APP端登录用户
     */
    public static ApiLoginUser getApiLoginUser() {
        return API_USER_THREAD_LOCAL.get();
    }

    /**
     * 获取当前APP端用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        ApiLoginUser apiLoginUser = getApiLoginUser();
        if (apiLoginUser == null) {
            return null;
        }
        return apiLoginUser.getUserId();
    }

    /**
     * 获取当前APP端用户名
     *
     * @return 用户名
     */
    public static String getUsername() {
        ApiLoginUser apiLoginUser = getApiLoginUser();
        if (apiLoginUser == null) {
            return null;
        }
        return apiLoginUser.getUsername();
    }

    /**
     * 获取当前APP端用户邮箱
     *
     * @return 邮箱
     */
    public static String getEmail() {
        ApiLoginUser apiLoginUser = getApiLoginUser();
        if (apiLoginUser == null) {
            return null;
        }
        return apiLoginUser.getEmail();
    }

    /**
     * 清除当前APP端登录用户
     */
    public static void clearApiLoginUser() {
        API_USER_THREAD_LOCAL.remove();
    }
}
