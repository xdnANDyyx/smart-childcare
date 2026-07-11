package com.zhyyt.nursery.module.system.controller;

import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.system.entity.SysRole;
import com.zhyyt.nursery.module.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @Operation(summary = "角色列表")
    @GetMapping("/list")
    public Result<List<SysRole>> list(@RequestParam(required = false) String keyword) {
        return Result.ok(roleService.listRoles(keyword));
    }

    @Data
    public static class RoleDTO {
        private SysRole role;
        private Long[] menuIds;
    }

    @Operation(summary = "新增角色")
    @PostMapping
    public Result<Void> add(@RequestBody RoleDTO dto) {
        roleService.createRole(dto.getRole(), dto.getMenuIds());
        return Result.ok();
    }

    @Operation(summary = "修改角色")
    @PutMapping
    public Result<Void> edit(@RequestBody RoleDTO dto) {
        roleService.updateRole(dto.getRole(), dto.getMenuIds());
        return Result.ok();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{roleId}")
    public Result<Void> remove(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return Result.ok();
    }

    @Operation(summary = "修改状态")
    @PutMapping("/changeStatus")
    public Result<Void> changeStatus(@RequestParam Long roleId, @RequestParam String status) {
        roleService.changeStatus(roleId, status);
        return Result.ok();
    }
}
