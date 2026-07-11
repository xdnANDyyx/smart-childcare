package com.zhyyt.nursery.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhyyt.nursery.module.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /** 查询所有菜单 */
    List<SysMenu> selectAllMenus();

    /** 根据用户ID查询菜单 */
    List<SysMenu> selectMenusByUserId(@Param("userId") Long userId);

    /** 根据角色ID查询菜单ID */
    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    /** 插入角色菜单关联 */
    int insertRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /** 删除角色菜单关联 */
    int deleteRoleMenu(@Param("roleId") Long roleId);
}
