package com.refund.common.utils.file;

import com.refund.common.utils.Aliyun.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * OSS文件上传服务
 * 
 * @author ruoyi
 */
@Service
public class OssFileUploadService
{
    @Autowired
    private OssUtil ossUtil;

    /**
     * 上传文件到OSS
     * 
     * @param file 上传的文件
     * @param allowedExtension 允许的文件扩展名
     * @return 返回上传成功的文件URL
     * @throws Exception
     */
    public String upload(MultipartFile file, String[] allowedExtension) throws Exception
    {
        // 文件校验
        FileUploadUtils.assertAllowed(file, allowedExtension);
        
        // 上传到OSS
        return ossUtil.uploadMultipartFile(file);
    }
    
    /**
     * 上传文件到OSS（使用默认允许的扩展名）
     * 
     * @param file 上传的文件
     * @return 返回上传成功的文件URL
     * @throws Exception
     */
    public String upload(MultipartFile file) throws Exception
    {
        return upload(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }
}