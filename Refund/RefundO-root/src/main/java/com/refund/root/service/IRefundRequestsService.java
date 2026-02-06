package com.refund.root.service;

import java.util.List;
import com.refund.common.core.page.PageResult;
import com.refund.common.core.domain.dto.PageQueryDTO;
import com.refund.common.core.domain.dto.RefundRequestDTO;
import com.refund.common.core.domain.vo.TransactionVO;
import com.refund.root.domain.RefundRequests;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 退款申请Service接口
 *
 * @author ruoyi
 * @date 2025-12-22
 */
public interface IRefundRequestsService {

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
     * 批量删除退款申请
     *
     * @param requestIds 需要删除的退款申请主键集合
     * @return 结果
     */
    public int deleteRefundRequestsByRequestIds(Long[] requestIds);

    /**
     * 删除退款申请信息
     *
     * @param requestId 退款申请主键
     * @return 结果
     */
    public int deleteRefundRequestsByRequestId(Long requestId);

    /**
     * 修改退款申请状态
     * @param requestIds
     * @param status
     * @return
     */
    int updateRefundRequestsStatus(@Param("requestIds") Long[] requestIds, @Param("status") Long status,@Param("reason") String reason);

    // ==================== APP端专用方法 ====================

    /**
     * 创建退款请求（含完整业务验证）
     *
     * @param dto    退款请求DTO
     * @param userId 当前用户ID
     */
    void createRefundRequest(RefundRequestDTO dto, Long userId);

    /**
     * 计算扫描记录总金额
     *
     * @param scanIdsStr 扫描ID列表字符串（逗号分隔）
     * @return 总金额
     */
    BigDecimal calculateTotalAmount(String scanIdsStr);

    /**
     * 分页查询当前用户的退款请求
     *
     * @param userId    用户ID
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    PageResult<RefundRequests> getRequestsByCurrentUserPage(Long userId, PageQueryDTO pageQuery);

    /**
     * 根据请求ID获取退款请求详情（含交易信息）
     *
     * @param requestId 退款请求ID
     * @return 交易详情VO
     */
    TransactionVO getByRequestId(Long requestId);

    /**
     * 根据请求编号获取退款请求
     *
     * @param requestNumber 请求编号
     * @return 退款请求
     */
    RefundRequests getByRequestNumber(String requestNumber);
}
