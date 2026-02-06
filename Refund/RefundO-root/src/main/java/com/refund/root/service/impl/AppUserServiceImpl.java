package com.refund.root.service.impl;

import com.refund.common.core.domain.dto.UserLoginDTO;
import com.refund.common.core.domain.dto.UserRegisterDTO;
import com.refund.common.core.domain.dto.UserUpdateDTO;
import com.refund.common.core.domain.vo.UserLoginVO;
import com.refund.common.exception.ServiceException;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.common.utils.StringUtils;
import com.refund.common.utils.password.PasswordUtil;
import com.refund.common.utils.uuid.IdUtils;
import com.refund.root.domain.RfUsers;
import com.refund.root.mapper.RfUsersMapper;
import com.refund.root.service.IAppUserService;
import com.refund.root.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * APP端用户服务实现
 */
@Service
public class AppUserServiceImpl implements IAppUserService {

    @Autowired
    private RfUsersMapper usersMapper;

    @Autowired
    private IVerificationCodeService verificationCodeService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     */
    @Override
    public void signup(UserRegisterDTO userRegisterDTO) {
        String userName = userRegisterDTO.getUserName();
        String email = userRegisterDTO.getEmail();
        String password = userRegisterDTO.getPassword();
        String verificationCode = userRegisterDTO.getVerificationCode();

        // 1. 验证邮箱验证码
        verifyCode(email, verificationCode);

        // 2. 检查用户名是否已存在
        RfUsers checkUser = new RfUsers();
        checkUser.setUsername(userName);
        RfUsers existingUser = usersMapper.loginByNameOrEmail(userRegisterDTO.getUserName(),null);
        if (existingUser != null) {
            throw new ServiceException("用户名已存在");
        }

        // 3. 检查邮箱是否已存在
        checkUser = new RfUsers();
        checkUser.setEmail(email);
        existingUser = usersMapper.loginByNameOrEmail(null,userRegisterDTO.getEmail());
        if (existingUser != null) {
            throw new ServiceException("邮箱已被注册");
        }

        // 4. 密码加密
        String encryptedPassword = PasswordUtil.encodePassword(password);

        // 5. 执行注册
        RfUsers user = new RfUsers();
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setBalance(BigDecimal.ZERO);
        user.setUserStatus(0L); // 正常状态

        int result = usersMapper.insertUsers(user);
        if (result == 0) {
            throw new ServiceException("注册失败");
        }
    }

    /**
     * 用户登录
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        String userName = userLoginDTO.getUserName();
        String email = userLoginDTO.getEmail();
        String password = userLoginDTO.getPassword();

        // 查询用户是否存在
        RfUsers user = usersMapper.loginByNameOrEmail(userName, email);

        // 验证用户状态
        validateUser(user);

        // 验证密码
        validatePassword(user, password);

        // 返回用户信息（不包含密码）
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserId(user.getUserId());
        userLoginVO.setUserName(user.getUsername());
        userLoginVO.setEmail(user.getEmail());
        userLoginVO.setBalance(user.getBalance());
        userLoginVO.setPhoneNumber(user.getPhoneNumber());
        userLoginVO.setSangke(user.getSangke());
        userLoginVO.setWave(user.getWave());

        return userLoginVO;
    }

    /**
     * 获取当前用户信息
     */
    @Override
    public RfUsers getCurrentUser() {
        Long userId = ApiSecurityUtils.getUserId();
        RfUsers user = usersMapper.selectUsersByUserId(userId);
        if (user != null) {
            user.setPassword(null); // 不返回密码
        }
        return user;
    }

    /**
     * 更新用户信息
     */
    @Override
    public RfUsers updateUser(UserUpdateDTO userUpdate) {
        Long userId = ApiSecurityUtils.getUserId();
        RfUsers user = usersMapper.selectUsersByUserId(userId);

        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        // 更新用户信息
        if (!user.getUsername().equals(userUpdate.getUserName())) {
            // 检查新用户名是否已存在
            RfUsers existingUser = usersMapper.loginByNameOrEmail(userUpdate.getUserName(),null);
            if (existingUser != null && !existingUser.getUserId().equals(userId)) {
                throw new ServiceException("用户名已被使用");
            }
            user.setUsername(userUpdate.getUserName());
        }

        if (!user.getEmail().equals(userUpdate.getEmail())) {
            // 检查新邮箱是否已存在
            RfUsers existingUser = usersMapper.loginByNameOrEmail(null, userUpdate.getEmail());
            if (existingUser != null && !existingUser.getUserId().equals(userId)) {
                throw new ServiceException("邮箱已被使用");
            }
            user.setEmail(userUpdate.getEmail());
        }

        if (StringUtils.isNotEmpty(userUpdate.getPhoneNumber())) {
            user.setPhoneNumber(userUpdate.getPhoneNumber());
        }

        if (StringUtils.isNotEmpty(userUpdate.getSangke())) {
            user.setSangke(userUpdate.getSangke());
        }

        if (StringUtils.isNotEmpty(userUpdate.getWave())) {
            user.setWave(userUpdate.getWave());
        }

        if (StringUtils.isNotEmpty(userUpdate.getAvatarUrl())) {
            // 需要添加avatarUrl字段到实体
            // user.setAvatarUrl(userUpdate.getAvatarUrl());
        }

        // 执行更新
        int result = usersMapper.updateUsers(user);
        if (result == 0) {
            throw new ServiceException("更新失败");
        }

        // 返回更新后的用户信息（不包含密码）
        user.setPassword(null);
        return user;
    }

    /**
     * 更新用户密码
     */
    @Override
    public void updatePassword(String newPassword) {
        Long userId = ApiSecurityUtils.getUserId();
        RfUsers user = usersMapper.selectUsersByUserId(userId);

        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        // 加密新密码
        String encryptedPassword = PasswordUtil.encodePassword(newPassword);

        // 更新密码
        user.setPassword(encryptedPassword);
        int result = usersMapper.updateUsers(user);
        if (result == 0) {
            throw new ServiceException("密码更新失败");
        }
    }

    /**
     * 验证用户密码
     */
    @Override
    public void validatePassword(RfUsers user, String password) {
        if (!PasswordUtil.matchesPassword(password, user.getPassword())) {
            throw new ServiceException("密码错误");
        }
    }

    /**
     * 验证用户状态
     */
    @Override
    public void validateUser(RfUsers user) {
        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        // 检查用户状态
        if (user.getUserStatus() == 2) {
            throw new ServiceException("账号已被禁用");
        }
        if (user.getUserStatus() == 1) {
            throw new ServiceException("账号已被冻结");
        }
    }

    /**
     * 忘记密码
     */
    @Override
    public UserLoginVO forget(String email) {
        // 查询用户是否存在
        RfUsers checkUser = new RfUsers();
        checkUser.setEmail(email);
        RfUsers user = usersMapper.selectUsersList(checkUser).stream().findFirst().orElse(null);

        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        // 生成临时token
        String token = IdUtils.fastUUID();

        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserId(user.getUserId());
        userLoginVO.setUserName(user.getUsername());
        userLoginVO.setEmail(user.getEmail());
        userLoginVO.setToken(token);

        verificationCodeService.sendCode(email);

        return userLoginVO;
    }

    /**
     * 验证码校验
     */
    @Override
    public void checkCode(String email, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get(email);
        if (redisCode == null) {
            throw new ServiceException("验证码已过期");
        } else if (!code.equals(redisCode)) {
            throw new ServiceException("验证码错误");
        }

        redisTemplate.delete(email);
    }

    /**
     * 验证邮箱验证码
     */
    private void verifyCode(String email, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get("email_code:" + email);
        if (redisCode == null) {
            throw new ServiceException("验证码已过期");
        } else if (!code.equals(redisCode)) {
            throw new ServiceException("验证码错误");
        }
    }
}
