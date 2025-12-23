package com.refund.root.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.refund.common.annotation.Log;
import com.refund.common.core.controller.BaseController;
import com.refund.common.core.domain.AjaxResult;
import com.refund.common.enums.BusinessType;
import com.refund.root.domain.RfUsers;
import com.refund.root.service.IRfUsersService;
import com.refund.common.utils.poi.ExcelUtil;
import com.refund.common.core.page.TableDataInfo;

/**
 * 用户信息Controller
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@RestController
@RequestMapping("/users/users")
public class RfUsersController extends BaseController
{
    @Autowired
    private IRfUsersService usersService;

    /**
     * 查询用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('users:users:list')")
    @GetMapping("/list")
    public TableDataInfo list(RfUsers rfUsers)
    {
        startPage();
        List<RfUsers> list = usersService.selectUsersList(rfUsers);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('users:users:export')")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RfUsers rfUsers)
    {
        List<RfUsers> list = usersService.selectUsersList(rfUsers);
        ExcelUtil<RfUsers> util = new ExcelUtil<RfUsers>(RfUsers.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('users:users:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(usersService.selectUsersByUserId(userId));
    }

    /**
     * 新增用户信息
     */
    @PreAuthorize("@ss.hasPermi('users:users:add')")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RfUsers rfUsers)
    {
        return toAjax(usersService.insertUsers(rfUsers));
    }

    /**
     * 修改用户信息
     */
    @PreAuthorize("@ss.hasPermi('users:users:edit')")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RfUsers rfUsers)
    {
        return toAjax(usersService.updateUsers(rfUsers));
    }

    /**
     * 删除用户信息
     */
    @PreAuthorize("@ss.hasPermi('users:users:remove')")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(usersService.deleteUsersByUserIds(userIds));
    }

    /**
     * 修改用户状态 0为正常 1为冻结 2为封禁
     * */
    @PreAuthorize("@ss.hasPermi('users:users:status')")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping("status/{userIds}/{status}")
    public AjaxResult changeStatus(@PathVariable("userIds") Long[] userId, @PathVariable("status") Integer status)
    {
        return toAjax(usersService.updateUsersStatus(userId, status));
    }
}
