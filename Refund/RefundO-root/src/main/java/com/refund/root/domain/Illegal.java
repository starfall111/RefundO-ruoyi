package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 违法记录实体类
 *
 * @author refund
 */
public class Illegal implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 违法记录ID
     */
    private Long illegalId;

    /**
     * 原因
     */
    private String reason;

    /**
     * 扫描时间
     */
    private LocalDateTime scanTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 无参构造函数
     */
    public Illegal() {
    }

    /**
     * 全参数构造函数
     */
    public Illegal(Long illegalId, String reason, LocalDateTime scanTime, Long userId) {
        this.illegalId = illegalId;
        this.reason = reason;
        this.scanTime = scanTime;
        this.userId = userId;
    }

    public Long getIllegalId() {
        return illegalId;
    }

    public void setIllegalId(Long illegalId) {
        this.illegalId = illegalId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getScanTime() {
        return scanTime;
    }

    public void setScanTime(LocalDateTime scanTime) {
        this.scanTime = scanTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("illegalId", illegalId)
                .append("reason", reason)
                .append("scanTime", scanTime)
                .append("userId", userId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Illegal illegal = (Illegal) o;

        return illegalId != null ? illegalId.equals(illegal.illegalId) : illegal.illegalId == null;
    }

    @Override
    public int hashCode() {
        return illegalId != null ? illegalId.hashCode() : 0;
    }
}
