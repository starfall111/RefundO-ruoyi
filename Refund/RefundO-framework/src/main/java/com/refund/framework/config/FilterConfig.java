package com.refund.framework.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.refund.common.constant.Constants;
import com.refund.common.filter.RefererFilter;
import com.refund.common.filter.RepeatableFilter;
import com.refund.common.filter.XssFilter;
import com.refund.common.utils.StringUtils;
import com.refund.framework.security.filter.LocaleFilter;
import com.refund.framework.security.filter.RateLimitFilter;

/**
 * Filter配置
 *
 * @author ruoyi
 */
@Configuration
public class FilterConfig
{
    @Value("${xss.excludes}")
    private String excludes;

    @Value("${xss.urlPatterns}")
    private String urlPatterns;

    @Value("${referer.allowed-domains}")
    private String allowedDomains;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
    public FilterRegistrationBean xssFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", excludes);
        registration.setInitParameters(initParameters);
        return registration;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @ConditionalOnProperty(value = "referer.enabled", havingValue = "true")
    public FilterRegistrationBean refererFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new RefererFilter());
        registration.addUrlPatterns(Constants.RESOURCE_PREFIX + "/*");
        registration.setName("refererFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("allowedDomains", allowedDomains);
        registration.setInitParameters(initParameters);
        return registration;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean someFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

    /**
     * 注册APP端语言环境过滤器
     * Order=1，最先执行
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean localeFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LocaleFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("localeFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 注册APP端限流过滤器
     * Order=2，在LocaleFilter之后执行
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean rateLimitFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RateLimitFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("rateLimitFilter");
        registration.setOrder(2);
        return registration;
    }

}
