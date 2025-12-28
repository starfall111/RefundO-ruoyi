package com.refund.framework.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.refund.common.config.RuoYiConfig;
import com.refund.common.utils.ServletUtils;

/**
 * 服务配置
 * 
 * @author ruoyi
 */
@Component
public class ServerConfig
{
    @Value("${aliyun.oss.endpoint}")
    private String ossEndpoint;
    
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     * 
     * @return 服务地址
     */
    public String getUrl()
    {
        // 如果使用OSS，则返回OSS的完整URL
        if (RuoYiConfig.isUseOss()) {
            // 确保endpoint不包含协议部分，我们构建完整的OSS URL
            String cleanEndpoint = ossEndpoint.replace("https://", "").replace("http://", "");
            return "https://" + cleanEndpoint + "/" + bucketName;
        }
        
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}