package com.refund.root.mapper;

import java.util.List;

import com.refund.common.core.domain.vo.TransactionVO;
import com.refund.root.domain.RefundTransactions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 退款交易记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Mapper
public interface RefundTransactionsMapper 
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
     * @param refundTransactions 退款交易记录
     * @return 结果
     */
    public int updateRefundTransactions(RefundTransactions refundTransactions);

    /**
     * 删除退款交易记录
     * 
     * @param transId 退款交易记录主键
     * @return 结果
     */
    public int deleteRefundTransactionsByTransId(Long transId);

    /**
     * 批量删除退款交易记录
     * 
     * @param transIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRefundTransactionsByTransIds(Long[] transIds);

    /**
     * 批量插入退款交易记录
     *
     * @param refundTransactionsList 申请编号
     * @return 插入结果
     */
    public int addRefundTransactions(@Param("refundTransactionsList") List<RefundTransactions> refundTransactionsList);

    // ==================== APP端专用方法 ====================

    /**
     * 根据退款请求ID查询交易记录
     *
     * @param requestId 退款请求ID
     * @return 交易记录，不存在则返回null
     */
    TransactionVO selectByRequestId(@Param("requestId") Long requestId);
}