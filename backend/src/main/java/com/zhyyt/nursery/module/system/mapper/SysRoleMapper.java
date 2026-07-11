package com.zhyyt.nursery.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhyyt.nursery.module.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /** 根据用户ID查询角色 */
    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);

    /** 查询所有角色 */
    List<SysRole> selectAllRoles();
}
