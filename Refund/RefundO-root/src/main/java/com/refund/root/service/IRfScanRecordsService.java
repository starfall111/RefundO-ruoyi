package com.refund.root.service;

import java.util.List;
import com.refund.root.domain.RfScanRecords;

/**
 * 用户扫码记录Service接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public interface IRfScanRecordsService 
{
    /**
     * 查询用户扫码记录
     * 
     * @param scanId 用户扫码记录主键
     * @return 用户扫码记录
     */
    public RfScanRecords selectRfScanRecordsByScanId(Long scanId);

    /**
     * 查询用户扫码记录列表
     * 
     * @param rfScanRecords 用户扫码记录
     * @return 用户扫码记录集合
     */
    public List<RfScanRecords> selectRfScanRecordsList(RfScanRecords rfScanRecords);

    /**
     * 新增用户扫码记录
     * 
     * @param rfScanRecords 用户扫码记录
     * @return 结果
     */
    public int insertRfScanRecords(RfScanRecords rfScanRecords);

    /**
     * 修改用户扫码记录
     * 
     * @param rfScanRecords 用户扫码记录
     * @return 结果
     */
    public int updateRfScanRecords(RfScanRecords rfScanRecords);

    /**
     * 批量删除用户扫码记录
     * 
     * @param scanIds 需要删除的用户扫码记录主键集合
     * @return 结果
     */
    public int deleteRfScanRecordsByScanIds(Long[] scanIds);

    /**
     * 删除用户扫码记录信息
     * 
     * @param scanId 用户扫码记录主键
     * @return 结果
     */
    public int deleteRfScanRecordsByScanId(Long scanId);
}
