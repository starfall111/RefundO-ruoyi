package com.refund.root.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.refund.common.core.domain.model.LoginUser;
import com.refund.common.service.IMailService;
import com.refund.common.utils.DateUtils;
import com.refund.common.utils.SecurityUtils;
import com.refund.root.domain.RefundTransactions;
import com.refund.root.domain.RfScanRecords;
import com.refund.root.domain.RfUsers;
import com.refund.root.mapper.RefundTransactionsMapper;
import com.refund.root.service.IRfScanRecordsService;
import com.refund.root.service.IRfUsersService;
import com.refund.root.utils.numberGenUtils;
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

    @Autowired
    private IRfScanRecordsService rfScanRecordsService;

    @Autowired
    private numberGenUtils numberGenUtil;

    @Autowired
    private IRfUsersService rfUsersService;

    @Autowired
    private IMailService mailService;

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
//    TODO 在添加退款交易记录时，先检查表中是否已经存在requestId相同的记录，如果存在则不添加，如果不存在则添加
    @Override
    public int updateRefundRequestsStatus(Long[] requestIds, Long status,String rejectReason) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();

        //判断请求状态是否合法
        if (status == 1 || status == 2) {
            for (Long requestId : requestIds) {
                RefundRequests refundRequests = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
                if (refundRequests.getRequestStatus() != 0) {
                    return -1;
                }
            }
        }

        //当审批拒绝或交易失败时，修改扫描记录状态为0并返还余额
        if(status == 2 || status == 5){
            for(Long requestId : requestIds){
                RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);

                // 根据目标状态确定允许的前置状态
                List<Long> allowedStatuses = new ArrayList<>();
                if(status == 2) {
                    // 审批拒绝：前置状态必须是 0（待审批）
                    allowedStatuses.add(0L);
                } else{
                    // 交易失败：前置状态必须是 3（退款中）
                    allowedStatuses.add(3L);
                }

                // 先原子化返还余额（状态校验确保幂等性）
                boolean balanceReturned = rfUsersService.increaseBalanceWithRequestCheck(
                        request.getUserId(),
                        request.getAmount(),
                        requestId,
                        allowedStatuses
                );

                if (!balanceReturned) {
                    // 余额返还失败（状态已变更或其他原因），记录日志
                    System.err.println("余额返还失败，请求状态可能已变更, requestId: " + requestId + ", userId: " + request.getUserId() + ", targetStatus: " + status);
                }

                // 修改扫描记录状态为0
                RfScanRecords rfScanRecords = new RfScanRecords();
                String ScanIds = request.getScanId();
                String[] ScanIdArray = ScanIds.split(",");
//                将扫描记录ID转为Long类型
                Long[] scanIdArray = new Long[ScanIdArray.length];
                for(int i = 0; i < ScanIdArray.length; i++){
                    scanIdArray[i] = Long.parseLong(ScanIdArray[i]);
                    rfScanRecords.setScanId(scanIdArray[i]);
                    rfScanRecords.setRefundStatus(0);
                    rfScanRecordsService.updateRfScanRecords(rfScanRecords);
                }
            }
        }


        //先修改请求状态
        int result = refundRequestsMapper.updateRefundRequestsStatus(requestIds, status,rejectReason,userId);

        if(status == 1){
            // 不再立即创建交易记录，由定时任务处理

            // 发送审批通过邮件
            for(Long requestId : requestIds){
                RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
                RfUsers user = rfUsersService.selectUsersByUserId(request.getUserId());
                if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
                    mailService.sendRefundApprovedEmail(user.getEmail(), user.getUsername(),
                            request.getRequestNumber(), request.getAmount());
                }
            }

            return result;
        }

        // 状态2：审批拒绝，发送拒绝邮件
        if(status == 2){
            for(Long requestId : requestIds){
                RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
                RfUsers user = rfUsersService.selectUsersByUserId(request.getUserId());
                if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
                    mailService.sendRefundRejectedEmail(user.getEmail(), user.getUsername(),
                            request.getRequestNumber(), rejectReason);
                }
            }
        }

        // 状态5：交易失败，发送交易失败邮件
        if(status == 5){
            for(Long requestId : requestIds){
                RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
                RfUsers user = rfUsersService.selectUsersByUserId(request.getUserId());
                if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
                    mailService.sendTransactionFailedEmail(user.getEmail(), user.getUsername(),
                            request.getRequestNumber(), rejectReason);
                }
            }
        }

        return result;
    }
}
