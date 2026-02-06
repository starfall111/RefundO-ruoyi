package com.refund.root.service;

import com.refund.common.core.domain.dto.UserLoginDTO;
import com.refund.common.core.domain.dto.UserRegisterDTO;
import com.refund.common.core.domain.dto.UserUpdateDTO;
import com.refund.common.core.domain.vo.UserLoginVO;
import com.refund.root.domain.RfUsers;

/**
 * APP端用户服务接口
 */
public interface IAppUserService {

    /**
     * 用户注册
     *
     * @param userRegisterDTO 注册信息
     */
    void signup(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     *
     * @param userLoginDTO 登录信息
     * @return 用户登录信息
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    RfUsers getCurrentUser();

    /**
     * 更新用户信息
     *
     * @param userUpdate 更新信息
     * @return 更新后的用户信息
     */
    RfUsers updateUser(UserUpdateDTO userUpdate);

    /**
     * 更新用户密码
     *
     * @param newPassword 新密码
     */
    void updatePassword(String newPassword);

    /**
     * 验证用户密码
     *
     * @param user      用户信息
     * @param password  待验证密码
     */
    void validatePassword(RfUsers user, String password);

    /**
     * 验证用户状态
     *
     * @param user 用户信息
     */
    void validateUser(RfUsers user);

    /**
     * 忘记密码
     *
     * @param email 邮箱
     * @return 用户登录信息
     */
    UserLoginVO forget(String email);

    /**
     * 验证码校验
     *
     * @param email 邮箱
     * @param code  验证码
     */
    void checkCode(String email, String code);
}
