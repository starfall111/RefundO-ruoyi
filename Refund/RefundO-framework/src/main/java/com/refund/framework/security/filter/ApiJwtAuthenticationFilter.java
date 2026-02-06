package com.refund.framework.security.filter;

import com.refund.common.constant.Constants;
import com.refund.common.core.domain.model.ApiLoginUser;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.common.utils.StringUtils;
import com.refund.framework.web.service.ApiTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * APP端 JWT认证过滤器
 * 拦截/api/*路径的请求，验证JWT token
 */
@Component
public class ApiJwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(ApiJwtAuthenticationFilter.class);

    @Autowired
    private ApiTokenService apiTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 只处理API路径
        String requestURI = request.getRequestURI();
        if (!requestURI.startsWith("/api/")) {
            chain.doFilter(request, response);
            return;
        }

        ApiLoginUser loginUser = apiTokenService.getLoginUser(request);

        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            // 验证令牌有效期
            apiTokenService.verifyToken(loginUser);

            // 设置用户信息到SecurityContext（用于权限控制）
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    loginUser,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_USER"))
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 设置到ApiSecurityUtils用于业务代码获取用户信息
            ApiSecurityUtils.setApiLoginUser(loginUser);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            // 清理ThreadLocal
            ApiSecurityUtils.clearApiLoginUser();
        }
    }
}
