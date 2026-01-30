package com.refund.common.utils.file;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.refund.common.config.RuoYiConfig;
import com.refund.common.utils.uuid.IdUtils;

/**
 * 统一文件上传服务
 * 根据文件类型自动选择上传方式：
 * - APK文件：本地上传，返回完整URL
 * - 其他文件：OSS上传
 *
 * @author ruoyi
 */
@Service
public class CompositeFileUploadService
{
    @Autowired
    private OssFileUploadService ossFileUploadService;

    /**
     * APK域名配置
     */
    @Value("${apk.domain:https://example.com}")
    private String apkDomain;

    /**
     * APK URL路径前缀
     */
    private static final String APK_URL_PREFIX = "/apk/";

    /**
     * 上传文件（自动判断上传方式）
     *
     * @param file 上传的文件
     * @return 返回上传成功的文件URL
     * @throws Exception
     */
    public String upload(MultipartFile file) throws Exception
    {
        return upload(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }

    /**
     * 上传文件（自动判断上传方式）
     *
     * @param file 上传的文件
     * @param allowedExtension 允许的文件扩展名
     * @return 返回上传成功的文件URL
     * @throws Exception
     */
    public String upload(MultipartFile file, String[] allowedExtension) throws Exception
    {
        String extension = FileUploadUtils.getExtension(file);

        // 判断是否为APK文件
        if ("apk".equalsIgnoreCase(extension))
        {
            return uploadApkToLocal(file, allowedExtension);
        }
        else
        {
            // 其他文件使用OSS上传
            return ossFileUploadService.upload(file, allowedExtension);
        }
    }

    /**
     * APK文件本地上传
     * 直接存放在profile目录下，不按时间划分
     * 返回格式：https://example.com/apk/xxx.apk
     *
     * @param file 上传的文件
     * @param allowedExtension 允许的文件扩展名
     * @return 返回完整的APK访问URL
     * @throws Exception
     */
    private String uploadApkToLocal(MultipartFile file, String[] allowedExtension) throws Exception
    {
        // 文件校验
        FileUploadUtils.assertAllowed(file, allowedExtension);

        // 生成文件名：UUID.apk
        String fileName = IdUtils.fastSimpleUUID() + ".apk";

        // 获取上传目录
        String uploadDir = RuoYiConfig.getProfile();

        // 创建文件对象
        File dest = new File(uploadDir, fileName);
        if (!dest.getParentFile().exists())
        {
            dest.getParentFile().mkdirs();
        }

        // 保存文件
        file.transferTo(Paths.get(dest.getAbsolutePath()));

        // 返回完整URL
        return buildApkUrl(fileName);
    }

    /**
     * 构建APK访问URL
     *
     * @param fileName 文件名
     * @return 完整URL
     */
    private String buildApkUrl(String fileName)
    {
        // 确保域名没有尾部斜杠
        String domain = apkDomain.endsWith("/") ? apkDomain.substring(0, apkDomain.length() - 1) : apkDomain;

        return domain + APK_URL_PREFIX + fileName;
    }
}
