package com.refund.root.service.impl;

import java.util.List;
import com.refund.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RfFaqCategoriesMapper;
import com.refund.root.domain.RfFaqCategories;
import com.refund.root.service.IRfFaqCategoriesService;

/**
 * FAQ分类Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
@Service
public class RfFaqCategoriesServiceImpl implements IRfFaqCategoriesService 
{
    @Autowired
    private RfFaqCategoriesMapper rfFaqCategoriesMapper;

    /**
     * 查询FAQ分类
     * 
     * @param id FAQ分类主键
     * @return FAQ分类
     */
    @Override
    public RfFaqCategories selectRfFaqCategoriesById(Long id)
    {
        return rfFaqCategoriesMapper.selectRfFaqCategoriesById(id);
    }

    /**
     * 查询FAQ分类列表
     * 
     * @param rfFaqCategories FAQ分类
     * @return FAQ分类
     */
    @Override
    public List<RfFaqCategories> selectRfFaqCategoriesList(RfFaqCategories rfFaqCategories)
    {
        return rfFaqCategoriesMapper.selectRfFaqCategoriesList(rfFaqCategories);
    }

    /**
     * 新增FAQ分类
     * 
     * @param rfFaqCategories FAQ分类
     * @return 结果
     */
    @Override
    public int insertRfFaqCategories(RfFaqCategories rfFaqCategories)
    {
        rfFaqCategories.setCreateTime(DateUtils.getNowDate());
        return rfFaqCategoriesMapper.insertRfFaqCategories(rfFaqCategories);
    }

    /**
     * 修改FAQ分类
     * 
     * @param rfFaqCategories FAQ分类
     * @return 结果
     */
    @Override
    public int updateRfFaqCategories(RfFaqCategories rfFaqCategories)
    {
        rfFaqCategories.setUpdateTime(DateUtils.getNowDate());
        return rfFaqCategoriesMapper.updateRfFaqCategories(rfFaqCategories);
    }

    /**
     * 批量删除FAQ分类
     * 
     * @param ids 需要删除的FAQ分类主键
     * @return 结果
     */
    @Override
    public int deleteRfFaqCategoriesByIds(Long[] ids)
    {
        return rfFaqCategoriesMapper.deleteRfFaqCategoriesByIds(ids);
    }

    /**
     * 删除FAQ分类信息
     * 
     * @param id FAQ分类主键
     * @return 结果
     */
    @Override
    public int deleteRfFaqCategoriesById(Long id)
    {
        return rfFaqCategoriesMapper.deleteRfFaqCategoriesById(id);
    }
}
