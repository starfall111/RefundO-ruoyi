package com.refund.root.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
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
import com.refund.root.domain.RefundRequests;
import com.refund.root.service.IRefundRequestsService;
import com.refund.common.utils.poi.ExcelUtil;
import com.refund.common.core.page.TableDataInfo;

/**
 * 退款申请Controller
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@RestController
@RequestMapping("/refund_request/requests")
public class RefundRequestsController extends BaseController
{
    @Autowired
    private IRefundRequestsService refundRequestsService;

    /**
     * 查询退款申请列表
     */
    @PreAuthorize("@ss.hasPermi('refund_request:requests:list')")
    @GetMapping("/list")
    public TableDataInfo list(RefundRequests refundRequests)
    {
        startPage();
        List<RefundRequests> list = refundRequestsService.selectRefundRequestsList(refundRequests);
        return getDataTable(list);
    }

    /**
     * 导出退款申请列表
     */
    @PreAuthorize("@ss.hasPermi('refund_request:requests:export')")
    @Log(title = "退款申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RefundRequests refundRequests)
    {
        List<RefundRequests> list = refundRequestsService.selectRefundRequestsList(refundRequests);
        ExcelUtil<RefundRequests> util = new ExcelUtil<RefundRequests>(RefundRequests.class);
        util.exportExcel(response, list, "退款申请数据");
    }

    /**
     * 获取退款申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('refund_request:requests:query')")
    @GetMapping(value = "/{requestId}")
    public AjaxResult getInfo(@PathVariable("requestId") Long requestId)
    {
        return success(refundRequestsService.selectRefundRequestsByRequestId(requestId));
    }

    /**
     * 新增退款申请
     */
    @PreAuthorize("@ss.hasPermi('refund_request:requests:add')")
    @Log(title = "退款申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RefundRequests refundRequests)
    {
        return toAjax(refundRequestsService.insertRefundRequests(refundRequests));
    }

    /**
     * 修改退款申请
     */
    @PreAuthorize("@ss.hasPermi('refund_request:requests:edit')")
    @Log(title = "退款申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RefundRequests refundRequests)
    {
        return toAjax(refundRequestsService.updateRefundRequests(refundRequests));
    }

    /**
     * 删除退款申请
     */
    @PreAuthorize("@ss.hasPermi('refund_request:requests:remove')")
    @Log(title = "退款申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{requestIds}")
    public AjaxResult remove(@PathVariable Long[] requestIds)
    {
        return toAjax(refundRequestsService.deleteRefundRequestsByRequestIds(requestIds));
    }

    /**
     * 审批退款申请
     * */
    @PreAuthorize("@ss.hasPermi('refund_request:request:status')")
    @Log(title = "退款申请", businessType = BusinessType.UPDATE)
    @PutMapping("/status/{requestIds}/{status}")
    public AjaxResult approve(@PathVariable("requestIds") Long[] requestIds, @PathVariable("status") Integer status)
    {
        return toAjax(refundRequestsService.updateRefundRequestsStatus(requestIds, status));
    }
}
