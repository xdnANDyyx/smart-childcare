package com.zhyyt.nursery.module.system.controller;

import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.system.entity.SysDept;
import com.zhyyt.nursery.module.system.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "部门管理")
@RestController
@RequestMapping("/system/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;

    @Operation(summary = "部门列表")
    @GetMapping("/list")
    public Result<List<SysDept>> list(@RequestParam(required = false) String keyword) {
        return Result.ok(deptService.listDepts(keyword));
    }

    @Operation(summary = "新增部门")
    @PostMapping
    public Result<Void> add(@RequestBody SysDept dept) {
        deptService.createDept(dept);
        return Result.ok();
    }

    @Operation(summary = "修改部门")
    @PutMapping
    public Result<Void> edit(@RequestBody SysDept dept) {
        deptService.updateDept(dept);
        return Result.ok();
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{deptId}")
    public Result<Void> remove(@PathVariable Long deptId) {
        deptService.deleteDept(deptId);
        return Result.ok();
    }
}
