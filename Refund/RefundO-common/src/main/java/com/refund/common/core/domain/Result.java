package com.refund.common.core.domain;

import java.io.Serializable;

/**
 * APP端统一返回结果类
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码: 1-成功, 0-失败
     */
    private Integer Code;

    /**
     * 返回数据
     */
    private T Data;

    /**
     * 返回消息
     */
    private String Message;

    public Result() {
    }

    public Result(Integer code, T data, String message) {
        this.Code = code;
        this.Data = data;
        this.Message = message;
    }

    /**
     * 成功返回（无数据）
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.Code = 1;
        return result;
    }

    /**
     * 成功返回（带数据）
     */
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.Data = object;
        result.Code = 1;
        return result;
    }

    /**
     * 成功返回（带数据和消息）
     */
    public static <T> Result<T> success(T object, String message) {
        Result<T> result = new Result<>();
        result.Data = object;
        result.Code = 1;
        result.Message = message;
        return result;
    }

    /**
     * 失败返回
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.Message = message;
        result.Code = 0;
        return result;
    }

    /**
     * 失败返回（带状态码）
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.Code = code;
        result.Message = message;
        return result;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        this.Code = code;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        this.Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }
}
