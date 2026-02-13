package com.refund.root.controller.api;

import com.refund.common.core.domain.Result;
import com.refund.root.domain.RfAppVersion;
import com.refund.root.service.IRfAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APP端版本更新控制器
 */
@RestController
@RequestMapping("/api/version")
public class AppVersionController {

    @Autowired
    private IRfAppVersionService rfAppVersionService;

    /**
     * 获取APP版本信息
     * 返回最新的版本信息，用于APP端检查更新
     *
     * @return 版本信息
     */
    @GetMapping("/get")
    public Result<RfAppVersion> getVersion() {
        // 查询所有版本，取最新的（假设按版本ID降序，第一个即为最新版本）
        RfAppVersion version = rfAppVersionService.update();

        return Result.success(version);
    }
}
