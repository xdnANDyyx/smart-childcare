package com.zhyyt.nursery.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhyyt.nursery.module.system.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    List<SysRole> listRoles(String keyword);

    void createRole(SysRole role, Long[] menuIds);

    void updateRole(SysRole role, Long[] menuIds);

    void deleteRole(Long roleId);

    void changeStatus(Long roleId, String status);
}
