package com.refund.root.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RfIllegalRecordsMapper;
import com.refund.root.domain.RfIllegalRecords;
import com.refund.root.service.IRfIllegalRecordsService;

/**
 * 违规记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Service
public class RfIllegalRecordsServiceImpl implements IRfIllegalRecordsService 
{
    @Autowired
    private RfIllegalRecordsMapper rfIllegalRecordsMapper;

    /**
     * 查询违规记录
     * 
     * @param illegalId 违规记录主键
     * @return 违规记录
     */
    @Override
    public RfIllegalRecords selectRfIllegalRecordsByIllegalId(Long illegalId)
    {
        return rfIllegalRecordsMapper.selectRfIllegalRecordsByIllegalId(illegalId);
    }

    /**
     * 查询违规记录列表
     * 
     * @param rfIllegalRecords 违规记录
     * @return 违规记录
     */
    @Override
    public List<RfIllegalRecords> selectRfIllegalRecordsList(RfIllegalRecords rfIllegalRecords)
    {
        return rfIllegalRecordsMapper.selectRfIllegalRecordsList(rfIllegalRecords);
    }

    /**
     * 新增违规记录
     * 
     * @param rfIllegalRecords 违规记录
     * @return 结果
     */
    @Override
    public int insertRfIllegalRecords(RfIllegalRecords rfIllegalRecords)
    {
        return rfIllegalRecordsMapper.insertRfIllegalRecords(rfIllegalRecords);
    }

    /**
     * 修改违规记录
     * 
     * @param rfIllegalRecords 违规记录
     * @return 结果
     */
    @Override
    public int updateRfIllegalRecords(RfIllegalRecords rfIllegalRecords)
    {
        return rfIllegalRecordsMapper.updateRfIllegalRecords(rfIllegalRecords);
    }

    /**
     * 批量删除违规记录
     * 
     * @param illegalIds 需要删除的违规记录主键
     * @return 结果
     */
    @Override
    public int deleteRfIllegalRecordsByIllegalIds(Long[] illegalIds)
    {
        return rfIllegalRecordsMapper.deleteRfIllegalRecordsByIllegalIds(illegalIds);
    }

    /**
     * 删除违规记录信息
     * 
     * @param illegalId 违规记录主键
     * @return 结果
     */
    @Override
    public int deleteRfIllegalRecordsByIllegalId(Long illegalId)
    {
        return rfIllegalRecordsMapper.deleteRfIllegalRecordsByIllegalId(illegalId);
    }
}
