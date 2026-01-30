package com.refund.root.task;

import com.refund.common.utils.DateUtils;
import com.refund.root.domain.RefundRequests;
import com.refund.root.domain.RefundTransactions;
import com.refund.root.mapper.RefundTransactionsMapper;
import com.refund.root.mapper.RefundRequestsMapper;
import com.refund.root.utils.numberGenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 退款定时任务
 */
@Component("refundTasks")
public class RefundTasks {

    private static final Logger log = LoggerFactory.getLogger(RefundTasks.class);

    @Autowired
    private RefundRequestsMapper refundRequestsMapper;

    @Autowired
    private RefundTransactionsMapper refundTransactionsMapper;

    @Autowired
    private numberGenUtils numberGenUtil;

    /**
     * 定时创建退款交易记录
     * 每分钟执行一次，处理状态为1（审批通过待创建交易）的退款请求
     */
    @Scheduled(cron = "0 * * * * ?")
    public void createRefundTransactions() {
        try {
            log.info("开始执行退款交易创建定时任务，时间: {}", DateUtils.getNowDate());

            // 查询状态为1的退款请求
            List<RefundRequests> pendingRequests = refundRequestsMapper.selectRefundRequestsByStatus(1L);

            if (pendingRequests == null || pendingRequests.isEmpty()) {
                log.info("无待处理的退款请求");
                return;
            }

            log.info("发现 {} 条待处理的退款请求", pendingRequests.size());

            List<RefundTransactions> transactionsList = new ArrayList<>();
            List<Long> requestIds = new ArrayList<>();

            // 创建交易记录
            for (RefundRequests request : pendingRequests) {
                String refundNumber = numberGenUtil.genTransNumber();
                RefundTransactions transaction = new RefundTransactions();
                transaction.setRequestId(request.getRequestId());
                transaction.setAdminId(request.getAdminId());
                transaction.setTransStatus(0L);  // 待处理
                transaction.setCreateTime(DateUtils.getNowDate());
                transaction.setUpdateTime(DateUtils.getNowDate());
                transaction.setRefundNumber(refundNumber);
                transactionsList.add(transaction);
                requestIds.add(request.getRequestId());

                log.debug("创建交易记录: requestId={}, refundNumber={}",
                          request.getRequestId(), refundNumber);
            }

            // 批量插入交易记录
            int insertCount = refundTransactionsMapper.addRefundTransactions(transactionsList);
            log.info("成功创建 {} 条交易记录", insertCount);

            // 批量更新退款请求状态为3（退款中）
            if (!requestIds.isEmpty()) {
                Long[] requestIdArray = requestIds.toArray(new Long[0]);
                int updateCount = refundRequestsMapper.batchUpdateRequestStatus(requestIdArray, 3L);
                log.info("成功更新 {} 条退款请求状态为3（退款中）", updateCount);
            }

        } catch (Exception e) {
            log.error("退款交易创建定时任务执行异常", e);
        }
    }
}
