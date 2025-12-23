package com.refund.root.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RfScanRecordsMapper;
import com.refund.root.domain.RfScanRecords;
import com.refund.root.service.IRfScanRecordsService;

/**
 * 用户扫码记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Service
public class RfScanRecordsServiceImpl implements IRfScanRecordsService 
{
    @Autowired
    private RfScanRecordsMapper rfScanRecordsMapper;

    /**
     * 查询用户扫码记录
     * 
     * @param scanId 用户扫码记录主键
     * @return 用户扫码记录
     */
    @Override
    public RfScanRecords selectRfScanRecordsByScanId(Long scanId)
    {
        return rfScanRecordsMapper.selectRfScanRecordsByScanId(scanId);
    }

    /**
     * 查询用户扫码记录列表
     * 
     * @param rfScanRecords 用户扫码记录
     * @return 用户扫码记录
     */
    @Override
    public List<RfScanRecords> selectRfScanRecordsList(RfScanRecords rfScanRecords)
    {
        return rfScanRecordsMapper.selectRfScanRecordsList(rfScanRecords);
    }

    /**
     * 新增用户扫码记录
     * 
     * @param rfScanRecords 用户扫码记录
     * @return 结果
     */
    @Override
    public int insertRfScanRecords(RfScanRecords rfScanRecords)
    {
        return rfScanRecordsMapper.insertRfScanRecords(rfScanRecords);
    }

    /**
     * 修改用户扫码记录
     * 
     * @param rfScanRecords 用户扫码记录
     * @return 结果
     */
    @Override
    public int updateRfScanRecords(RfScanRecords rfScanRecords)
    {
        return rfScanRecordsMapper.updateRfScanRecords(rfScanRecords);
    }

    /**
     * 批量删除用户扫码记录
     * 
     * @param scanIds 需要删除的用户扫码记录主键
     * @return 结果
     */
    @Override
    public int deleteRfScanRecordsByScanIds(Long[] scanIds)
    {
        return rfScanRecordsMapper.deleteRfScanRecordsByScanIds(scanIds);
    }

    /**
     * 删除用户扫码记录信息
     * 
     * @param scanId 用户扫码记录主键
     * @return 结果
     */
    @Override
    public int deleteRfScanRecordsByScanId(Long scanId)
    {
        return rfScanRecordsMapper.deleteRfScanRecordsByScanId(scanId);
    }
}
