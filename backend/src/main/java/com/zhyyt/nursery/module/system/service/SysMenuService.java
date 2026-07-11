package com.zhyyt.nursery.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhyyt.nursery.module.system.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /** 查询菜单树（全部） */
    List<SysMenu> listMenus(String keyword);

    /** 根据用户ID查询菜单树 */
    List<SysMenu> getMenusByUserId(Long userId);

    /** 新增菜单 */
    void createMenu(SysMenu menu);

    /** 修改菜单 */
    void updateMenu(SysMenu menu);

    /** 删除菜单 */
    void deleteMenu(Long menuId);

    /** 构建菜单树结构 */
    List<SysMenu> buildTree(List<SysMenu> menus);
}
