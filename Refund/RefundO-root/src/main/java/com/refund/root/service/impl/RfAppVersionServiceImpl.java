package com.refund.root.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RfAppVersionMapper;
import com.refund.root.domain.RfAppVersion;
import com.refund.root.service.IRfAppVersionService;
import com.refund.root.utils.ApkMd5Utils;

/**
 * version_controllerService业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
@Service
public class RfAppVersionServiceImpl implements IRfAppVersionService 
{
    @Autowired
    private RfAppVersionMapper rfAppVersionMapper;

    private final static String APK_PATH = "/apk/";

    /**
     * 查询version_controller
     * 
     * @param versionId version_controller主键
     * @return version_controller
     */
    @Override
    public RfAppVersion selectRfAppVersionByVersionId(Long versionId)
    {
        return rfAppVersionMapper.selectRfAppVersionByVersionId(versionId);
    }

    /**
     * 查询version_controller列表
     * 
     * @param rfAppVersion version_controller
     * @return version_controller
     */
    @Override
    public List<RfAppVersion> selectRfAppVersionList(RfAppVersion rfAppVersion)
    {
        return rfAppVersionMapper.selectRfAppVersionList(rfAppVersion);
    }

    /**
     * 新增version_controller
     * 
     * @param rfAppVersion version_controller
     * @return 结果
     */
    @Override
    public int insertRfAppVersion(RfAppVersion rfAppVersion)
    {
        String path = rfAppVersion.getDownloadUrl();
//        提取文件名
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        path = APK_PATH + fileName;
        String apkMd5 = ApkMd5Utils.calculateFileMd5(path);
        rfAppVersion.setApkMd5(apkMd5);
        return rfAppVersionMapper.insertRfAppVersion(rfAppVersion);
    }

    /**
     * 修改version_controller
     * 
     * @param rfAppVersion version_controller
     * @return 结果
     */
    @Override
    public int updateRfAppVersion(RfAppVersion rfAppVersion)
    {
        return rfAppVersionMapper.updateRfAppVersion(rfAppVersion);
    }

    /**
     * 批量删除version_controller
     * 
     * @param versionIds 需要删除的version_controller主键
     * @return 结果
     */
    @Override
    public int deleteRfAppVersionByVersionIds(Long[] versionIds)
    {
        return rfAppVersionMapper.deleteRfAppVersionByVersionIds(versionIds);
    }

    /**
     * 删除version_controller信息
     * 
     * @param versionId version_controller主键
     * @return 结果
     */
    @Override
    public int deleteRfAppVersionByVersionId(Long versionId)
    {
        return rfAppVersionMapper.deleteRfAppVersionByVersionId(versionId);
    }
}
