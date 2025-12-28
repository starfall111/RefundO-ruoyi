package com.refund.root.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class numberGenUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String Scan_Key = "RFS";
    private static final String Request_Key = "RFQ";
    private static final String Trans_Key = "RFT";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String genScanNumber() {
        String dateStr = LocalDate.now().format(DATE_FORMATTER);
        String key = Scan_Key + dateStr;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 自增
        Long increment = valueOperations.increment(key, 1);

        // 生成序列号
        if (increment == 1) {
            // 第一次生成序列号，设置过期时间
            redisTemplate.expire(key, 1, java.util.concurrent.TimeUnit.DAYS);
        }
        return key + String.format("%05d", increment);

    }


    public String genRequestNumber() {
        String dateStr = LocalDate.now().format(DATE_FORMATTER);
        String key = Request_Key + dateStr;
        ValueOperations valueOperations = redisTemplate.opsForValue();

        Long increment = valueOperations.increment(key, 1);

        if (increment == 1) {
            // 第第一次生成序列号，设置过期时间
            redisTemplate.expire(key, 1, java.util.concurrent.TimeUnit.DAYS);
        }

        return key + String.format("%05d", increment);

    }

    public String genTransNumber() {
        String dateStr = LocalDate.now().format(DATE_FORMATTER);
        String key = Trans_Key + dateStr;
        ValueOperations valueOperations = redisTemplate.opsForValue();

        Long increment = valueOperations.increment(key, 1);

        if (increment == 1) {
            // 第第一次生成序列号，设置过期时间
            redisTemplate.expire(key, 1, java.util.concurrent.TimeUnit.DAYS);
        }

        return key + String.format("%05d", increment);
    }
}
