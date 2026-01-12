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
import com.refund.root.domain.RfFaqCategories;
import com.refund.root.service.IRfFaqCategoriesService;
import com.refund.common.utils.poi.ExcelUtil;

/**
 * FAQ分类Controller
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
@RestController
@RequestMapping("/root/categories")
public class RfFaqCategoriesController extends BaseController
{
    @Autowired
    private IRfFaqCategoriesService rfFaqCategoriesService;

    /**
     * 查询FAQ分类列表
     */
    @PreAuthorize("@ss.hasPermi('root:categories:list')")
    @GetMapping("/list")
    public AjaxResult list(RfFaqCategories rfFaqCategories)
    {
        List<RfFaqCategories> list = rfFaqCategoriesService.selectRfFaqCategoriesList(rfFaqCategories);
        return success(list);
    }

    /**
     * 导出FAQ分类列表
     */
    @PreAuthorize("@ss.hasPermi('root:categories:export')")
    @Log(title = "FAQ分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RfFaqCategories rfFaqCategories)
    {
        List<RfFaqCategories> list = rfFaqCategoriesService.selectRfFaqCategoriesList(rfFaqCategories);
        ExcelUtil<RfFaqCategories> util = new ExcelUtil<RfFaqCategories>(RfFaqCategories.class);
        util.exportExcel(response, list, "FAQ分类数据");
    }

    /**
     * 获取FAQ分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('root:categories:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(rfFaqCategoriesService.selectRfFaqCategoriesById(id));
    }

    /**
     * 新增FAQ分类
     */
    @PreAuthorize("@ss.hasPermi('root:categories:add')")
    @Log(title = "FAQ分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RfFaqCategories rfFaqCategories)
    {
        return toAjax(rfFaqCategoriesService.insertRfFaqCategories(rfFaqCategories));
    }

    /**
     * 修改FAQ分类
     */
    @PreAuthorize("@ss.hasPermi('root:categories:edit')")
    @Log(title = "FAQ分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RfFaqCategories rfFaqCategories)
    {
        return toAjax(rfFaqCategoriesService.updateRfFaqCategories(rfFaqCategories));
    }

    /**
     * 删除FAQ分类
     */
    @PreAuthorize("@ss.hasPermi('root:categories:remove')")
    @Log(title = "FAQ分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(rfFaqCategoriesService.deleteRfFaqCategoriesByIds(ids));
    }
}
