package com.refund.common.utils.ratelimit;

import com.refund.common.enums.LimitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 限流工具类
 * 基于Redis实现分布式限流
 */
@Component
public class RateLimitUtil {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * Redis key前缀
     */
    private static final String RATE_LIMIT_PREFIX = "rate_limit:";
    private static final String RATE_LIMIT_IP_PREFIX = RATE_LIMIT_PREFIX + "ip:";
    private static final String RATE_LIMIT_USER_PREFIX = RATE_LIMIT_PREFIX + "user:";
    private static final String RATE_LIMIT_GLOBAL_PREFIX = RATE_LIMIT_PREFIX + "global:";
    private static final String RATE_LIMIT_BLOCKED_PREFIX = RATE_LIMIT_PREFIX + "blocked:";

    /**
     * 登录失败记录相关key前缀（与 UserServiceImpl 保持一致）
     */
    private static final String LOGIN_FAIL_PREFIX = "login_fail:";
    private static final String LOGIN_LOCKED_PREFIX = "login_lock:";

    /**
     * 检查是否达到限流阈值（滑动窗口算法）
     *
     * @param key  限流key
     * @param time 时间窗口（秒）
     * @param count 最大请求数
     * @return true-达到限流阈值，false-未达到
     */
    public boolean isRateLimited(String key, int time, int count) {
        String redisKey = RATE_LIMIT_PREFIX + key;

        // 获取当前时间戳
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - time * 1000L;

        // 使用ZSet存储请求时间戳，score为时间戳
        // 1. 移除时间窗口外的数据
        redisTemplate.opsForZSet().removeRangeByScore(redisKey, 0, windowStart);

        // 2. 获取当前时间窗口内的请求数
        Long currentCount = redisTemplate.opsForZSet().count(redisKey, windowStart, currentTime);
        if (currentCount == null) {
            currentCount = 0L;
        }

        // 3. 判断是否超过阈值
        if (currentCount >= count) {
            return true;
        }

        // 4. 添加当前请求
        redisTemplate.opsForZSet().add(redisKey, String.valueOf(currentTime), currentTime);

        // 5. 设置过期时间
        redisTemplate.expire(redisKey, Duration.ofSeconds(time));

        return false;
    }

    /**
     * 检查IP是否被封禁
     *
     * @param ip IP地址
     * @return true-已封禁，false-未封禁
     */
    public boolean isIPBlocked(String ip) {
        String key = RATE_LIMIT_BLOCKED_PREFIX + "ip:" + ip;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 封禁IP
     *
     * @param ip       IP地址
     * @param duration 封禁时长（秒）
     */
    public void blockIP(String ip, long duration) {
        String key = RATE_LIMIT_BLOCKED_PREFIX + "ip:" + ip;
        redisTemplate.opsForValue().set(key, "blocked", Duration.ofSeconds(duration));
    }

    /**
     * 记录登录失败次数
     *
     * @param identifier 标识符（IP或账号）
     * @param isIP       是否为IP
     * @return 当前失败次数
     */
    public long recordLoginFailure(String identifier, boolean isIP) {
        String prefix = isIP ? LOGIN_FAIL_PREFIX + "ip:" : LOGIN_FAIL_PREFIX + "user:";
        String key = prefix + identifier;

        Long count = redisTemplate.opsForValue().increment(key);
        if (count == null) {
            count = 1L;
        }

        // 设置5分钟过期
        redisTemplate.expire(key, Duration.ofMinutes(5));

        return count;
    }

    /**
     * 检查是否被锁定
     *
     * @param identifier 标识符（IP或账号）
     * @param isIP       是否为IP
     * @return true-已锁定，false-未锁定
     */
    public boolean isLocked(String identifier, boolean isIP) {
        String prefix = isIP ? LOGIN_LOCKED_PREFIX + "ip:" : LOGIN_LOCKED_PREFIX + "user:";
        String key = prefix + identifier;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 锁定账号或IP
     *
     * @param identifier 标识符（IP或账号）
     * @param isIP       是否为IP
     * @param minutes    锁定时长（分钟）
     */
    public void lock(String identifier, boolean isIP, int minutes) {
        String prefix = isIP ? LOGIN_LOCKED_PREFIX + "ip:" : LOGIN_LOCKED_PREFIX + "user:";
        String key = prefix + identifier;
        redisTemplate.opsForValue().set(key, "locked", Duration.ofMinutes(minutes));
    }

    /**
     * 清除登录失败记录（登录成功时调用）
     *
     * @param identifier 标识符（IP或账号）
     * @param isIP       是否为IP
     */
    public void clearLoginFailure(String identifier, boolean isIP) {
        String prefix = isIP ? LOGIN_FAIL_PREFIX + "ip:" : LOGIN_FAIL_PREFIX + "user:";
        String key = prefix + identifier;
        redisTemplate.delete(key);
    }

    /**
     * 构建限流key
     *
     * @param limitType  限流类型
     * @param identifier 标识符（IP、用户ID等）
     * @param endpoint   接口路径
     * @param customKey  自定义key前缀
     * @return 完整的限流key
     */
    public String buildRateLimitKey(LimitType limitType, String identifier, String endpoint, String customKey) {
        String prefix;
        switch (limitType) {
            case IP:
                prefix = RATE_LIMIT_IP_PREFIX;
                break;
            case USER:
                prefix = RATE_LIMIT_USER_PREFIX;
                break;
            case GLOBAL:
                prefix = RATE_LIMIT_GLOBAL_PREFIX;
                break;
            default:
                prefix = RATE_LIMIT_PREFIX;
        }

        StringBuilder keyBuilder = new StringBuilder(prefix);

        if (limitType == LimitType.IP || limitType == LimitType.USER) {
            keyBuilder.append(identifier);
        }

        if (customKey != null && !customKey.isEmpty()) {
            keyBuilder.append(":").append(customKey);
        } else if (endpoint != null && !endpoint.isEmpty()) {
            // 使用接口路径的最后一段作为key
            String[] parts = endpoint.split("/");
            keyBuilder.append(":").append(parts[parts.length - 1]);
        }

        return keyBuilder.toString();
    }
}
