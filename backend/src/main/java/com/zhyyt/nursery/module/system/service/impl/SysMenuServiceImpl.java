package com.zhyyt.nursery.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhyyt.nursery.module.system.entity.SysMenu;
import com.zhyyt.nursery.module.system.mapper.SysMenuMapper;
import com.zhyyt.nursery.module.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper menuMapper;

    @Override
    public List<SysMenu> listMenus(String keyword) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(SysMenu::getMenuName, keyword);
        }
        wrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menus = menuMapper.selectList(wrapper);
        return buildTree(menus);
    }

    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        List<SysMenu> menus = menuMapper.selectMenusByUserId(userId);
        return buildTree(menus);
    }

    @Override
    public void createMenu(SysMenu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(SysMenu menu) {
        menuMapper.updateById(menu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        // 检查是否有子菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, menuId);
        if (menuMapper.selectCount(wrapper) > 0) {
            throw new com.zhyyt.nursery.common.exception.BusinessException("存在子菜单，不允许删除");
        }
        menuMapper.deleteById(menuId);
    }

    @Override
    public List<SysMenu> buildTree(List<SysMenu> menus) {
        return menus.stream()
                .filter(m -> m.getParentId() == null || m.getParentId() == 0L)
                .peek(m -> {
                    m.setChildren(findChildren(m, menus));
                    if (m.getChildren().isEmpty()) {
                        m.setChildren(null);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<SysMenu> findChildren(SysMenu parent, List<SysMenu> all) {
        return all.stream()
                .filter(m -> parent.getMenuId().equals(m.getParentId()))
                .peek(m -> {
                    m.setChildren(findChildren(m, all));
                    if (m.getChildren().isEmpty()) {
                        m.setChildren(null);
                    }
                })
                .collect(Collectors.toList());
    }
}
