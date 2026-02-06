package com.refund.common.utils;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码生成工具类
 *
 * @author refund
 */
public class VerificationCodeUtil {

    private static final int DEFAULT_LENGTH = 6;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private VerificationCodeUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 生成默认6位数字验证码
     *
     * @return 6位验证码字符串
     */
    public static String generateCode() {
        return generateCode(DEFAULT_LENGTH);
    }

    /**
     * 生成指定长度的数字验证码
     *
     * @param length 验证码长度
     * @return 验证码字符串
     */
    public static String generateCode(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Verification code length must be positive");
        }
        if (length > 10) {
            throw new IllegalArgumentException("Verification code length cannot exceed 10");
        }

        // 使用 ThreadLocalRandom 保证线程安全且避免重复创建实例
        String format = "%0" + length + "d";
        return String.format(format, ThreadLocalRandom.current().nextInt((int) Math.pow(10, length)));
    }

    /**
     * 生成包含字母和数字的验证码
     *
     * @param length 验证码长度
     * @return 验证码字符串
     */
    public static String generateAlphanumericCode(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Verification code length must be positive");
        }

        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // 排除易混淆字符
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            code.append(chars.charAt(SECURE_RANDOM.nextInt(chars.length())));
        }

        return code.toString();
    }
}
