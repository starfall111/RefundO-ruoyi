package com.refund.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.refund.common.constant.CacheConstants;
import com.refund.common.constant.Constants;
import com.refund.common.constant.UserConstants;
import com.refund.common.core.domain.entity.SysUser;
import com.refund.common.core.domain.model.RegisterBody;
import com.refund.common.core.redis.RedisCache;
import com.refund.common.exception.user.CaptchaException;
import com.refund.common.exception.user.CaptchaExpireException;
import com.refund.common.utils.DateUtils;
import com.refund.common.utils.MessageUtils;
import com.refund.common.utils.SecurityUtils;
import com.refund.common.utils.StringUtils;
import com.refund.framework.manager.AsyncManager;
import com.refund.framework.manager.factory.AsyncFactory;
import com.refund.system.service.ISysConfigService;
import com.refund.system.service.ISysUserService;

/**
 * 注册校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);

        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled)
        {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username))
        {
            msg = MessageUtils.message("register.username.empty");
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = MessageUtils.message("register.password.empty");
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = MessageUtils.message("register.username.length");
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = MessageUtils.message("register.password.length");
        }
        else if (!userService.checkUserNameUnique(sysUser))
        {
            msg = MessageUtils.message("register.failed.username.exists", username);
        }
        else
        {
            sysUser.setNickName(username);
            sysUser.setPwdUpdateDate(DateUtils.getNowDate());
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag)
            {
                msg = MessageUtils.message("register.failed");
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }
}
