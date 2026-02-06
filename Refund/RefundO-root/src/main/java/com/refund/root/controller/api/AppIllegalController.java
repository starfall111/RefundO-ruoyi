package com.refund.root.controller.api;

import com.refund.common.core.domain.Result;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.root.domain.RfIllegalRecords;
import com.refund.root.service.IRfIllegalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APP端违规记录控制器
 */
@RestController
@RequestMapping("/api/illegal")
public class AppIllegalController {

    @Autowired
    private IRfIllegalRecordsService rfIllegalRecordsService;

    /**
     * 获取当前用户的违规记录
     *
     * @return 违规记录列表
     */
    @GetMapping("/list")
    public Result<List<RfIllegalRecords>> getIllegalRecords() {
        // 获取当前用户ID
        Long userId = ApiSecurityUtils.getUserId();

        // 查询条件
        RfIllegalRecords query = new RfIllegalRecords();
        query.setUserId(userId);

        List<RfIllegalRecords> illegalRecords = rfIllegalRecordsService.selectRfIllegalRecordsList(query);
        return Result.success(illegalRecords);
    }
}
