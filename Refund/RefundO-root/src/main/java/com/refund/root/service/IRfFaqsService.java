package com.refund.root.service;

import java.util.List;
import com.refund.root.domain.RfFaqs;

/**
 * FAQ内容Service接口
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
public interface IRfFaqsService 
{
    /**
     * 查询FAQ内容
     * 
     * @param id FAQ内容主键
     * @return FAQ内容
     */
    public RfFaqs selectRfFaqsById(Long id);

    /**
     * 查询FAQ内容列表
     * 
     * @param rfFaqs FAQ内容
     * @return FAQ内容集合
     */
    public List<RfFaqs> selectRfFaqsList(RfFaqs rfFaqs);

    /**
     * 新增FAQ内容
     * 
     * @param rfFaqs FAQ内容
     * @return 结果
     */
    public int insertRfFaqs(RfFaqs rfFaqs);

    /**
     * 修改FAQ内容
     * 
     * @param rfFaqs FAQ内容
     * @return 结果
     */
    public int updateRfFaqs(RfFaqs rfFaqs);

    /**
     * 批量删除FAQ内容
     * 
     * @param ids 需要删除的FAQ内容主键集合
     * @return 结果
     */
    public int deleteRfFaqsByIds(Long[] ids);

    /**
     * 删除FAQ内容信息
     * 
     * @param id FAQ内容主键
     * @return 结果
     */
    public int deleteRfFaqsById(Long id);
}
