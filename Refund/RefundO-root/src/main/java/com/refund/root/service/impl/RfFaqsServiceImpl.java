package com.refund.root.service.impl;

import java.util.List;
import com.refund.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RfFaqsMapper;
import com.refund.root.domain.RfFaqs;
import com.refund.root.service.IRfFaqsService;

/**
 * FAQ内容Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-12
 */
@Service
public class RfFaqsServiceImpl implements IRfFaqsService 
{
    @Autowired
    private RfFaqsMapper rfFaqsMapper;

    /**
     * 查询FAQ内容
     * 
     * @param id FAQ内容主键
     * @return FAQ内容
     */
    @Override
    public RfFaqs selectRfFaqsById(Long id)
    {
        return rfFaqsMapper.selectRfFaqsById(id);
    }

    /**
     * 查询FAQ内容列表
     * 
     * @param rfFaqs FAQ内容
     * @return FAQ内容
     */
    @Override
    public List<RfFaqs> selectRfFaqsList(RfFaqs rfFaqs)
    {
        return rfFaqsMapper.selectRfFaqsList(rfFaqs);
    }

    /**
     * 新增FAQ内容
     * 
     * @param rfFaqs FAQ内容
     * @return 结果
     */
    @Override
    public int insertRfFaqs(RfFaqs rfFaqs)
    {
        rfFaqs.setCreateTime(DateUtils.getNowDate());
        return rfFaqsMapper.insertRfFaqs(rfFaqs);
    }

    /**
     * 修改FAQ内容
     * 
     * @param rfFaqs FAQ内容
     * @return 结果
     */
    @Override
    public int updateRfFaqs(RfFaqs rfFaqs)
    {
        rfFaqs.setUpdateTime(DateUtils.getNowDate());
        return rfFaqsMapper.updateRfFaqs(rfFaqs);
    }

    /**
     * 批量删除FAQ内容
     * 
     * @param ids 需要删除的FAQ内容主键
     * @return 结果
     */
    @Override
    public int deleteRfFaqsByIds(Long[] ids)
    {
        return rfFaqsMapper.deleteRfFaqsByIds(ids);
    }

    /**
     * 删除FAQ内容信息
     * 
     * @param id FAQ内容主键
     * @return 结果
     */
    @Override
    public int deleteRfFaqsById(Long id)
    {
        return rfFaqsMapper.deleteRfFaqsById(id);
    }
}
