package com.refund.root.controller;

import java.util.List;
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
     * 修改退款交易记录
     */
    @PreAuthorize("@ss.hasPermi('refund_transactions:transactions:edit')")
    @Log(title = "退款交易记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RefundTransactions refundTransactions)
    {
        return toAjax(refundTransactionsService.updateRefundTransactions(refundTransactions));
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

    /*todo 一、上传交易凭证，上传成功则改变status为1
       ，二、设置交易单状态为2，接收失败原因，在service层调用requestservice 将请求单状态改为拒绝，将失败原因作为拒绝原因
       三、在request接口中，当status被设置为2时，将想用户端发送消息、邮箱？短信？app内部通知？
     */
}
