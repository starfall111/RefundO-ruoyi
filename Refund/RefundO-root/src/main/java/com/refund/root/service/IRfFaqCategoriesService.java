package com.refund.root.service;

import java.util.List;
import com.refund.root.domain.RfFaqCategories;

/**
 * FAQ分类Service接口
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
public interface IRfFaqCategoriesService 
{
    /**
     * 查询FAQ分类
     * 
     * @param id FAQ分类主键
     * @return FAQ分类
     */
    public RfFaqCategories selectRfFaqCategoriesById(Long id);

    /**
     * 查询FAQ分类列表
     * 
     * @param rfFaqCategories FAQ分类
     * @return FAQ分类集合
     */
    public List<RfFaqCategories> selectRfFaqCategoriesList(RfFaqCategories rfFaqCategories);

    /**
     * 新增FAQ分类
     * 
     * @param rfFaqCategories FAQ分类
     * @return 结果
     */
    public int insertRfFaqCategories(RfFaqCategories rfFaqCategories);

    /**
     * 修改FAQ分类
     * 
     * @param rfFaqCategories FAQ分类
     * @return 结果
     */
    public int updateRfFaqCategories(RfFaqCategories rfFaqCategories);

    /**
     * 批量删除FAQ分类
     * 
     * @param ids 需要删除的FAQ分类主键集合
     * @return 结果
     */
    public int deleteRfFaqCategoriesByIds(Long[] ids);

    /**
     * 删除FAQ分类信息
     * 
     * @param id FAQ分类主键
     * @return 结果
     */
    public int deleteRfFaqCategoriesById(Long id);
}
