package com.refund.root.service.impl;

import java.util.List;

import com.refund.common.core.domain.model.LoginUser;
import com.refund.common.service.IMailService;
import com.refund.common.utils.DateUtils;
import com.refund.common.utils.SecurityUtils;
import com.refund.root.domain.RefundRequests;
import com.refund.root.domain.RfUsers;
import com.refund.root.service.IRefundRequestsService;
import com.refund.root.service.IRfUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RefundTransactionsMapper;
import com.refund.root.domain.RefundTransactions;
import com.refund.root.service.IRefundTransactionsService;

/**
 * 退款交易记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Service
public class RefundTransactionsServiceImpl implements IRefundTransactionsService 
{
    @Autowired
    private RefundTransactionsMapper refundTransactionsMapper;

    @Autowired
    private IRefundRequestsService refundRequestsService;

    @Autowired
    private IRfUsersService rfUsersService;

    @Autowired
    private IMailService mailService;

    /**
     * 查询退款交易记录
     * 
     * @param transId 退款交易记录主键
     * @return 退款交易记录
     */
    @Override
    public RefundTransactions selectRefundTransactionsByTransId(Long transId)
    {
        return refundTransactionsMapper.selectRefundTransactionsByTransId(transId);
    }

    /**
     * 查询退款交易记录列表
     * 
     * @param refundTransactions 退款交易记录
     * @return 退款交易记录
     */
    @Override
    public List<RefundTransactions> selectRefundTransactionsList(RefundTransactions refundTransactions)
    {
        return refundTransactionsMapper.selectRefundTransactionsList(refundTransactions);
    }

    /**
     * 新增退款交易记录
     * 
     * @param refundTransactions 退款交易记录
     * @return 结果
     */
    @Override
    public int insertRefundTransactions(RefundTransactions refundTransactions)
    {
        refundTransactions.setCreateTime(DateUtils.getNowDate());
        return refundTransactionsMapper.insertRefundTransactions(refundTransactions);
    }

    /**
     * 修改退款交易记录
     * 
     * @param rejectReason 退款交易记录
     * @return 结果
     */
    @Override
    public int updateRefundTransactions(Long transId, Long requestId,String rejectReason,Long requestStatus)
    {
        RefundTransactions refundTransactions = new RefundTransactions();
        Long[] requestIds = new Long[]{requestId};
        refundRequestsService.updateRefundRequestsStatus(requestIds, requestStatus,rejectReason);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        refundTransactions.setAdminId(userId);
        refundTransactions.setTransId(transId);
        refundTransactions.setTransStatus(2L);
        refundTransactions.setUpdateTime(DateUtils.getNowDate());
        return refundTransactionsMapper.updateRefundTransactions(refundTransactions);
    }

    /**
     * 批量删除退款交易记录
     * 
     * @param transIds 需要删除的退款交易记录主键
     * @return 结果
     */
    @Override
    public int deleteRefundTransactionsByTransIds(Long[] transIds)
    {
        return refundTransactionsMapper.deleteRefundTransactionsByTransIds(transIds);
    }

    /**
     * 删除退款交易记录信息
     * 
     * @param transId 退款交易记录主键
     * @return 结果
     */
    @Override
    public int deleteRefundTransactionsByTransId(Long transId)
    {
        return refundTransactionsMapper.deleteRefundTransactionsByTransId(transId);
    }

    /**
     * 上传交易凭证
     * @param transId
     * @param requestId
     * @param remittanceReceipt
     * @return
     */
    @Override
    public int upload(Long transId, Long requestId, String remittanceReceipt) {
        RefundTransactions refundTransactions = new RefundTransactions();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long[] requestIds = new Long[]{requestId};
        refundRequestsService.updateRefundRequestsStatus(requestIds, 4L,"");
        refundTransactions.setTransId(transId);
        refundTransactions.setAdminId(loginUser.getUserId());
        refundTransactions.setRemittanceReceipt(remittanceReceipt);
        refundTransactions.setTransStatus(1L);
        refundTransactions.setUpdateTime(DateUtils.getNowDate());
        return refundTransactionsMapper.updateRefundTransactions(refundTransactions);
    }
}
