package com.zhyyt.nursery.module.system.controller;

import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.system.entity.SysMenu;
import com.zhyyt.nursery.module.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜单管理")
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;

    @Operation(summary = "菜单列表")
    @GetMapping("/list")
    public Result<List<SysMenu>> list(@RequestParam(required = false) String keyword) {
        return Result.ok(menuService.listMenus(keyword));
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    public Result<Void> add(@RequestBody SysMenu menu) {
        menuService.createMenu(menu);
        return Result.ok();
    }

    @Operation(summary = "修改菜单")
    @PutMapping
    public Result<Void> edit(@RequestBody SysMenu menu) {
        menuService.updateMenu(menu);
        return Result.ok();
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{menuId}")
    public Result<Void> remove(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return Result.ok();
    }
}
