package com.refund.root.controller.api;

import com.refund.common.annotation.RateLimit;
import com.refund.common.core.domain.Result;
import com.refund.common.core.domain.dto.UserLoginDTO;
import com.refund.common.core.domain.dto.UserRegisterDTO;
import com.refund.common.core.domain.dto.UserUpdateDTO;
import com.refund.common.core.domain.model.ApiLoginUser;
import com.refund.common.core.domain.vo.UserLoginVO;
import com.refund.common.enums.LimitType;
import com.refund.common.utils.ApiSecurityUtils;
import com.refund.framework.web.service.ApiTokenService;
import com.refund.root.domain.RfUsers;
import com.refund.root.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * APP端用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class AppUserController {

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private ApiTokenService apiTokenService;

    /**
     * 用户注册
     *
     * @param userRegisterDTO 用户注册信息
     * @return 注册结果
     */
    @PostMapping("/signup")
    @RateLimit(time = 60, count = 5, limitType = LimitType.IP, key = "signup")
    public Result<Void> signup(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        appUserService.signup(userRegisterDTO);
        return Result.success();
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO 登录信息
     * @return 用户信息（包含token）
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        UserLoginVO userLoginVO = appUserService.login(userLoginDTO);

        // 创建ApiLoginUser并生成token
        ApiLoginUser apiLoginUser = new ApiLoginUser();
        apiLoginUser.setUserId(userLoginVO.getUserId());
        apiLoginUser.setUsername(userLoginVO.getUserName());
        apiLoginUser.setEmail(userLoginVO.getEmail());

        String token = apiTokenService.createToken(apiLoginUser);
        userLoginVO.setToken(token);

        return Result.success(userLoginVO);
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @GetMapping
    public Result<RfUsers> getUserInfo() {
        RfUsers user = appUserService.getCurrentUser();
        return Result.success(user);
    }

    /**
     * 更新用户信息
     *
     * @param userUpdate 更新信息
     * @return 更新后的用户信息
     */
    @PutMapping
    public Result<RfUsers> updateUser(@RequestBody UserUpdateDTO userUpdate) {
        RfUsers user = appUserService.updateUser(userUpdate);
        return Result.success(user);
    }

    /**
     * 更新用户密码
     *
     * @param newPassword 新密码
     * @return 更新结果
     */
    @PutMapping("/password")
    @RateLimit(time = 3600, count = 3, limitType = LimitType.USER, key = "password")
    public Result<Void> updatePassword(@RequestParam String newPassword) {
        appUserService.updatePassword(newPassword);
        return Result.success();
    }

    /**
     * 验证用户登录信息
     *
     * @param userLoginDTO 登录信息
     * @return 验证结果
     */
    @PostMapping("/check")
    public Result<Void> check(@RequestBody UserLoginDTO userLoginDTO) {
        appUserService.login(userLoginDTO);
        return Result.success();
    }

    /**
     * 忘记密码
     *
     * @param email 邮箱
     * @return 用户登录信息
     */
    @GetMapping("/forget")
    public Result<UserLoginVO> forget(@RequestParam String email) {
        UserLoginVO userLoginVO = appUserService.forget(email);
        return Result.success(userLoginVO);
    }

    /**
     * 验证码校验
     *
     * @param email 邮箱
     * @param code  验证码
     * @return 验证结果
     */
    @GetMapping("/checkCode")
    public Result<Void> checkCode(@RequestParam String email, @RequestParam String code) {
        appUserService.checkCode(email, code);
        return Result.success();
    }
}
