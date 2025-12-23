package com.refund.root.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.refund.common.core.domain.model.LoginUser;
import com.refund.common.utils.DateUtils;
import com.refund.common.utils.SecurityUtils;
import com.refund.root.domain.RefundTransactions;
import com.refund.root.mapper.RefundTransactionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RefundRequestsMapper;
import com.refund.root.domain.RefundRequests;
import com.refund.root.service.IRefundRequestsService;

/**
 * 退款申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Service
public class RefundRequestsServiceImpl implements IRefundRequestsService 
{
    @Autowired
    private RefundRequestsMapper refundRequestsMapper;

    @Autowired
    private RefundTransactionsMapper refundTransactionsMapper;

    /**
     * 查询退款申请
     * 
     * @param requestId 退款申请主键
     * @return 退款申请
     */
    @Override
    public RefundRequests selectRefundRequestsByRequestId(Long requestId)
    {
        return refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
    }

    /**
     * 查询退款申请列表
     * 
     * @param refundRequests 退款申请
     * @return 退款申请
     */
    @Override
    public List<RefundRequests> selectRefundRequestsList(RefundRequests refundRequests)
    {
        return refundRequestsMapper.selectRefundRequestsList(refundRequests);
    }

    /**
     * 新增退款申请
     * 
     * @param refundRequests 退款申请
     * @return 结果
     */
    @Override
    public int insertRefundRequests(RefundRequests refundRequests)
    {
        refundRequests.setCreateTime(DateUtils.getNowDate());
        return refundRequestsMapper.insertRefundRequests(refundRequests);
    }

    /**
     * 修改退款申请
     * 
     * @param refundRequests 退款申请
     * @return 结果
     */
    @Override
    public int updateRefundRequests(RefundRequests refundRequests)
    {
        refundRequests.setUpdateTime(DateUtils.getNowDate());
        return refundRequestsMapper.updateRefundRequests(refundRequests);
    }

    /**
     * 批量删除退款申请
     * 
     * @param requestIds 需要删除的退款申请主键
     * @return 结果
     */
    @Override
    public int deleteRefundRequestsByRequestIds(Long[] requestIds)
    {
        return refundRequestsMapper.deleteRefundRequestsByRequestIds(requestIds);
    }

    /**
     * 删除退款申请信息
     * 
     * @param requestId 退款申请主键
     * @return 结果
     */
    @Override
    public int deleteRefundRequestsByRequestId(Long requestId)
    {
        return refundRequestsMapper.deleteRefundRequestsByRequestId(requestId);
    }

    /**
     * 审批退款申请
     * @param requestIds 退款申请主键
     * @param status 状态
     * @return 结果
     */
    @Override
    public int updateRefundRequestsStatus(Long[] requestIds, Integer status) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        //先修改请求状态
        refundRequestsMapper.updateRefundRequestsStatus(requestIds, status);

        //在向交易表中批量插入数据
        List<RefundTransactions> refundTransactionsList = new ArrayList<>();

        for(Long requestId : requestIds){
            RefundTransactions refundTransactions = new RefundTransactions();
            refundTransactions.setRequestId(requestId);
            refundTransactions.setAdminId(userId);
            refundTransactions.setTransStatus(Long.valueOf(0));
            refundTransactions.setCreateTime(DateUtils.getNowDate());
            refundTransactions.setUpdateTime(DateUtils.getNowDate());
            refundTransactionsList.add(refundTransactions);
        }

        return refundTransactionsMapper.addRefundTransactions(refundTransactionsList);

    }
}
