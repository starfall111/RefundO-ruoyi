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
import com.refund.root.domain.RfIllegalRecords;
import com.refund.root.service.IRfIllegalRecordsService;
import com.refund.common.utils.poi.ExcelUtil;
import com.refund.common.core.page.TableDataInfo;

/**
 * 违规记录Controller
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@RestController
@RequestMapping("/Illegal/illegalRecords")
public class RfIllegalRecordsController extends BaseController
{
    @Autowired
    private IRfIllegalRecordsService rfIllegalRecordsService;

    /**
     * 查询违规记录列表
     */
    @PreAuthorize("@ss.hasPermi('Illegal:illegalRecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(RfIllegalRecords rfIllegalRecords)
    {
        startPage();
        List<RfIllegalRecords> list = rfIllegalRecordsService.selectRfIllegalRecordsList(rfIllegalRecords);
        return getDataTable(list);
    }

    /**
     * 导出违规记录列表
     */
    @PreAuthorize("@ss.hasPermi('Illegal:illegalRecords:export')")
    @Log(title = "违规记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RfIllegalRecords rfIllegalRecords)
    {
        List<RfIllegalRecords> list = rfIllegalRecordsService.selectRfIllegalRecordsList(rfIllegalRecords);
        ExcelUtil<RfIllegalRecords> util = new ExcelUtil<RfIllegalRecords>(RfIllegalRecords.class);
        util.exportExcel(response, list, "违规记录数据");
    }

    /**
     * 获取违规记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('Illegal:illegalRecords:query')")
    @GetMapping(value = "/{illegalId}")
    public AjaxResult getInfo(@PathVariable("illegalId") Long illegalId)
    {
        return success(rfIllegalRecordsService.selectRfIllegalRecordsByIllegalId(illegalId));
    }

    /**
     * 新增违规记录
     */
    @PreAuthorize("@ss.hasPermi('Illegal:illegalRecords:add')")
    @Log(title = "违规记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RfIllegalRecords rfIllegalRecords)
    {
        return toAjax(rfIllegalRecordsService.insertRfIllegalRecords(rfIllegalRecords));
    }

    /**
     * 修改违规记录
     */
    @PreAuthorize("@ss.hasPermi('Illegal:illegalRecords:edit')")
    @Log(title = "违规记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RfIllegalRecords rfIllegalRecords)
    {
        return toAjax(rfIllegalRecordsService.updateRfIllegalRecords(rfIllegalRecords));
    }

    /**
     * 删除违规记录
     */
    @PreAuthorize("@ss.hasPermi('Illegal:illegalRecords:remove')")
    @Log(title = "违规记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{illegalIds}")
    public AjaxResult remove(@PathVariable Long[] illegalIds)
    {
        return toAjax(rfIllegalRecordsService.deleteRfIllegalRecordsByIllegalIds(illegalIds));
    }
}
