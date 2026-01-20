package com.refund.root.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.refund.common.core.domain.model.LoginUser;
import com.refund.common.utils.DateUtils;
import com.refund.common.utils.SecurityUtils;
import com.refund.root.domain.RefundTransactions;
import com.refund.root.domain.RfScanRecords;
import com.refund.root.mapper.RefundTransactionsMapper;
import com.refund.root.service.IRfScanRecordsService;
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

        //当审批拒绝或交易失败时，修改扫描记录状态为0
        if(status == 2 || status == 5){
            for(Long requestId : requestIds){
                RfScanRecords rfScanRecords = new RfScanRecords();
                String ScanIds = refundRequestsMapper.selectRefundRequestsByRequestId(requestId).getScanId();
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
            //在向交易表中批量插入数据
            List<RefundTransactions> refundTransactionsList = new ArrayList<>();

            for(Long requestId : requestIds){
                String refundNumber = numberGenUtil.genTransNumber();
                RefundTransactions refundTransactions = new RefundTransactions();
                refundTransactions.setRequestId(requestId);
                refundTransactions.setAdminId(userId);
                refundTransactions.setTransStatus(Long.valueOf(0));
                refundTransactions.setCreateTime(DateUtils.getNowDate());
                refundTransactions.setUpdateTime(DateUtils.getNowDate());
                refundTransactions.setRefundNumber(refundNumber);
                refundTransactionsList.add(refundTransactions);

            }

            return refundTransactionsMapper.addRefundTransactions(refundTransactionsList);
        }
        return result;
    }
}
