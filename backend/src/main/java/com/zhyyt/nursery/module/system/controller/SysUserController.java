package com.zhyyt.nursery.module.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.system.entity.SysUser;
import com.zhyyt.nursery.module.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @Operation(summary = "用户列表")
    @GetMapping("/list")
    public Result<List<SysUser>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) String status) {
        return Result.ok(userService.listUsers(page, size, keyword, deptId, status));
    }

    @Data
    public static class UserDTO {
        private SysUser user;
        private Long[] roleIds;
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public Result<Void> add(@RequestBody UserDTO dto) {
        userService.createUser(dto.getUser(), dto.getRoleIds());
        return Result.ok();
    }

    @Operation(summary = "修改用户")
    @PutMapping
    public Result<Void> edit(@RequestBody UserDTO dto) {
        userService.updateUser(dto.getUser(), dto.getRoleIds());
        return Result.ok();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{userId}")
    public Result<Void> remove(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return Result.ok();
    }

    @Operation(summary = "重置密码")
    @PutMapping("/resetPwd")
    public Result<Void> resetPwd(@RequestParam Long userId, @RequestParam String newPassword) {
        userService.resetPassword(userId, newPassword);
        return Result.ok();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/changePwd")
    public Result<Void> changePwd(@RequestParam String oldPassword, @RequestParam String newPassword) {
        Long userId = StpUtil.getLoginIdAsLong();
        userService.changePassword(userId, oldPassword, newPassword);
        return Result.ok();
    }

    @Operation(summary = "修改状态")
    @PutMapping("/changeStatus")
    public Result<Void> changeStatus(@RequestParam Long userId, @RequestParam String status) {
        userService.changeStatus(userId, status);
        return Result.ok();
    }
}
