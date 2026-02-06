package com.refund.root.controller.api;

import com.refund.common.annotation.RateLimit;
import com.refund.common.core.page.PageResult;
import com.refund.common.core.domain.dto.PageQueryDTO;
import com.refund.common.core.domain.Result;
import com.refund.common.core.domain.vo.ScanRecordsVO;
import com.refund.common.enums.LimitType;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.root.domain.Product;
import com.refund.root.service.IScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * APP端扫描控制器
 */
@RestController
@RequestMapping("/api/scan")
public class AppScanController {

    @Autowired
    private IScanService scanService;

    /**
     * 分页获取当前用户的扫描记录
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    @GetMapping("/records")
    public Result<PageResult<ScanRecordsVO>> getScansByPage(PageQueryDTO pageQuery) {
        Long userId = ApiSecurityUtils.getUserId();
        PageResult<ScanRecordsVO> result = scanService.getScansByUserIdPage(userId, pageQuery);
        return Result.success(result);
    }

    /**
     * 根据扫描ID获取详细信息
     */
    @GetMapping("/{scanId}")
    public Result<ScanRecordsVO> getScanDetail(@PathVariable Long scanId) {
        Long userId = ApiSecurityUtils.getUserId();
        ScanRecordsVO scanDetail = scanService.getScanDetailById(scanId, userId);
        return Result.success(scanDetail);
    }

    /**
     * 新增扫描记录
     */
    @PostMapping("/insert")
    @RateLimit(time = 60, count = 10, limitType = LimitType.USER, key = "scan")
    public Result<Void> addScanRecord(@RequestBody Product product) {
        Long userId = ApiSecurityUtils.getUserId();
        scanService.addScanRecord(product, userId);
        return Result.success();
    }
}
