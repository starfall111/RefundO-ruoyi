package com.refund.root.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.refund.common.annotation.Log;
import com.refund.common.core.controller.BaseController;
import com.refund.common.core.domain.AjaxResult;
import com.refund.common.enums.BusinessType;
import com.refund.root.domain.RefundTransactions;
import com.refund.root.service.IRefundTransactionsService;
import com.refund.common.utils.poi.ExcelUtil;
import com.refund.common.core.page.TableDataInfo;

/**
 * 退款交易记录Controller
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@RestController
@RequestMapping("/refund_transactions/transactions")
public class RefundTransactionsController extends BaseController
{
    @Autowired
    private IRefundTransactionsService refundTransactionsService;

    /**
     * 查询退款交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('refund_transactions:transactions:list')")
    @GetMapping("/list")
    public TableDataInfo list(RefundTransactions refundTransactions)
    {
        startPage();
        List<RefundTransactions> list = refundTransactionsService.selectRefundTransactionsList(refundTransactions);
        return getDataTable(list);
    }

    /**
     * 导出退款交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('refund_transactions:transactions:export')")
    @Log(title = "退款交易记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RefundTransactions refundTransactions)
    {
        List<RefundTransactions> list = refundTransactionsService.selectRefundTransactionsList(refundTransactions);
        ExcelUtil<RefundTransactions> util = new ExcelUtil<RefundTransactions>(RefundTransactions.class);
        util.exportExcel(response, list, "退款交易记录数据");
    }

    /**
     * 获取退款交易记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('refund_transactions:transactions:query')")
    @GetMapping(value = "/{transId}")
    public AjaxResult getInfo(@PathVariable("transId") Long transId)
    {
        return success(refundTransactionsService.selectRefundTransactionsByTransId(transId));
    }

    /**
     * 新增退款交易记录
     */
    @PreAuthorize("@ss.hasPermi('refund_transactions:transactions:add')")
    @Log(title = "退款交易记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RefundTransactions refundTransactions)
    {
        return toAjax(refundTransactionsService.insertRefundTransactions(refundTransactions));
    }

    /**
     * 打回退款交易记录
     */
    @PreAuthorize("@ss.hasPermi('refund_transactions:transactions:edit')")
    @Log(title = "退款交易记录", businessType = BusinessType.UPDATE)
    @PutMapping("/{transId}/{requestId}")
    public AjaxResult edit(@PathVariable("transId") Long transId, @PathVariable("requestId") Long requestId, @RequestBody Map<String,String> rejectReason)
    {
        String reason = rejectReason.get("rejectReason");
        return toAjax(refundTransactionsService.updateRefundTransactions(transId, requestId, reason,5L));
    }

    /**
     * 删除退款交易记录
     */
    @PreAuthorize("@ss.hasPermi('refund_transactions:transactions:remove')")
    @Log(title = "退款交易记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{transIds}")
    public AjaxResult remove(@PathVariable Long[] transIds)
    {
        return toAjax(refundTransactionsService.deleteRefundTransactionsByTransIds(transIds));
    }

    /*todo 在request接口中，当status被设置为1,2,4,5时，将想用户端发送消息、邮箱？短信？app内部通知？*/

    @PreAuthorize("@ss.hasPermi('refund_transactions:transactions:upload')")
    @Log(title = "上传交易凭证", businessType = BusinessType.UPDATE)
    @PutMapping("/upload/{transId}/{requestId}")
    public AjaxResult upload(@PathVariable("transId") Long transId, @PathVariable("requestId") Long requestId, @RequestBody Map<String,String> remittanceReceiptMap)
    {
        String remittanceReceipt = remittanceReceiptMap.get("remittanceReceipt");
        return toAjax(refundTransactionsService.upload(transId, requestId, remittanceReceipt));
    }

}
