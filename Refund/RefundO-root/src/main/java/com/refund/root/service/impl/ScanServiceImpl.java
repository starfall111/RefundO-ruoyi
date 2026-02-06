package com.refund.root.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.refund.common.core.page.PageResult;
import com.refund.common.core.domain.dto.PageQueryDTO;
import com.refund.common.core.domain.vo.ScanRecordsVO;
import com.refund.common.exception.business.ProductInvalidException;
import com.refund.common.exception.business.ProductRepetitionScanException;
import com.refund.common.exception.business.MessageKeys;
import com.refund.common.exception.business.ResourceNotFoundException;
import com.refund.common.utils.sign.HmacUtil;
import com.refund.root.utils.numberGenUtils;
import com.refund.root.domain.Product;
import com.refund.root.domain.RfIllegalRecords;
import com.refund.root.domain.RfScanRecords;
import com.refund.root.mapper.RfProductsMapper;
import com.refund.root.mapper.RfScanRecordsMapper;
import com.refund.root.mapper.RfUsersMapper;
import com.refund.root.service.IRfIllegalRecordsService;
import com.refund.root.service.IScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * APP端扫描服务实现
 *
 * @author refund
 */
@Service
public class ScanServiceImpl implements IScanService {

    private static final Logger log = LoggerFactory.getLogger(ScanServiceImpl.class);

    @Autowired
    private RfScanRecordsMapper scanRecordsMapper;

    @Autowired
    private RfProductsMapper productsMapper;

    @Autowired
    private RfUsersMapper usersMapper;

    @Autowired
    private IRfIllegalRecordsService illegalRecordsService;

    @Autowired
    private numberGenUtils numberGenUtils;

    @Override
    public PageResult<ScanRecordsVO> getScansByUserIdPage(Long userId, PageQueryDTO pageQuery) {
        // 构建排序字段和方向
        String orderBy = "amount".equals(pageQuery.getOrderBy()) ? "amount" : "create_time";
        String orderDirection = "desc".equalsIgnoreCase(pageQuery.getOrderDirection()) ? "desc" : "asc";

        // 设置分页参数
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());

        // 查询数据
        List<ScanRecordsVO> scans = scanRecordsMapper.findByUserIdWithProduct(userId, orderBy, orderDirection);
        PageInfo<ScanRecordsVO> pageInfo = new PageInfo<>(scans);

        return PageResult.of(scans, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    @Override
    public ScanRecordsVO getScanDetailById(Long scanId, Long userId) {
        RfScanRecords scan = scanRecordsMapper.selectRfScanRecordsByScanId(scanId);
        if (scan == null) {
            throw new ResourceNotFoundException(MessageKeys.REFUND_SCAN_NOT_EXIST);
        }

        // 权限验证：检查扫描记录是否属于当前用户
        if (!scan.getUserId().equals(userId)) {
            throw new ResourceNotFoundException(MessageKeys.REFUND_SCAN_NOT_EXIST);
        }

        // 获取产品信息
        Product product = productsMapper.selectById(scan.getProductId());
        ScanRecordsVO vo = new ScanRecordsVO();
        vo.setScanId(scan.getScanId());
        vo.setScanNumber(scan.getScanNumber());
        vo.setUserId(scan.getUserId());
        vo.setProductId(scan.getProductId());
        vo.setScanTime(scan.getScanTime() != null ?
                scan.getScanTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);
        vo.setRefundStatus(scan.getRefundStatus());

        if (product != null) {
            vo.setRefundRatio(product.getRefundRatio() != null ?
                    BigDecimal.valueOf(product.getRefundRatio()) : null);
            vo.setValue(product.getValue());
            vo.setOriginalPrice(product.getOriginalPrice());
        }

        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addScanRecord(Product product, Long userId) {
        // 1. 序列化产品信息用于HMAC验证
        String message = serializeProduct(product);

        // 2. HMAC签名验证
        if (!HmacUtil.verifyHmac(message, product.getHash())) {
            // 记录违规行为
            recordIllegalActivity("产品验证失败: " + product.getHash(), userId);
            throw new ProductInvalidException(MessageKeys.PRODUCT_INVALID);
        }

        // 3. 检查产品是否已存在（通过Hash）
        Long existingProductId = productsMapper.findIdByHash(product.getHash());
        if (existingProductId != null) {
            throw new ProductRepetitionScanException(MessageKeys.PRODUCT_REPETITION_SCAN);
        }

        // 4. 插入产品信息
        // 将Double类型的refundRatio转换为BigDecimal
        if (product.getRefundRatio() != null) {
            product.setRefundRatio(Double.parseDouble(
                    String.format("%.4f", product.getRefundRatio())));
        }
        productsMapper.insert(product);

        // 5. 生成扫描编号
        String scanNumber = numberGenUtils.genScanNumber();
        RfScanRecords scan = new RfScanRecords();
        scan.setScanNumber(scanNumber);
        scan.setUserId(userId);
        scan.setProductId(product.getProductId());
        scan.setScanTime(new Date());
        scan.setRefundStatus(0); // 默认为未退款

        // 6. 插入扫描记录
        scanRecordsMapper.insertRfScanRecords(scan);

        // 7. 原子性更新用户余额（增加产品价值）
        usersMapper.addBalance(userId, product.getValue());

        log.info("扫描记录添加成功: userId={}, scanNumber={}, productId={}",
                userId, scanNumber, product.getProductId());
    }

    /**
     * 记录违规行为
     *
     * @param description 违规描述
     * @param userId      用户ID
     */
    private void recordIllegalActivity(String description, Long userId) {
        try {
            RfIllegalRecords illegal = new RfIllegalRecords();
            illegal.setUserId(userId);
            illegal.setReason(description);
            illegal.setScanTime(new Date());
            illegalRecordsService.insertRfIllegalRecords(illegal);
            log.warn("记录违规行为: userId={}, description={}", userId, description);
        } catch (Exception e) {
            log.error("记录违规行为失败: userId={}, description={}", userId, description, e);
        }
    }

    /**
     * 序列化产品信息用于HMAC验证
     * 格式: originalPrice|productId|value|refundRatio
     *
     * @param product 产品信息
     * @return 序列化后的字符串
     */
    private String serializeProduct(Product product) {
        return String.format("%.2f|%d|%.2f|%.2f",
                product.getOriginalPrice(),
                product.getProductId() != null ? product.getProductId() : 0,
                product.getValue(),
                product.getRefundRatio() != null ? product.getRefundRatio() : 0.0);
    }
}
