package com.refund.root.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * APP版本信息
 *
 * @author refund
 */
public class AppVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 版本ID
     */
    private Long versionId;

    /**
     * 更新状态
     */
    private Integer updateStatus;

    /**
     * 版本号
     */
    private Integer versionCode;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 修改内容
     */
    private String modifyContent;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * APK大小
     */
    private Integer apkSize;

    /**
     * 文件MD5
     */
    private String fileMd5;

    /**
     * 无参构造函数
     */
    public AppVersion() {
    }

    /**
     * 全参数构造函数
     */
    public AppVersion(Long versionId, Integer updateStatus, Integer versionCode, String versionName,
                       String modifyContent, String downloadUrl, Integer apkSize, String fileMd5) {
        this.versionId = versionId;
        this.updateStatus = updateStatus;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.modifyContent = modifyContent;
        this.downloadUrl = downloadUrl;
        this.apkSize = apkSize;
        this.fileMd5 = fileMd5;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getModifyContent() {
        return modifyContent;
    }

    public void setModifyContent(String modifyContent) {
        this.modifyContent = modifyContent;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getApkSize() {
        return apkSize;
    }

    public void setApkSize(Integer apkSize) {
        this.apkSize = apkSize;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("versionId", versionId)
                .append("updateStatus", updateStatus)
                .append("versionCode", versionCode)
                .append("versionName", versionName)
                .append("modifyContent", modifyContent)
                .append("downloadUrl", downloadUrl)
                .append("apkSize", apkSize)
                .append("fileMd5", fileMd5)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppVersion appVersion = (AppVersion) o;

        return versionId != null ? versionId.equals(appVersion.versionId) : appVersion.versionId == null;
    }

    @Override
    public int hashCode() {
        return versionId != null ? versionId.hashCode() : 0;
    }
}
