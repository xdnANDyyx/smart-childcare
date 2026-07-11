package com.zhyyt.nursery.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhyyt.nursery.common.constant.Constants;
import com.zhyyt.nursery.common.exception.BusinessException;
import com.zhyyt.nursery.module.system.entity.SysRole;
import com.zhyyt.nursery.module.system.mapper.SysMenuMapper;
import com.zhyyt.nursery.module.system.mapper.SysRoleMapper;
import com.zhyyt.nursery.module.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper roleMapper;
    private final SysMenuMapper menuMapper;

    @Override
    public List<SysRole> listRoles(String keyword) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(SysRole::getRoleName, keyword).or().like(SysRole::getRoleKey, keyword);
        }
        wrapper.orderByAsc(SysRole::getRoleSort);
        return roleMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(SysRole role, Long[] menuIds) {
        role.setStatus("0");
        roleMapper.insert(role);
        if (menuIds != null) {
            for (Long menuId : menuIds) {
                menuMapper.insertRoleMenu(role.getRoleId(), menuId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRole role, Long[] menuIds) {
        roleMapper.updateById(role);
        if (menuIds != null) {
            menuMapper.deleteRoleMenu(role.getRoleId());
            for (Long menuId : menuIds) {
                menuMapper.insertRoleMenu(role.getRoleId(), menuId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId) {
        if (Constants.SUPER_ADMIN_ID == roleId) {
            throw new BusinessException("不允许删除超级管理员角色");
        }
        menuMapper.deleteRoleMenu(roleId);
        roleMapper.deleteById(roleId);
    }

    @Override
    public void changeStatus(Long roleId, String status) {
        SysRole role = new SysRole();
        role.setRoleId(roleId);
        role.setStatus(status);
        roleMapper.updateById(role);
    }
}
