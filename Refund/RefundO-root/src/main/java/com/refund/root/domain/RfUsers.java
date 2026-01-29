package com.refund.root.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.refund.common.annotation.Excel;
import com.refund.common.core.domain.BaseEntity;

/**
 * 用户信息对象 rf_users
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public class RfUsers extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long userId;

    /** 用户名 */
    @Excel(name = "Username")
    private String username;

    /** 余额 */
    @Excel(name = "Balance")
    private BigDecimal balance;

    /** 状态 */
    @Excel(name = "Status")
    private Long userStatus;

    /** 手机号 */
    @Excel(name = "Phone Number")
    private String phoneNumber;

    /** 邮箱 */
    @Excel(name = "Email")
    private String email;

    /** sangke */
    private String sangke;

    /** wave */
    private String wave;

    /** 用户密码(加密存储) */
    private String password;

    /** 封禁次数 */
    @Excel(name = "Ban Count")
    private Long bansCount;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }

    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }

    public void setUserStatus(Long userStatus) 
    {
        this.userStatus = userStatus;
    }

    public Long getUserStatus() 
    {
        return userStatus;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setSangke(String sangke) 
    {
        this.sangke = sangke;
    }

    public String getSangke() 
    {
        return sangke;
    }

    public void setWave(String wave) 
    {
        this.wave = wave;
    }

    public String getWave() 
    {
        return wave;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setBansCount(Long bansCount) 
    {
        this.bansCount = bansCount;
    }

    public Long getBansCount() 
    {
        return bansCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("username", getUsername())
            .append("balance", getBalance())
            .append("userStatus", getUserStatus())
            .append("phoneNumber", getPhoneNumber())
            .append("email", getEmail())
            .append("sangke", getSangke())
            .append("wave", getWave())
            .append("password", getPassword())
            .append("createTime", getCreateTime())
            .append("bansCount", getBansCount())
            .toString();
    }
}
