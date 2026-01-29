package com.refund.root.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.refund.common.annotation.Excel;
import com.refund.common.core.domain.BaseEntity;

/**
 * version_controller对象 rf_app_version
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
public class RfAppVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 版本ID */
    @Excel(name = "Version ID")
    private Long versionId;

    /** 版本状态 */
    @Excel(name = "Version Status")
    private Long updateStatus;

    /** 版本code */
    @Excel(name = "Version Code")
    private Long versionCode;

    /** 版本名 */
    @Excel(name = "Version Name")
    private String versionName;

    /** 更新内容 */
    @Excel(name = "Update Content")
    private String modifyContent;

    /** 安装包 */
    @Excel(name = "Download URL")
    private String downloadUrl;

    /** 安装包大小 */
    @Excel(name = "APK Size")
    private BigDecimal apkSize;

    /** 1111 */
    private String apkMd5;

    public void setVersionId(Long versionId) 
    {
        this.versionId = versionId;
    }

    public Long getVersionId() 
    {
        return versionId;
    }

    public void setUpdateStatus(Long updateStatus) 
    {
        this.updateStatus = updateStatus;
    }

    public Long getUpdateStatus() 
    {
        return updateStatus;
    }

    public void setVersionCode(Long versionCode) 
    {
        this.versionCode = versionCode;
    }

    public Long getVersionCode() 
    {
        return versionCode;
    }

    public void setVersionName(String versionName) 
    {
        this.versionName = versionName;
    }

    public String getVersionName() 
    {
        return versionName;
    }

    public void setModifyContent(String modifyContent) 
    {
        this.modifyContent = modifyContent;
    }

    public String getModifyContent() 
    {
        return modifyContent;
    }

    public void setDownloadUrl(String downloadUrl) 
    {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() 
    {
        return downloadUrl;
    }

    public void setApkSize(BigDecimal apkSize) 
    {
        this.apkSize = apkSize;
    }

    public BigDecimal getApkSize() 
    {
        return apkSize;
    }

    public void setApkMd5(String apkMd5) 
    {
        this.apkMd5 = apkMd5;
    }

    public String getApkMd5() 
    {
        return apkMd5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("versionId", getVersionId())
            .append("updateStatus", getUpdateStatus())
            .append("versionCode", getVersionCode())
            .append("versionName", getVersionName())
            .append("modifyContent", getModifyContent())
            .append("downloadUrl", getDownloadUrl())
            .append("apkSize", getApkSize())
            .append("apkMd5", getApkMd5())
            .toString();
    }
}
