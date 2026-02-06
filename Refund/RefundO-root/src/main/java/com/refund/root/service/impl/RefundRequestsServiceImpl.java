package com.refund.root.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.refund.common.core.page.PageResult;
import com.refund.common.core.domain.dto.PageQueryDTO;
import com.refund.common.core.domain.dto.RefundRequestDTO;
import com.refund.common.core.domain.model.LoginUser;
import com.refund.common.core.domain.vo.TransactionVO;
import com.refund.common.exception.business.MessageKeys;
import com.refund.common.exception.business.RefundRequestStatusException;
import com.refund.common.exception.business.ResourceNotFoundException;
import com.refund.common.service.IMailService;
import com.refund.common.utils.DateUtils;
import com.refund.common.utils.SecurityUtils;
import com.refund.root.utils.numberGenUtils;
import com.refund.root.domain.Product;
import com.refund.root.domain.RefundTransactions;
import com.refund.root.domain.RfIllegalRecords;
import com.refund.root.domain.RfScanRecords;
import com.refund.root.domain.RfUsers;
import com.refund.root.mapper.RefundTransactionsMapper;
import com.refund.root.mapper.RfProductsMapper;
import com.refund.root.mapper.RfScanRecordsMapper;
import com.refund.root.mapper.RfUsersMapper;
import com.refund.root.service.IRfIllegalRecordsService;
import com.refund.root.service.IRfScanRecordsService;
import com.refund.root.service.IRfUsersService;
import com.refund.root.service.IRefundTransactionsService;
import com.refund.root.utils.numberGenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.refund.root.mapper.RefundRequestsMapper;
import com.refund.root.domain.RefundRequests;
import com.refund.root.service.IRefundRequestsService;

/**
 * 退款申请Service业务层处理
 *
 * @author ruoyi
 * @date 2025-12-22
 */
@Service
public class RefundRequestsServiceImpl implements IRefundRequestsService {

    private static final Logger log = LoggerFactory.getLogger(RefundRequestsServiceImpl.class);

    @Autowired
    private RefundRequestsMapper refundRequestsMapper;

    @Autowired
    private RefundTransactionsMapper refundTransactionsMapper;

    @Autowired
    private IRfScanRecordsService rfScanRecordsService;

    @Autowired
    private numberGenUtils numberGenUtil;

    @Autowired
    private IRfUsersService rfUsersService;

    @Autowired
    private IMailService mailService;

    // ==================== APP端新增依赖 ====================

    @Autowired
    private RfScanRecordsMapper scanRecordsMapper;

    @Autowired
    private RfProductsMapper productsMapper;

    @Autowired
    private RfUsersMapper usersMapper;

    @Autowired
    private IRfIllegalRecordsService illegalRecordsService;

    @Autowired
    private IRefundTransactionsService refundTransactionsService;

    @Autowired
    private numberGenUtils numberGenUtils;

    /**
     * 查询退款申请
     * 
     * @param requestId 退款申请主键
     * @return 退款申请
     */
    @Override
    public RefundRequests selectRefundRequestsByRequestId(Long requestId)
    {
        return refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
    }

    /**
     * 查询退款申请列表
     * 
     * @param refundRequests 退款申请
     * @return 退款申请
     */
    @Override
    public List<RefundRequests> selectRefundRequestsList(RefundRequests refundRequests)
    {
        return refundRequestsMapper.selectRefundRequestsList(refundRequests);
    }

    /**
     * 新增退款申请
     * 
     * @param refundRequests 退款申请
     * @return 结果
     */
    @Override
    public int insertRefundRequests(RefundRequests refundRequests)
    {
        refundRequests.setCreateTime(DateUtils.getNowDate());
        return refundRequestsMapper.insertRefundRequests(refundRequests);
    }

    /**
     * 修改退款申请
     * 
     * @param refundRequests 退款申请
     * @return 结果
     */
    @Override
    public int updateRefundRequests(RefundRequests refundRequests)
    {
        refundRequests.setUpdateTime(DateUtils.getNowDate());
        return refundRequestsMapper.updateRefundRequests(refundRequests);
    }

    /**
     * 批量删除退款申请
     * 
     * @param requestIds 需要删除的退款申请主键
     * @return 结果
     */
    @Override
    public int deleteRefundRequestsByRequestIds(Long[] requestIds)
    {
        return refundRequestsMapper.deleteRefundRequestsByRequestIds(requestIds);
    }

    /**
     * 删除退款申请信息
     * 
     * @param requestId 退款申请主键
     * @return 结果
     */
    @Override
    public int deleteRefundRequestsByRequestId(Long requestId)
    {
        return refundRequestsMapper.deleteRefundRequestsByRequestId(requestId);
    }

    /**
     * 审批退款申请
     * @param requestIds 退款申请主键
     * @param status 状态
     * @return 结果
     */
//    TODO 在添加退款交易记录时，先检查表中是否已经存在requestId相同的记录，如果存在则不添加，如果不存在则添加
    @Override
    public int updateRefundRequestsStatus(Long[] requestIds, Long status,String rejectReason) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();

        //判断请求状态是否合法
        if (status == 1 || status == 2) {
            for (Long requestId : requestIds) {
                RefundRequests refundRequests = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
                if (refundRequests.getRequestStatus() != 0) {
                    return -1;
                }
            }
        }

        //当审批拒绝或交易失败时，修改扫描记录状态为0并返还余额
        if(status == 2 || status == 5){
            for(Long requestId : requestIds){
                RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);

                // 根据目标状态确定允许的前置状态
                List<Long> allowedStatuses = new ArrayList<>();
                if(status == 2) {
                    // 审批拒绝：前置状态必须是 0（待审批）
                    allowedStatuses.add(0L);
                } else{
                    // 交易失败：前置状态必须是 3（退款中）
                    allowedStatuses.add(3L);
                }

                // 先原子化返还余额（状态校验确保幂等性）
                boolean balanceReturned = rfUsersService.increaseBalanceWithRequestCheck(
                        request.getUserId(),
                        request.getAmount(),
                        requestId,
                        allowedStatuses
                );

                if (!balanceReturned) {
                    // 余额返还失败（状态已变更或其他原因），记录日志
                    System.err.println("余额返还失败，请求状态可能已变更, requestId: " + requestId + ", userId: " + request.getUserId() + ", targetStatus: " + status);
                }

                // 修改扫描记录状态为0
                RfScanRecords rfScanRecords = new RfScanRecords();
                String ScanIds = request.getScanId();
                String[] ScanIdArray = ScanIds.split(",");
//                将扫描记录ID转为Long类型
                Long[] scanIdArray = new Long[ScanIdArray.length];
                for(int i = 0; i < ScanIdArray.length; i++){
                    scanIdArray[i] = Long.parseLong(ScanIdArray[i]);
                    rfScanRecords.setScanId(scanIdArray[i]);
                    rfScanRecords.setRefundStatus(0);
                    rfScanRecordsService.updateRfScanRecords(rfScanRecords);
                }
            }
        }


        //先修改请求状态
        int result = refundRequestsMapper.updateRefundRequestsStatus(requestIds, status,rejectReason,userId);

        if(status == 1){
            // 不再立即创建交易记录，由定时任务处理

            // 发送审批通过邮件
            for(Long requestId : requestIds){
                RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
                RfUsers user = rfUsersService.selectUsersByUserId(request.getUserId());
                if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
                    mailService.sendRefundApprovedEmail(user.getEmail(), user.getUsername(),
                            request.getRequestNumber(), request.getAmount());
                }
            }

            return result;
        }

        // 状态2：审批拒绝，发送拒绝邮件
        if(status == 2){
            for(Long requestId : requestIds){
                RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
                RfUsers user = rfUsersService.selectUsersByUserId(request.getUserId());
                if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
                    mailService.sendRefundRejectedEmail(user.getEmail(), user.getUsername(),
                            request.getRequestNumber(), rejectReason);
                }
            }
        }

        // 状态5：交易失败，发送交易失败邮件
        if(status == 5){
            for(Long requestId : requestIds){
                RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
                RfUsers user = rfUsersService.selectUsersByUserId(request.getUserId());
                if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()){
                    mailService.sendTransactionFailedEmail(user.getEmail(), user.getUsername(),
                            request.getRequestNumber(), rejectReason);
                }
            }
        }

        return result;
    }

    // ==================== APP端专用方法实现 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRefundRequest(RefundRequestDTO dto, Long userId) {
        try {
            // 1. 解析扫描ID列表
            List<Long> scanIds = parseScanIds(dto.getScanIds());
            BigDecimal totalAmount = BigDecimal.ZERO;

            // 2. 遍历验证每个扫描记录
            for (Long scanId : scanIds) {
                RfScanRecords scan = scanRecordsMapper.selectRfScanRecordsByScanId(scanId);
                if (scan == null) {
                    // 记录违规行为
                    recordIllegalActivity("申请退款时发现扫描记录不存在: " + scanId, userId);
                    throw new RefundRequestStatusException(MessageKeys.REFUND_SCAN_NOT_EXIST);
                }

                // 3. 原子性检查并更新退款状态
                int updated = scanRecordsMapper.updateRefundStatusIfNotApplied(scanId);
                if (updated == 0) {
                    // 记录违规行为
                    recordIllegalActivity("申请退款时发现扫描记录已申请退款: " + scanId, userId);
                    throw new RefundRequestStatusException(MessageKeys.REFUND_SCAN_ALREADY_APPLIED);
                }

                // 4. 检查日期是否满5个月
                LocalDateTime scanTime = scan.getScanTime().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (scanTime.plusMonths(5).isAfter(LocalDateTime.now())) {
                    throw new RefundRequestStatusException(MessageKeys.REFUND_SCAN_NOT_ELIGIBLE);
                }

                // 5. 获取产品信息计算金额
                Product product = productsMapper.selectById(scan.getProductId());
                if (product == null) {
                    throw new RefundRequestStatusException(MessageKeys.PRODUCT_NOT_EXIST);
                }
                totalAmount = totalAmount.add(product.getValue());
            }

            // 6. 检查总金额是否≥5000
            if (totalAmount.compareTo(new BigDecimal("5000")) < 0) {
                throw new RefundRequestStatusException(MessageKeys.REFUND_AMOUNT_INSUFFICIENT);
            }

            // 7. 生成请求编号
            String requestNumber = numberGenUtils.genRequestNumber();

            // 8. 创建退款请求
            RefundRequests request = new RefundRequests();
            request.setRequestNumber(requestNumber);
            request.setUserId(userId);
            request.setScanId(dto.getScanIds());
            request.setAmount(totalAmount);
            request.setVoucherUrl(dto.getVoucherUrl());
            request.setRequestStatus(0L); // 待审核
            request.setPaymentMethod(dto.getPaymentMethod());
            request.setPaymentNumber(dto.getPaymentNumber());
            request.setCreateTime(new Date());
            refundRequestsMapper.insertRefundRequests(request);

            // 9. 原子性扣减用户余额
            int balanceUpdated = usersMapper.subtractBalance(userId, totalAmount);
            if (balanceUpdated == 0) {
                throw new RefundRequestStatusException("余额不足");
            }

            log.info("退款请求创建成功: userId={}, requestNumber={}, amount={}",
                    userId, requestNumber, totalAmount);
        } catch (RefundRequestStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建退款请求时发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("创建退款请求失败", e);
        }
    }

    @Override
    public BigDecimal calculateTotalAmount(String scanIdsStr) {
        // 解析扫描ID列表
        List<Long> scanIds = parseScanIds(scanIdsStr);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Long scanId : scanIds) {
            RfScanRecords scan = scanRecordsMapper.selectRfScanRecordsByScanId(scanId);
            if (scan != null) {
                Product product = productsMapper.selectById(scan.getProductId());
                if (product != null) {
                    totalAmount = totalAmount.add(product.getValue());
                }
            }
        }

        return totalAmount;
    }

    @Override
    public PageResult<RefundRequests> getRequestsByCurrentUserPage(Long userId, PageQueryDTO pageQuery) {
        // 构建排序字段和方向
        String orderBy = buildOrderBy(pageQuery);

        // 设置分页参数和排序
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), orderBy);

        RefundRequests queryParam = new RefundRequests();
        queryParam.setUserId(userId);
        List<RefundRequests> requests = refundRequestsMapper.selectRefundRequestsList(queryParam);
        PageInfo<RefundRequests> pageInfo = new PageInfo<>(requests);

        return PageResult.of(requests, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    @Override
    public TransactionVO getByRequestId(Long requestId) {
        RefundRequests request = refundRequestsMapper.selectRefundRequestsByRequestId(requestId);
        if (request == null) {
            throw new ResourceNotFoundException(MessageKeys.REFUND_SCAN_NOT_EXIST);
        }

        // 查询关联的交易记录
        TransactionVO vo = refundTransactionsMapper.selectByRequestId(requestId);

        return vo;
    }

    @Override
    public RefundRequests getByRequestNumber(String requestNumber) {
        RefundRequests queryParam = new RefundRequests();
        queryParam.setRequestNumber(requestNumber);
        List<RefundRequests> list = refundRequestsMapper.selectRefundRequestsList(queryParam);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 解析扫描ID字符串
     *
     * @param scanIdsStr 扫描ID字符串（逗号分隔）
     * @return 扫描ID列表
     */
    private List<Long> parseScanIds(String scanIdsStr) {
        if (scanIdsStr == null || scanIdsStr.trim().isEmpty()) {
            throw new RefundRequestStatusException("扫描ID不能为空");
        }
        return Arrays.stream(scanIdsStr.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .toList();
    }

    /**
     * 构建排序字段和方向
     *
     * @param pageQuery 分页查询参数
     * @return 排序字符串
     */
    private String buildOrderBy(PageQueryDTO pageQuery) {
        String column = "amount".equals(pageQuery.getOrderBy()) ? "amount" : "create_time";
        String direction = "desc".equalsIgnoreCase(pageQuery.getOrderDirection()) ? "desc" : "asc";
        return column + " " + direction;
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
}
