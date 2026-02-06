package com.refund.common.utils.sign;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

/**
 * HMAC签名工具类
 * 用于生成和验证HMAC-SHA256签名
 */
public class HmacUtil {
    private static final String ALGORITHM = "HmacSHA256";
    private static final String SECRET_KEY = "3f8a7c1d9e2b0a4f6c5e7d8b9a0c1d2e3f4a5b6c7d8e9f0a1b2c3d4e5f6a7b8c";

    /**
     * 验证HMAC签名
     *
     * @param message 原始消息
     * @param hash    待验证的哈希值
     * @return 是否匹配
     */
    public static boolean verifyHmac(String message, String hash) {
        try {
            String calculatedHash = HmacUtil.generateHmac(message);
            return constantTimeEquals(calculatedHash, hash);
        } catch (Exception e) {
            throw new RuntimeException("HMAC验证失败", e);
        }
    }

    /**
     * 字节数组转十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 生成HMAC签名
     *
     * @param message 原始消息
     * @return HMAC签名
     */
    public static String generateHmac(String message) {
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM
            );
            mac.init(secretKey);

            byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("生成HMAC失败", e);
        }
    }

    /**
     * 常量时间比较，防止时序攻击
     */
    private static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) {
            return Objects.equals(a, b);
        }
        return MessageDigest.isEqual(a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8));
    }
}
