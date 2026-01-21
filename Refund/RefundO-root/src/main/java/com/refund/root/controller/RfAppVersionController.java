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
import com.refund.root.domain.RfAppVersion;
import com.refund.root.service.IRfAppVersionService;
import com.refund.common.utils.poi.ExcelUtil;
import com.refund.common.core.page.TableDataInfo;

/**
 * version_controllerController
 * 
 * @author ruoyi
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/version/version")
public class RfAppVersionController extends BaseController
{
    @Autowired
    private IRfAppVersionService rfAppVersionService;

    /**
     * 查询version_controller列表
     */
    @PreAuthorize("@ss.hasPermi('version:version:list')")
    @GetMapping("/list")
    public TableDataInfo list(RfAppVersion rfAppVersion)
    {
        startPage();
        List<RfAppVersion> list = rfAppVersionService.selectRfAppVersionList(rfAppVersion);
        return getDataTable(list);
    }

    /**
     * 导出version_controller列表
     */
    @PreAuthorize("@ss.hasPermi('version:version:export')")
    @Log(title = "version_controller", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RfAppVersion rfAppVersion)
    {
        List<RfAppVersion> list = rfAppVersionService.selectRfAppVersionList(rfAppVersion);
        ExcelUtil<RfAppVersion> util = new ExcelUtil<RfAppVersion>(RfAppVersion.class);
        util.exportExcel(response, list, "version_controller数据");
    }

    /**
     * 获取version_controller详细信息
     */
    @PreAuthorize("@ss.hasPermi('version:version:query')")
    @GetMapping(value = "/{versionId}")
    public AjaxResult getInfo(@PathVariable("versionId") Long versionId)
    {
        return success(rfAppVersionService.selectRfAppVersionByVersionId(versionId));
    }

    /**
     * 新增version_controller
     */
    @PreAuthorize("@ss.hasPermi('version:version:add')")
    @Log(title = "version_controller", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RfAppVersion rfAppVersion)
    {
        return toAjax(rfAppVersionService.insertRfAppVersion(rfAppVersion));
    }

    /**
     * 修改version_controller
     */
    @PreAuthorize("@ss.hasPermi('version:version:edit')")
    @Log(title = "version_controller", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RfAppVersion rfAppVersion)
    {
        return toAjax(rfAppVersionService.updateRfAppVersion(rfAppVersion));
    }

    /**
     * 删除version_controller
     */
    @PreAuthorize("@ss.hasPermi('version:version:remove')")
    @Log(title = "version_controller", businessType = BusinessType.DELETE)
	@DeleteMapping("/{versionIds}")
    public AjaxResult remove(@PathVariable Long[] versionIds)
    {
        return toAjax(rfAppVersionService.deleteRfAppVersionByVersionIds(versionIds));
    }
}
