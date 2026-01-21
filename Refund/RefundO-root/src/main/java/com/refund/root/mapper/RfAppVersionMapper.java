package com.refund.root.mapper;

import java.util.List;
import com.refund.root.domain.RfAppVersion;

/**
 * version_controllerMapper接口
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
public interface RfAppVersionMapper 
{
    /**
     * 查询version_controller
     * 
     * @param versionId version_controller主键
     * @return version_controller
     */
    public RfAppVersion selectRfAppVersionByVersionId(Long versionId);

    /**
     * 查询version_controller列表
     * 
     * @param rfAppVersion version_controller
     * @return version_controller集合
     */
    public List<RfAppVersion> selectRfAppVersionList(RfAppVersion rfAppVersion);

    /**
     * 新增version_controller
     * 
     * @param rfAppVersion version_controller
     * @return 结果
     */
    public int insertRfAppVersion(RfAppVersion rfAppVersion);

    /**
     * 修改version_controller
     * 
     * @param rfAppVersion version_controller
     * @return 结果
     */
    public int updateRfAppVersion(RfAppVersion rfAppVersion);

    /**
     * 删除version_controller
     * 
     * @param versionId version_controller主键
     * @return 结果
     */
    public int deleteRfAppVersionByVersionId(Long versionId);

    /**
     * 批量删除version_controller
     * 
     * @param versionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRfAppVersionByVersionIds(Long[] versionIds);
}
