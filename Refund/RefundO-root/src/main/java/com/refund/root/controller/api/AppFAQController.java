package com.refund.root.controller.api;

import com.refund.common.core.domain.Result;
import com.refund.root.domain.RfFaqs;
import com.refund.root.service.IRfFaqsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * APP端FAQ控制器
 */
@RestController
@RequestMapping("/api/faq")
public class AppFAQController {

    @Autowired
    private IRfFaqsService rfFaqsService;

    /**
     * 获取所有激活的FAQ
     *
     * @return FAQ列表
     */
    @GetMapping("/all")
    public Result<List<RfFaqs>> getAllFAQs() {
        RfFaqs query = new RfFaqs();
        query.setStatus(1L); // 只返回激活状态的FAQ
        List<RfFaqs> faqs = rfFaqsService.selectRfFaqsList(query);
        return Result.success(faqs);
    }

    /**
     * 根据分类ID获取FAQ列表
     *
     * @param categoryId 分类ID
     * @return FAQ列表
     */
    @GetMapping("/category/{categoryId}")
    public Result<List<RfFaqs>> getFAQsByCategory(@PathVariable Long categoryId) {
        RfFaqs query = new RfFaqs();
        query.setCategoryId(categoryId);
        query.setStatus(1L); // 只返回激活状态的FAQ
        List<RfFaqs> faqs = rfFaqsService.selectRfFaqsList(query);
        return Result.success(faqs);
    }

    /**
     * 根据ID获取FAQ详情
     *
     * @param id FAQ ID
     * @return FAQ详情
     */
    @GetMapping("/{id}")
    public Result<RfFaqs> getFAQById(@PathVariable Long id) {
        RfFaqs faq = rfFaqsService.selectRfFaqsById(id);

        // 增加浏览次数
        if (faq != null) {
            Long viewCount = faq.getViewCount();
            faq.setViewCount(viewCount == null ? 1L : viewCount + 1);
            rfFaqsService.updateRfFaqs(faq);
        }

        return Result.success(faq);
    }
}
