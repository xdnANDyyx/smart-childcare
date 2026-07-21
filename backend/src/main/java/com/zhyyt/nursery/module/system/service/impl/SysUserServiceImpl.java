package com.zhyyt.nursery.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhyyt.nursery.common.constant.Constants;
import com.zhyyt.nursery.common.exception.BusinessException;
import com.zhyyt.nursery.module.system.entity.SysUser;
import com.zhyyt.nursery.module.system.mapper.SysUserMapper;
import com.zhyyt.nursery.module.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;

    @Override
    public List<SysUser> listUsers(int page, int size, String keyword, Long deptId, String status) {
        Page<SysUser> pageObj = new Page<>(page, size);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(SysUser::getUsername, keyword)
                    .or().like(SysUser::getNickname, keyword)
                    .or().like(SysUser::getPhone, keyword);
        }
        if (deptId != null) {
            wrapper.eq(SysUser::getDeptId, deptId);
        }
        if (StringUtils.hasText(status)) {
            wrapper.eq(SysUser::getStatus, status);
        }
        wrapper.orderByDesc(SysUser::getCreateTime);
        Page<SysUser> result = userMapper.selectPage(pageObj, wrapper);
        result.getRecords().forEach(u -> u.setPassword(null));
        return result.getRecords();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(SysUser user, Long[] roleIds) {
        SysUser exist = userMapper.selectByUsername(user.getUsername());
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(
                (user.getPassword() != null ? user.getPassword() : Constants.DEFAULT_PASSWORD).getBytes()));
        if (user.getStatus() == null) {
            user.setStatus("0");
        }
        userMapper.insert(user);
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                userMapper.insertUserRole(user.getUserId(), roleId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser user, Long[] roleIds) {
        user.setPassword(null); // 不在此处修改密码
        userMapper.updateById(user);
        if (roleIds != null) {
            userMapper.deleteUserRole(user.getUserId());
            for (Long roleId : roleIds) {
                userMapper.insertUserRole(user.getUserId(), roleId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        if (Constants.SUPER_ADMIN_ID == userId) {
            throw new BusinessException("不允许删除超级管理员");
        }
        userMapper.deleteUserRole(userId);
        userMapper.deleteById(userId);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        userMapper.updateById(user);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        String oldMd5 = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if (!oldMd5.equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        userMapper.updateById(user);
    }

    @Override
    public void changeStatus(Long userId, String status) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setStatus(status);
        userMapper.updateById(user);
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        return userMapper.selectRoleIdsByUserId(userId);
    }
}
