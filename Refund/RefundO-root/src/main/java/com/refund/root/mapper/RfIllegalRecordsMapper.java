package com.refund.root.mapper;

import java.util.List;
import com.refund.root.domain.RfIllegalRecords;
import org.apache.ibatis.annotations.Mapper;

/**
 * 违规记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Mapper
public interface RfIllegalRecordsMapper 
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
     * 删除违规记录
     * 
     * @param illegalId 违规记录主键
     * @return 结果
     */
    public int deleteRfIllegalRecordsByIllegalId(Long illegalId);

    /**
     * 批量删除违规记录
     * 
     * @param illegalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRfIllegalRecordsByIllegalIds(Long[] illegalIds);
}
