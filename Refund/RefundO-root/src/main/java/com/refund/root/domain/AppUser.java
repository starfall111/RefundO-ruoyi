package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * APP端用户实体类
 *
 * @author refund
 */
public class AppUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 三克
     */
    private String sangke;

    /**
     * WAVE
     */
    private String wave;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 封禁次数
     */
    private Integer bansCount;

    /**
     * 无参构造函数
     */
    public AppUser() {
    }

    /**
     * 全参数构造函数
     */
    public AppUser(Long userId, String userName, String password, BigDecimal balance, Integer userStatus,
                   String phoneNumber, String email, String sangke, String wave, String avatarUrl,
                   LocalDateTime createTime, Integer bansCount) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.balance = balance;
        this.userStatus = userStatus;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.sangke = sangke;
        this.wave = wave;
        this.avatarUrl = avatarUrl;
        this.createTime = createTime;
        this.bansCount = bansCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSangke() {
        return sangke;
    }

    public void setSangke(String sangke) {
        this.sangke = sangke;
    }

    public String getWave() {
        return wave;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getBansCount() {
        return bansCount;
    }

    public void setBansCount(Integer bansCount) {
        this.bansCount = bansCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", userId)
                .append("userName", userName)
                .append("balance", balance)
                .append("userStatus", userStatus)
                .append("phoneNumber", phoneNumber)
                .append("email", email)
                .append("sangke", sangke)
                .append("wave", wave)
                .append("avatarUrl", avatarUrl)
                .append("createTime", createTime)
                .append("bansCount", bansCount)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUser appUser = (AppUser) o;

        return userId != null ? userId.equals(appUser.userId) : appUser.userId == null;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
