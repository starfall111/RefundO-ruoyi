package com.refund.root.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.refund.root.domain.RfUsers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Mapper
public interface RfUsersMapper
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
     * 删除用户信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteUsersByUserId(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUsersByUserIds(Long[] userIds);

    /**
     * 修改用户状态
     *
     * @param userIds 需要修改的用户id
     * @param status  修改后的状态
     * @return 结果
     */
    int updateUsersStatus(@Param("userIds") Long[] userIds, @Param("status") Integer status);

    /**
     * 原子化增加用户余额（带退款请求状态校验，实现幂等性）
     * @param userId 用户ID
     * @param amount 增加金额
     * @param requestId 退款请求ID
     * @param allowedStatuses 该请求允许处于的状态列表（确保幂等性）
     * @return 影响行数，0表示状态不匹配（已处理或不存在）
     */
    int increaseBalanceWithRequestCheck(@Param("userId") Long userId,
                                         @Param("amount") BigDecimal amount,
                                         @Param("requestId") Long requestId,
                                         @Param("allowedStatuses") List<Long> allowedStatuses);
}
