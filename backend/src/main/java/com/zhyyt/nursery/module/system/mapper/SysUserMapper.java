package com.zhyyt.nursery.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhyyt.nursery.module.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /** 根据用户名查询用户 */
    SysUser selectByUsername(@Param("username") String username);

    /** 查询用户角色ID列表 */
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);

    /** 查询用户角色编码列表 */
    List<String> selectRoleKeysByUserId(@Param("userId") Long userId);

    /** 查询用户菜单权限列表 */
    List<String> selectPermsByUserId(@Param("userId") Long userId);

    /** 查询用户菜单列表 */
    List<Long> selectMenuIdsByUserId(@Param("userId") Long userId);

    /** 插入用户角色关联 */
    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /** 删除用户角色关联 */
    int deleteUserRole(@Param("userId") Long userId);
}
