package com.zhyyt.nursery.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhyyt.nursery.module.system.entity.SysUser;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    /** 分页查询用户 */
    List<SysUser> listUsers(int page, int size, String keyword, Long deptId, String status);

    /** 新增用户 */
    void createUser(SysUser user, Long[] roleIds);

    /** 修改用户 */
    void updateUser(SysUser user, Long[] roleIds);

    /** 删除用户 */
    void deleteUser(Long userId);

    /** 重置密码 */
    void resetPassword(Long userId, String newPassword);

    /** 修改密码 */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /** 修改状态 */
    void changeStatus(Long userId, String status);
}
