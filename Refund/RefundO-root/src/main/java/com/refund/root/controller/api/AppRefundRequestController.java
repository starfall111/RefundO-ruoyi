package com.refund.root.controller.api;

import com.refund.common.annotation.RateLimit;
import com.refund.common.core.page.PageResult;
import com.refund.common.core.domain.dto.PageQueryDTO;
import com.refund.common.core.domain.dto.RefundRequestDTO;
import com.refund.common.core.domain.Result;
import com.refund.common.core.domain.vo.TransactionVO;
import com.refund.common.enums.LimitType;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.root.domain.RefundRequests;
import com.refund.root.service.IRefundRequestsService;
import com.refund.root.service.IRefundTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * APP端退款请求控制器
 */
@RestController
@RequestMapping("/api/refund-request")
public class AppRefundRequestController {

    @Autowired
    private IRefundRequestsService refundRequestsService;

    @Autowired
    private IRefundTransactionsService refundTransactionsService;

    /**
     * 创建退款请求
     */
    @PostMapping
    @RateLimit(time = 3600, count = 10, limitType = LimitType.USER, key = "create")
    public Result<Void> createRefundRequest(@RequestBody RefundRequestDTO refundRequestDTO) {
        Long userId = ApiSecurityUtils.getUserId();
        refundRequestsService.createRefundRequest(refundRequestDTO, userId);
        return Result.success();
    }

    /**
     * 计算符合条件的扫描记录总金额
     */
    @GetMapping("/calculate-amount")
    public Result<BigDecimal> calculateTotalAmount(@RequestParam String scanIds) {
        BigDecimal totalAmount = refundRequestsService.calculateTotalAmount(scanIds);
        return Result.success(totalAmount);
    }

    /**
     * 分页获取当前用户的退款请求记录
     */
    @GetMapping("/list")
    public Result<PageResult<RefundRequests>> getRequestsPage(PageQueryDTO pageQuery) {
        Long userId = ApiSecurityUtils.getUserId();
        PageResult<RefundRequests> result = refundRequestsService.getRequestsByCurrentUserPage(userId, pageQuery);
        return Result.success(result);
    }

    /**
     * 根据ID获取退款请求审批记录
     */
    @GetMapping("/{id}")
    public Result<TransactionVO> get(@PathVariable Long id) {
        TransactionVO transaction = refundRequestsService.getByRequestId(id);
        return Result.success(transaction);
    }
}
