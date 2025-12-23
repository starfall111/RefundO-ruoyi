package com.refund.root.service;

import java.util.List;
import com.refund.root.domain.RfUsers;

/**
 * 用户信息Service接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public interface IRfUsersService
{
    /**
     * 查询用户信息
     * 
     * @param userId 用户信息主键
     * @return 用户信息
     */
    public RfUsers selectUsersByUserId(Long userId);

    /**
     * 查询用户信息列表
     * 
     * @param rfUsers 用户信息
     * @return 用户信息集合
     */
    public List<RfUsers> selectUsersList(RfUsers rfUsers);

    /**
     * 新增用户信息
     * 
     * @param rfUsers 用户信息
     * @return 结果
     */
    public int insertUsers(RfUsers rfUsers);

    /**
     * 修改用户信息
     * 
     * @param rfUsers 用户信息
     * @return 结果
     */
    public int updateUsers(RfUsers rfUsers);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息主键集合
     * @return 结果
     */
    public int deleteUsersByUserIds(Long[] userIds);

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteUsersByUserId(Long userId);

    /**
     * 修改用户状态
     *
     * @param userId 用户id
     * @param status 状态
     * @return 结果
     */
    int updateUsersStatus(Long[] userId, Integer status);
}
