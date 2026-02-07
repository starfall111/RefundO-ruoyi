package com.refund.root.mapper;

import com.refund.common.core.domain.vo.ScanRecordsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.refund.root.domain.RfScanRecords;

/**
 * 用户扫码记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-12-22
 */
@Mapper
public interface RfScanRecordsMapper {

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
     * 删除用户扫码记录
     *
     * @param scanId 用户扫码记录主键
     * @return 结果
     */
    public int deleteRfScanRecordsByScanId(Long scanId);

    /**
     * 批量删除用户扫码记录
     *
     * @param scanIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRfScanRecordsByScanIds(Long[] scanIds);

    // ==================== APP端专用方法 ====================

    /**
     * 根据用户ID分页查询扫描记录（含产品信息）
     *
     * @param userId         用户ID
     * @param orderBy        排序字段（amount 或 create_time）
     * @param orderDirection 排序方向（desc 或 asc）
     * @return 扫描记录VO列表
     */
    List<ScanRecordsVO> findByUserIdWithProduct(@Param("userId") Long userId,
                                                  @Param("orderBy") String orderBy,
                                                  @Param("orderDirection") String orderDirection);

    /**
     * 原子性更新退款状态（仅当status=0时更新）
     *
     * @param scanIds 扫描ID组
     * @return 影响行数，0表示未更新（已申请退款）
     */
    int updateRefundStatusIfNotApplied(@Param("scanIds") List<Long> scanIds);

}
