package com.refund.root.service;

import java.util.List;
import com.refund.root.domain.RefundTransactions;
import org.apache.ibatis.annotations.Param;

/**
 * 退款交易记录Service接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public interface IRefundTransactionsService 
{
    /**
     * 查询退款交易记录
     * 
     * @param transId 退款交易记录主键
     * @return 退款交易记录
     */
    public RefundTransactions selectRefundTransactionsByTransId(Long transId);

    /**
     * 查询退款交易记录列表
     * 
     * @param refundTransactions 退款交易记录
     * @return 退款交易记录集合
     */
    public List<RefundTransactions> selectRefundTransactionsList(RefundTransactions refundTransactions);

    /**
     * 新增退款交易记录
     * 
     * @param refundTransactions 退款交易记录
     * @return 结果
     */
    public int insertRefundTransactions(RefundTransactions refundTransactions);

    /**
     * 修改退款交易记录
     * 
     * @param rejectReason 退款交易记录
     * @return 结果
     */
    public int updateRefundTransactions(Long transId, Long requestStatus,String rejectReason,Long transStatus);

    /**
     * 批量删除退款交易记录
     * 
     * @param transIds 需要删除的退款交易记录主键集合
     * @return 结果
     */
    public int deleteRefundTransactionsByTransIds(Long[] transIds);

    /**
     * 删除退款交易记录信息
     * 
     * @param transId 退款交易记录主键
     * @return 结果
     */
    public int deleteRefundTransactionsByTransId(Long transId);

    /**
     * 上传交易凭证
     * @param transId
     * @param requestId
     * @param remittanceReceipt
     * @return
     */
    int upload(@Param("transId") Long transId, @Param("requestId") Long requestId, @Param("remittanceReceipt") String remittanceReceipt);
}
