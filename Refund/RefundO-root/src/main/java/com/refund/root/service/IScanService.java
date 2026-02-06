package com.refund.root.service;

import com.refund.common.core.page.PageResult;
import com.refund.common.core.domain.dto.PageQueryDTO;
import com.refund.common.core.domain.vo.ScanRecordsVO;
import com.refund.root.domain.Product;

/**
 * APP端扫描服务接口
 *
 * @author refund
 */
public interface IScanService {

    /**
     * 分页查询当前用户的扫描记录
     *
     * @param userId    用户ID
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    PageResult<ScanRecordsVO> getScansByUserIdPage(Long userId, PageQueryDTO pageQuery);

    /**
     * 根据扫描ID获取详细信息（含权限验证）
     *
     * @param scanId 扫描ID
     * @param userId 当前用户ID（用于权限验证）
     * @return 扫描记录详情
     */
    ScanRecordsVO getScanDetailById(Long scanId, Long userId);

    /**
     * 新增扫描记录（含HMAC验证和余额更新）
     *
     * @param product 产品信息（包含HMAC签名）
     * @param userId  当前用户ID
     */
    void addScanRecord(Product product, Long userId);
}
