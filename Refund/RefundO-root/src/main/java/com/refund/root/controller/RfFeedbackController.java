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
import com.refund.root.domain.RfFeedback;
import com.refund.root.service.IRfFeedbackService;
import com.refund.common.utils.poi.ExcelUtil;
import com.refund.common.core.page.TableDataInfo;

/**
 * 用户反馈Controller
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
@RestController
@RequestMapping("/feedback/feedback")
public class RfFeedbackController extends BaseController
{
    @Autowired
    private IRfFeedbackService rfFeedbackService;

    /**
     * 查询用户反馈列表
     */
    @PreAuthorize("@ss.hasPermi('feedback:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(RfFeedback rfFeedback)
    {
        startPage();
        List<RfFeedback> list = rfFeedbackService.selectRfFeedbackList(rfFeedback);
        return getDataTable(list);
    }

    /**
     * 导出用户反馈列表
     */
    @PreAuthorize("@ss.hasPermi('feedback:feedback:export')")
    @Log(title = "用户反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RfFeedback rfFeedback)
    {
        List<RfFeedback> list = rfFeedbackService.selectRfFeedbackList(rfFeedback);
        ExcelUtil<RfFeedback> util = new ExcelUtil<RfFeedback>(RfFeedback.class);
        util.exportExcel(response, list, "用户反馈数据");
    }

    /**
     * 获取用户反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('feedback:feedback:query')")
    @GetMapping(value = "/{feedbackId}")
    public AjaxResult getInfo(@PathVariable("feedbackId") Long feedbackId)
    {
        return success(rfFeedbackService.selectRfFeedbackByFeedbackId(feedbackId));
    }

    /**
     * 新增用户反馈
     */
    @PreAuthorize("@ss.hasPermi('feedback:feedback:add')")
    @Log(title = "用户反馈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RfFeedback rfFeedback)
    {
        return toAjax(rfFeedbackService.insertRfFeedback(rfFeedback));
    }

    /**
     * 修改用户反馈
     */
    @PreAuthorize("@ss.hasPermi('feedback:feedback:edit')")
    @Log(title = "用户反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RfFeedback rfFeedback)
    {
        return toAjax(rfFeedbackService.updateRfFeedback(rfFeedback));
    }

    /**
     * 删除用户反馈
     */
    @PreAuthorize("@ss.hasPermi('feedback:feedback:remove')")
    @Log(title = "用户反馈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{feedbackIds}")
    public AjaxResult remove(@PathVariable Long[] feedbackIds)
    {
        return toAjax(rfFeedbackService.deleteRfFeedbackByFeedbackIds(feedbackIds));
    }
}
