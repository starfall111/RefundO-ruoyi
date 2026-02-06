package com.refund.common.core.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * APP端邮箱验证码DTO
 */
public class EmailCodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 验证码（用于验证）
     */
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
