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
import com.refund.root.domain.RfScanRecords;
import com.refund.root.service.IRfScanRecordsService;
import com.refund.common.utils.poi.ExcelUtil;
import com.refund.common.core.page.TableDataInfo;

/**
 * 用户扫码记录Controller
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@RestController
@RequestMapping("/scan_records/records")
public class RfScanRecordsController extends BaseController
{
    @Autowired
    private IRfScanRecordsService rfScanRecordsService;

    /**
     * 查询用户扫码记录列表
     */
    @PreAuthorize("@ss.hasPermi('scan_records:records:list')")
    @GetMapping("/list")
    public TableDataInfo list(RfScanRecords rfScanRecords)
    {
        startPage();
        List<RfScanRecords> list = rfScanRecordsService.selectRfScanRecordsList(rfScanRecords);
        return getDataTable(list);
    }

    /**
     * 导出用户扫码记录列表
     */
    @PreAuthorize("@ss.hasPermi('scan_records:records:export')")
    @Log(title = "用户扫码记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RfScanRecords rfScanRecords)
    {
        List<RfScanRecords> list = rfScanRecordsService.selectRfScanRecordsList(rfScanRecords);
        ExcelUtil<RfScanRecords> util = new ExcelUtil<RfScanRecords>(RfScanRecords.class);
        util.exportExcel(response, list, "用户扫码记录数据");
    }

    /**
     * 获取用户扫码记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('scan_records:records:query')")
    @GetMapping(value = "/{scanId}")
    public AjaxResult getInfo(@PathVariable("scanId") Long scanId)
    {
        return success(rfScanRecordsService.selectRfScanRecordsByScanId(scanId));
    }

    /**
     * 新增用户扫码记录
     */
    @PreAuthorize("@ss.hasPermi('scan_records:records:add')")
    @Log(title = "用户扫码记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RfScanRecords rfScanRecords)
    {
        return toAjax(rfScanRecordsService.insertRfScanRecords(rfScanRecords));
    }

    /**
     * 修改用户扫码记录
     */
    @PreAuthorize("@ss.hasPermi('scan_records:records:edit')")
    @Log(title = "用户扫码记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RfScanRecords rfScanRecords)
    {
        return toAjax(rfScanRecordsService.updateRfScanRecords(rfScanRecords));
    }

    /**
     * 删除用户扫码记录
     */
    @PreAuthorize("@ss.hasPermi('scan_records:records:remove')")
    @Log(title = "用户扫码记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{scanIds}")
    public AjaxResult remove(@PathVariable Long[] scanIds)
    {
        return toAjax(rfScanRecordsService.deleteRfScanRecordsByScanIds(scanIds));
    }
}
