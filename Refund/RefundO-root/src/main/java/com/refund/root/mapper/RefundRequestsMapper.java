package com.refund.root.mapper;

import java.util.List;
import com.refund.root.domain.RefundRequests;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 退款申请Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Mapper
public interface RefundRequestsMapper 
{
    /**
     * 查询退款申请
     * 
     * @param requestId 退款申请主键
     * @return 退款申请
     */
    public RefundRequests selectRefundRequestsByRequestId(Long requestId);

    /**
     * 查询退款申请列表
     * 
     * @param refundRequests 退款申请
     * @return 退款申请集合
     */
    public List<RefundRequests> selectRefundRequestsList(RefundRequests refundRequests);

    /**
     * 新增退款申请
     * 
     * @param refundRequests 退款申请
     * @return 结果
     */
    public int insertRefundRequests(RefundRequests refundRequests);

    /**
     * 修改退款申请
     * 
     * @param refundRequests 退款申请
     * @return 结果
     */
    public int updateRefundRequests(RefundRequests refundRequests);

    /**
     * 删除退款申请
     * 
     * @param requestId 退款申请主键
     * @return 结果
     */
    public int deleteRefundRequestsByRequestId(Long requestId);

    /**
     * 批量删除退款申请
     * 
     * @param requestIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRefundRequestsByRequestIds(Long[] requestIds);

    /**
     * 修改退款申请状态
     *
     * @param requestIds 退款申请主键
     * @param status 状态
     * */
    void updateRefundRequestsStatus(@Param("requestIds") Long[] requestIds, @Param("status") Integer status);
}
