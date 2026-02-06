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
        RfAppVersion query = new RfAppVersion();
        List<RfAppVersion> versionList = rfAppVersionService.selectRfAppVersionList(query);

        // 获取最新版本（假设版本ID越大越新）
        RfAppVersion latestVersion = null;
        if (versionList != null && !versionList.isEmpty()) {
            // 找到版本ID最大的记录
            for (RfAppVersion version : versionList) {
                if (latestVersion == null ||
                    (version.getVersionId() != null &&
                     latestVersion.getVersionId() != null &&
                     version.getVersionId() > latestVersion.getVersionId())) {
                    latestVersion = version;
                }
            }
        }

        return Result.success(latestVersion);
    }
}
