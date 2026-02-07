package com.refund.common.exception.business;

/**
 * APP端国际化消息键常量类
 * 所有消息键在此定义，避免硬编码字符串
 */
public final class MessageKeys {

    private MessageKeys() {
        // 防止实例化
    }

    // ==================== 账号相关 (1000-1999) ====================
    public static final String ACCOUNT_NOT_FOUND = "error.account.not.found";
    public static final String ACCOUNT_REPETITION = "error.account.repetition";
    public static final String ACCOUNT_REPETITION_USERNAME = "error.account.repetition.username";
    public static final String ACCOUNT_REPETITION_EMAIL = "error.account.repetition.email";
    public static final String ACCOUNT_STATUS_DISABLED = "error.account.status.disabled";
    public static final String ACCOUNT_STATUS_FROZEN = "error.account.status.frozen";
    public static final String LOGIN_IP_LOCKED = "error.login.ip.locked";
    public static final String LOGIN_ACCOUNT_LOCKED = "error.login.account.locked";
    // 密码错误，剩余N次机会
    public static final String LOGIN_PASSWORD_REMAINING_ATTEMPTS = "error.login.password.remaining.attempts";
    // IP锁定，将于 HH:mm 解锁
    public static final String LOGIN_IP_LOCKED_UNTIL = "error.login.ip.locked.until";
    // 账号锁定，将于 HH:mm 解锁
    public static final String LOGIN_ACCOUNT_LOCKED_UNTIL = "error.login.account.locked.until";

    // ==================== 密码相关 (2000-2999) ====================
    public static final String PASSWORD_INCORRECT = "error.password.incorrect";
    public static final String PASSWORD_UPDATE_FAILED = "error.password.update.failed";

    // ==================== 验证码相关 (3000-3999) ====================
    public static final String CODE_EXPIRED = "error.code.expired";
    public static final String CODE_INCORRECT = "error.code.incorrect";
    public static final String CODE_SEND_TOO_FREQUENTLY = "error.code.send.too.frequently";

    // ==================== 产品相关 (4000-4999) ====================
    public static final String PRODUCT_INVALID = "error.product.invalid";
    public static final String PRODUCT_REPETITION_SCAN = "error.product.repetition.scan";
    public static final String PRODUCT_NOT_EXIST = "error.product.not.exist";

    // ==================== 退款相关 (5000-5999) ====================
    public static final String REFUND_SCAN_NOT_EXIST = "error.refund.scan.not.exist";
    public static final String REFUND_SCAN_ALREADY_APPLIED = "error.refund.scan.already.applied";
    public static final String REFUND_SCAN_NOT_ELIGIBLE = "error.refund.scan.not.eligible";
    public static final String REFUND_AMOUNT_INSUFFICIENT = "error.refund.amount.insufficient";
    public static final String REFUND_BALANCE_INSUFFICIENT = "error.refund.balance.insufficient";

    // ==================== 余额相关 (5500-5599) ====================
    public static final String BALANCE_INSUFFICIENT = "error.balance.insufficient";

    // ==================== 服务器相关 (6000-6999) ====================
    public static final String SERVER_INTERNAL = "error.server.internal";
    public static final String SERVER_SIGNUP_FAILED = "error.server.signup.failed";
    public static final String SERVER_UPDATE_FAILED = "error.server.update.failed";

    // ==================== 邮件相关 (7000-7999) ====================
    public static final String EMAIL_SEND_FAILED = "error.email.send.failed";

    // ==================== 反馈相关 (8000-8999) ====================
    public static final String FEEDBACK_NOT_EXIST = "error.feedback.not.exist";
    public static final String FEEDBACK_UNAUTHORIZED = "error.feedback.unauthorized";

    // ==================== 通用 (9000-9999) ====================
    public static final String VALIDATION_INVALID = "error.validation.invalid";
    public static final String PARAM_INVALID = "error.param.invalid";
    public static final String PARAM_SCAN_IDS_EMPTY = "error.param.scan_ids.empty";
    public static final String PARAM_SCAN_IDS_INVALID = "error.param.scan_ids.invalid";

    // ==================== 限流相关 (10000-10999) ====================
    public static final String RATE_LIMIT_EXCEEDED = "error.rate.limit.exceeded";
    public static final String RATE_LIMIT_IP = "error.rate.limit.ip";
    public static final String RATE_LIMIT_USER = "error.rate.limit.user";
    public static final String RATE_LIMIT_GLOBAL = "error.rate.limit.global";
    public static final String RATE_LIMIT_BLOCKED = "error.rate.limit.blocked";
}
