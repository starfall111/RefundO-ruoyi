package com.refund.root.controller.api;

import com.refund.common.core.domain.Result;
import com.refund.root.domain.RfFaqCategories;
import com.refund.root.service.IRfFaqCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APP端FAQ分类控制器
 */
@RestController
@RequestMapping("/api/faq-category")
public class AppFAQCategoryController {

    @Autowired
    private IRfFaqCategoriesService rfFaqCategoriesService;

    /**
     * 获取所有激活的FAQ分类
     *
     * @return FAQ分类列表
     */
    @GetMapping("/all")
    public Result<List<RfFaqCategories>> getAllCategories() {
        RfFaqCategories query = new RfFaqCategories();
        query.setStatus(1L); // 只返回激活状态的分类
        List<RfFaqCategories> categories = rfFaqCategoriesService.selectRfFaqCategoriesList(query);
        return Result.success(categories);
    }

    /**
     * 根据ID获取分类详情
     *
     * @param id 分类ID
     * @return 分类详情
     */
    @GetMapping("/{id}")
    public Result<RfFaqCategories> getCategoryById(@PathVariable Long id) {
        RfFaqCategories category = rfFaqCategoriesService.selectRfFaqCategoriesById(id);
        return Result.success(category);
    }

    /**
     * 根据父分类ID获取子分类列表
     *
     * @param parentId 父分类ID（0表示顶级分类）
     * @return 子分类列表
     */
    @GetMapping("/sub/{parentId}")
    public Result<List<RfFaqCategories>> getSubCategories(@PathVariable Long parentId) {
        RfFaqCategories query = new RfFaqCategories();
        query.setParentId(parentId);
        query.setStatus(1L); // 只返回激活状态的分类
        List<RfFaqCategories> subCategories = rfFaqCategoriesService.selectRfFaqCategoriesList(query);
        return Result.success(subCategories);
    }
}
