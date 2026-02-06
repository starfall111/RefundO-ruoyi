package com.refund.root.controller.api;

import com.refund.common.annotation.RateLimit;
import com.refund.common.core.domain.Result;
import com.refund.common.core.domain.dto.EmailCodeDTO;
import com.refund.common.enums.LimitType;
import com.refund.root.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 验证码控制器（注册、找回密码通用）
 *
 * @author refund
 */
@RestController
@RequestMapping("/api/verification-code")
public class AppVerificationCodeController {

    @Autowired
    private IVerificationCodeService verificationCodeService;

    /**
     * 发送验证码（注册、找回密码通用）
     * 限流：每个IP每分钟最多3次
     */
    @PostMapping("/send")
    @RateLimit(time = 60, count = 3, limitType = LimitType.IP, key = "send")
    public Result<Void> sendCode(@Validated @RequestBody EmailCodeDTO dto) {
        verificationCodeService.sendCode(dto.getEmail());
        return Result.success();
    }

    /**
     * 验证验证码（注册、找回密码通用）
     */
    @PostMapping("/verify")
    public Result<Void> verifyCode(@Validated @RequestBody EmailCodeDTO dto) {
        verificationCodeService.verifyCode(dto.getEmail(), dto.getCode());
        return Result.success();
    }
}
