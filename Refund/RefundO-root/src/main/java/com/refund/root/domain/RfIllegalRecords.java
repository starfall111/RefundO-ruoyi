package com.refund.root.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.refund.common.annotation.Excel;
import com.refund.common.core.domain.BaseEntity;

/**
 * 违规记录对象 rf_illegal_records
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public class RfIllegalRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 违规记录ID */
    @Excel(name = "Violation Record ID")
    private Long illegalId;

    /** 违规原因详细描述 */
    @Excel(name = "Violation Reason")
    private String reason;

    /** 违规时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Violation Time", width = 30, dateFormat = "yyyy-MM-dd")
    private Date scanTime;

    /** 违规用户 */
    @Excel(name = "Violation User ID")
    private Long userId;

    public void setIllegalId(Long illegalId) 
    {
        this.illegalId = illegalId;
    }

    public Long getIllegalId() 
    {
        return illegalId;
    }

    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }

    public void setScanTime(Date scanTime) 
    {
        this.scanTime = scanTime;
    }

    public Date getScanTime() 
    {
        return scanTime;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("illegalId", getIllegalId())
            .append("reason", getReason())
            .append("scanTime", getScanTime())
            .append("userId", getUserId())
            .toString();
    }
}
