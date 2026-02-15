package com.refund.root.service.impl;

import com.refund.common.core.domain.dto.UserLoginDTO;
import com.refund.common.core.domain.dto.UserRegisterDTO;
import com.refund.common.core.domain.dto.UserUpdateDTO;
import com.refund.common.core.domain.vo.UserLoginVO;
import com.refund.common.exception.business.AccountNotFoundException;
import com.refund.common.exception.business.AccountRepetitionException;
import com.refund.common.exception.business.AccountStatusErrorException;
import com.refund.common.exception.business.PasswordErrorException;
import com.refund.common.exception.business.CodeErrorException;
import com.refund.common.exception.business.LoginLockedException;
import com.refund.common.exception.business.MessageKeys;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.common.utils.StringUtils;
import com.refund.common.utils.password.PasswordUtil;
import com.refund.common.utils.uuid.IdUtils;
import com.refund.root.domain.RfUsers;
import com.refund.root.mapper.RfUsersMapper;
import com.refund.root.service.IAppUserService;
import com.refund.root.service.IVerificationCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * APP端用户服务实现
 */
@Service
public class AppUserServiceImpl implements IAppUserService {

    // ========== 登录防撞库配置 ==========
    // 最大尝试次数
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    // 锁定时间
    private static final long LOCK_TIME_MINUTES = 30;
    // 用户IP登录失败记录前缀
    private static final String LOGIN_FAIL_IP_PREFIX = "login_fail:ip:";
    // 用户用户登录失败记录前缀
    private static final String LOGIN_FAIL_USER_PREFIX = "login_fail:user:";
    // 用户IP登录失败记录前缀
    private static final String LOGIN_LOCK_IP_PREFIX = "login_lock:ip:";
    // 用户用户登录失败记录前缀
    private static final String LOGIN_LOCK_USER_PREFIX = "login_lock:user:";


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
        RfUsers existingUser = usersMapper.loginByNameOrEmail(userName, null);
        if (existingUser != null) {
            throw new AccountRepetitionException(MessageKeys.ACCOUNT_REPETITION_USERNAME, userName);
        }

        // 3. 检查邮箱是否已存在
        existingUser = usersMapper.loginByNameOrEmail(null, email);
        if (existingUser != null) {
            throw new AccountRepetitionException(MessageKeys.ACCOUNT_REPETITION_EMAIL, email);
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
            throw new AccountStatusErrorException(MessageKeys.SERVER_SIGNUP_FAILED);
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

        // 获取客户端IP
        String clientIp = getClientIp();

        // 1. 检查IP是否被锁定
        checkIpLocked(clientIp);

        // 2. 检查用户是否被锁定
        String loginIdentifier = userName != null ? userName : email;
        checkUserLocked(loginIdentifier);

        // 3. 验证（失败时记录）
        try {
            // 查询用户是否存在
            RfUsers user = usersMapper.loginByNameOrEmail(userName, email);

            // 验证用户状态
            validateUser(user);

            // 验证密码
            validatePassword(user, password);

            // 登录成功，清除失败记录
            clearLoginFailCount(clientIp, loginIdentifier);

            // 返回用户信息（不包含密码）
            UserLoginVO resultUser = new UserLoginVO();
            BeanUtils.copyProperties(user, resultUser);

            return resultUser;
        } catch (AccountNotFoundException | PasswordErrorException e) {
            // 登录失败，记录失败次数并返回剩余尝试次数
            int[] remainingAttempts = recordLoginFail(clientIp, loginIdentifier);
            if (remainingAttempts[0] > 0) {
                throw new PasswordErrorException(MessageKeys.LOGIN_PASSWORD_REMAINING_ATTEMPTS,
                        new Object[]{String.valueOf(remainingAttempts[0])});
            } else if (remainingAttempts[1] > 0) {
                throw new PasswordErrorException(MessageKeys.LOGIN_PASSWORD_REMAINING_ATTEMPTS,
                        new Object[]{String.valueOf(remainingAttempts[1])});
            }
            throw e;
        }
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
            throw new AccountNotFoundException(MessageKeys.ACCOUNT_NOT_FOUND);
        }

        // 更新用户信息
        if (!user.getUsername().equals(userUpdate.getUserName())) {
            // 检查新用户名是否已存在
            RfUsers existingUser = usersMapper.loginByNameOrEmail(userUpdate.getUserName(), null);
            if (existingUser != null && !existingUser.getUserId().equals(userId)) {
                throw new AccountRepetitionException(MessageKeys.ACCOUNT_REPETITION_USERNAME, userUpdate.getUserName());
            }
            user.setUsername(userUpdate.getUserName());
        }

        if (!user.getEmail().equals(userUpdate.getEmail())) {
            // 检查新邮箱是否已存在
            RfUsers existingUser = usersMapper.loginByNameOrEmail(null, userUpdate.getEmail());
            if (existingUser != null && !existingUser.getUserId().equals(userId)) {
                throw new AccountRepetitionException(MessageKeys.ACCOUNT_REPETITION_EMAIL, userUpdate.getEmail());
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
            throw new AccountStatusErrorException(MessageKeys.SERVER_UPDATE_FAILED);
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
            throw new AccountNotFoundException(MessageKeys.ACCOUNT_NOT_FOUND);
        }

        // 加密新密码
        String encryptedPassword = PasswordUtil.encodePassword(newPassword);

        // 更新密码
        user.setPassword(encryptedPassword);
        int result = usersMapper.updateUsers(user);
        if (result == 0) {
            throw new AccountStatusErrorException(MessageKeys.PASSWORD_UPDATE_FAILED);
        }
    }

    /**
     * 验证用户密码
     */
    @Override
    public void validatePassword(RfUsers user, String password) {
        if (!PasswordUtil.matchesPassword(password, user.getPassword())) {
            throw new PasswordErrorException(MessageKeys.PASSWORD_INCORRECT);
        }
    }

    /**
     * 验证用户状态
     */
    @Override
    public void validateUser(RfUsers user) {
        if (user == null) {
            throw new AccountNotFoundException(MessageKeys.ACCOUNT_NOT_FOUND);
        }

        // 检查用户状态
        if (user.getUserStatus() == 2) {
            throw new AccountStatusErrorException(MessageKeys.ACCOUNT_STATUS_DISABLED);
        }
        if (user.getUserStatus() == 1) {
            throw new AccountStatusErrorException(MessageKeys.ACCOUNT_STATUS_FROZEN);
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
            throw new AccountNotFoundException(MessageKeys.ACCOUNT_NOT_FOUND);
        }

        // 生成临时token
        String token = IdUtils.fastUUID();

        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserId(user.getUserId());
        userLoginVO.setUsername(user.getUsername());
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
            throw new CodeErrorException(MessageKeys.CODE_EXPIRED);
        } else if (!code.equals(redisCode)) {
            throw new CodeErrorException(MessageKeys.CODE_INCORRECT);
        }

        redisTemplate.delete(email);
    }

    // ==================== 登录防撞库辅助方法 ====================

    /**
     * 检查IP是否被锁定
     */
    private void checkIpLocked(String ip) {
        if ("unknown".equals(ip)) return; // 未知IP不锁定

        String lockKey = LOGIN_LOCK_IP_PREFIX + ip;
        Boolean isLocked = redisTemplate.hasKey(lockKey);
        if (Boolean.TRUE.equals(isLocked)) {
            Long ttlSeconds = redisTemplate.getExpire(lockKey, TimeUnit.SECONDS);
            if (ttlSeconds != null && ttlSeconds > 0) {
                // 计算解锁时间（HH:mm格式）
                long unlockTimeMillis = System.currentTimeMillis() + (ttlSeconds * 1000);
                java.time.LocalTime unlockTime = java.time.Instant.ofEpochMilli(unlockTimeMillis)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalTime();
                String unlockTimeStr = unlockTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
                throw new LoginLockedException(MessageKeys.LOGIN_IP_LOCKED_UNTIL,
                        new Object[]{unlockTimeStr});
            }
        }
    }

    /**
     * 检查用户是否被锁定
     */
    private void checkUserLocked(String identifier) {
        if (identifier == null) return;

        String lockKey = LOGIN_LOCK_USER_PREFIX + identifier;
        Boolean isLocked = redisTemplate.hasKey(lockKey);
        if (Boolean.TRUE.equals(isLocked)) {
            Long ttlSeconds = redisTemplate.getExpire(lockKey, TimeUnit.SECONDS);
            if (ttlSeconds != null && ttlSeconds > 0) {
                // 计算解锁时间（HH:mm格式）
                long unlockTimeMillis = System.currentTimeMillis() + (ttlSeconds * 1000);
                java.time.LocalTime unlockTime = java.time.Instant.ofEpochMilli(unlockTimeMillis)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalTime();
                String unlockTimeStr = unlockTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
                throw new LoginLockedException(MessageKeys.LOGIN_ACCOUNT_LOCKED_UNTIL,
                        new Object[]{unlockTimeStr});
            }
        }
    }

    /**
     * 验证邮箱验证码
     */
    private void verifyCode(String email, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get("email_code:" + email);
        if (redisCode == null) {
            throw new CodeErrorException(MessageKeys.CODE_EXPIRED);
        } else if (!code.equals(redisCode)) {
            throw new CodeErrorException(MessageKeys.CODE_INCORRECT);
        }
    }

    /**
     * 记录登录失败
     * @return int[]{ipRemainingAttempts, userRemainingAttempts}
     *         如果被锁定则对应位置返回0或负数，否则返回剩余尝试次数
     */
    private int[] recordLoginFail(String ip, String userIdentifier) {
        int ipRemaining = -1; // -1表示不适用
        int userRemaining = -1;

        // 记录IP失败次数
        if (!"unknown".equals(ip)) {
            String ipFailKey = LOGIN_FAIL_IP_PREFIX + ip;
            Long ipFailCount = redisTemplate.opsForValue().increment(ipFailKey);
            if (ipFailCount == 1) {
                redisTemplate.expire(ipFailKey, LOCK_TIME_MINUTES, TimeUnit.MINUTES);
            }
            // 只有当失败次数达到阈值时才锁定IP
            if (ipFailCount >= MAX_LOGIN_ATTEMPTS) {
                String ipLockKey = LOGIN_LOCK_IP_PREFIX + ip;
                redisTemplate.opsForValue().set(ipLockKey, "1", LOCK_TIME_MINUTES, TimeUnit.MINUTES);
                redisTemplate.delete(ipFailKey);
                // 计算解锁时间
                long unlockTimeMillis = System.currentTimeMillis() + (LOCK_TIME_MINUTES * 60 * 1000);
                java.time.LocalTime unlockTime = java.time.Instant.ofEpochMilli(unlockTimeMillis)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalTime();
                String unlockTimeStr = unlockTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
                throw new LoginLockedException(MessageKeys.LOGIN_IP_LOCKED_UNTIL,
                        new Object[]{unlockTimeStr});
            } else {
                ipRemaining = (int) (MAX_LOGIN_ATTEMPTS - ipFailCount);
            }
        }

        // 记录用户失败次数
        if (userIdentifier != null) {
            String userFailKey = LOGIN_FAIL_USER_PREFIX + userIdentifier;
            Long userFailCount = redisTemplate.opsForValue().increment(userFailKey);
            if (userFailCount == 1) {
                redisTemplate.expire(userFailKey, LOCK_TIME_MINUTES, TimeUnit.MINUTES);
            }
            if (userFailCount >= MAX_LOGIN_ATTEMPTS) {
                String userLockKey = LOGIN_LOCK_USER_PREFIX + userIdentifier;
                redisTemplate.opsForValue().set(userLockKey, "1", LOCK_TIME_MINUTES, TimeUnit.MINUTES);
                redisTemplate.delete(userFailKey);
                // 计算解锁时间
                long unlockTimeMillis = System.currentTimeMillis() + (LOCK_TIME_MINUTES * 60 * 1000);
                java.time.LocalTime unlockTime = java.time.Instant.ofEpochMilli(unlockTimeMillis)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalTime();
                String unlockTimeStr = unlockTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
                throw new LoginLockedException(MessageKeys.LOGIN_ACCOUNT_LOCKED_UNTIL,
                        new Object[]{unlockTimeStr});
            } else {
                userRemaining = (int) (MAX_LOGIN_ATTEMPTS - userFailCount);
            }
        }

        return new int[]{ipRemaining, userRemaining};
    }


    /**
     * 清除登录失败记录
     */
    private void clearLoginFailCount(String ip, String userIdentifier) {
        if (!"unknown".equals(ip)) {
            redisTemplate.delete(LOGIN_FAIL_IP_PREFIX + ip);
        }
        if (userIdentifier != null) {
            redisTemplate.delete(LOGIN_FAIL_USER_PREFIX + userIdentifier);
        }
    }


    /**
     * 获取客户端IP地址
     * 只使用request.getRemoteAddr()，不读取反向代理请求头（防止IP伪造）
     */
    private String getClientIp() {
        try {
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                            .getRequest();

            String ip = request.getRemoteAddr();

            // 处理IPv6本地地址
            if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
                ip = "127.0.0.1";
            }

            return ip != null ? ip : "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }

}
