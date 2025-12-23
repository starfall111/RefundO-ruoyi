package com.refund.root.service.impl;

import java.util.List;
import com.refund.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refund.root.mapper.RfUsersMapper;
import com.refund.root.domain.RfUsers;
import com.refund.root.service.IRfUsersService;

/**
 * 用户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Service
public class RfRfUsersServiceImpl implements IRfUsersService
{
    @Autowired
    private RfUsersMapper usersMapper;

    /**
     * 查询用户信息
     * 
     * @param userId 用户信息主键
     * @return 用户信息
     */
    @Override
    public RfUsers selectUsersByUserId(Long userId)
    {
        return usersMapper.selectUsersByUserId(userId);
    }

    /**
     * 查询用户信息列表
     * 
     * @param rfUsers 用户信息
     * @return 用户信息
     */
    @Override
    public List<RfUsers> selectUsersList(RfUsers rfUsers)
    {
        return usersMapper.selectUsersList(rfUsers);
    }

    /**
     * 新增用户信息
     * 
     * @param rfUsers 用户信息
     * @return 结果
     */
    @Override
    public int insertUsers(RfUsers rfUsers)
    {
        rfUsers.setCreateTime(DateUtils.getNowDate());
        return usersMapper.insertUsers(rfUsers);
    }

    /**
     * 修改用户信息
     * 
     * @param rfUsers 用户信息
     * @return 结果
     */
    @Override
    public int updateUsers(RfUsers rfUsers)
    {
        return usersMapper.updateUsers(rfUsers);
    }

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUsersByUserIds(Long[] userIds)
    {
        return usersMapper.deleteUsersByUserIds(userIds);
    }

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUsersByUserId(Long userId)
    {
        return usersMapper.deleteUsersByUserId(userId);
    }

    /**
     * 修改用户状态
     *
     * @param userIds 用户ID
     * @param status 状态
     * @return 结果
     */
    @Override
    public int updateUsersStatus(Long[] userIds, Integer status) {
        return usersMapper.updateUsersStatus(userIds, status);
    }
}
