package com.refund.root.controller.api;

import com.refund.common.core.domain.Result;
import com.refund.common.core.domain.vo.TransactionVO;
import com.refund.root.service.IRefundRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * APP端退款交易控制器
 *
 * @author refund
 */
@RestController
@RequestMapping("/api/refund-transactions")
public class AppRefundTransactionsController {

    @Autowired
    private IRefundRequestsService refundRequestsService;

    /**
     * 根据退款请求ID获取交易详情
     *
     * @param requestId 退款请求ID
     * @return 交易详情
     */
    @GetMapping("/{requestId}")
    public Result<TransactionVO> getByRequestId(@PathVariable Long requestId) {
        TransactionVO transactionVO = refundRequestsService.getByRequestId(requestId);
        return Result.success(transactionVO);
    }
}
