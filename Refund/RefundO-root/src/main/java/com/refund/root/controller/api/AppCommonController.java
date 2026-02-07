package com.refund.root.controller.api;

import com.refund.common.core.domain.Result;
import com.refund.common.utils.Aliyun.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/common")
public class AppCommonController {
    @Autowired
    private OssUtil aliOssUtil;
    @PostMapping("/upload")
    public Result<String> upadload(MultipartFile file) throws Exception {
        String url = aliOssUtil.upload(file.getBytes(),file.getOriginalFilename());
        return Result.success(url);
    }

}
