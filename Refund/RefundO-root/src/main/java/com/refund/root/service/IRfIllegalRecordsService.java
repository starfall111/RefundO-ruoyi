package com.refund.root.service;

import java.util.List;
import com.refund.root.domain.RfIllegalRecords;

/**
 * 违规记录Service接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public interface IRfIllegalRecordsService 
{
    /**
     * 查询违规记录
     * 
     * @param illegalId 违规记录主键
     * @return 违规记录
     */
    public RfIllegalRecords selectRfIllegalRecordsByIllegalId(Long illegalId);

    /**
     * 查询违规记录列表
     * 
     * @param rfIllegalRecords 违规记录
     * @return 违规记录集合
     */
    public List<RfIllegalRecords> selectRfIllegalRecordsList(RfIllegalRecords rfIllegalRecords);

    /**
     * 新增违规记录
     * 
     * @param rfIllegalRecords 违规记录
     * @return 结果
     */
    public int insertRfIllegalRecords(RfIllegalRecords rfIllegalRecords);

    /**
     * 修改违规记录
     * 
     * @param rfIllegalRecords 违规记录
     * @return 结果
     */
    public int updateRfIllegalRecords(RfIllegalRecords rfIllegalRecords);

    /**
     * 批量删除违规记录
     * 
     * @param illegalIds 需要删除的违规记录主键集合
     * @return 结果
     */
    public int deleteRfIllegalRecordsByIllegalIds(Long[] illegalIds);

    /**
     * 删除违规记录信息
     * 
     * @param illegalId 违规记录主键
     * @return 结果
     */
    public int deleteRfIllegalRecordsByIllegalId(Long illegalId);
}
