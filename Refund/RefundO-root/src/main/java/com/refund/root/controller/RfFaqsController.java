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
import com.refund.root.domain.RfFaqs;
import com.refund.root.service.IRfFaqsService;
import com.refund.common.utils.poi.ExcelUtil;
import com.refund.common.core.page.TableDataInfo;

/**
 * FAQ内容Controller
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
@RestController
@RequestMapping("/faq/faqs")
public class RfFaqsController extends BaseController
{
    @Autowired
    private IRfFaqsService rfFaqsService;

    /**
     * 查询FAQ内容列表
     */
    @PreAuthorize("@ss.hasPermi('faq:faqs:list')")
    @GetMapping("/list")
    public TableDataInfo list(RfFaqs rfFaqs)
    {
        startPage();
        List<RfFaqs> list = rfFaqsService.selectRfFaqsList(rfFaqs);
        return getDataTable(list);
    }

    /**
     * 导出FAQ内容列表
     */
    @PreAuthorize("@ss.hasPermi('faq:faqs:export')")
    @Log(title = "FAQ内容", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RfFaqs rfFaqs)
    {
        List<RfFaqs> list = rfFaqsService.selectRfFaqsList(rfFaqs);
        ExcelUtil<RfFaqs> util = new ExcelUtil<RfFaqs>(RfFaqs.class);
        util.exportExcel(response, list, "FAQ内容数据");
    }

    /**
     * 获取FAQ内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('faq:faqs:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(rfFaqsService.selectRfFaqsById(id));
    }

    /**
     * 新增FAQ内容
     */
    @PreAuthorize("@ss.hasPermi('faq:faqs:add')")
    @Log(title = "FAQ内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RfFaqs rfFaqs)
    {
        return toAjax(rfFaqsService.insertRfFaqs(rfFaqs));
    }

    /**
     * 修改FAQ内容
     */
    @PreAuthorize("@ss.hasPermi('faq:faqs:edit')")
    @Log(title = "FAQ内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RfFaqs rfFaqs)
    {
        return toAjax(rfFaqsService.updateRfFaqs(rfFaqs));
    }

    /**
     * 删除FAQ内容
     */
    @PreAuthorize("@ss.hasPermi('faq:faqs:remove')")
    @Log(title = "FAQ内容", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(rfFaqsService.deleteRfFaqsByIds(ids));
    }
}
