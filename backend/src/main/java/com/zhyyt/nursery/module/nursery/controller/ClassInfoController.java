package com.zhyyt.nursery.module.nursery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.nursery.entity.ClassInfo;
import com.zhyyt.nursery.module.nursery.mapper.ClassInfoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "班级管理")
@RestController
@RequestMapping("/nursery/class")
@RequiredArgsConstructor
public class ClassInfoController {

    private final ClassInfoMapper classMapper;

    @Operation(summary = "班级列表")
    @GetMapping("/list")
    public Result<List<ClassInfo>> list(
            @RequestParam(required = false) Long orgId,
            @RequestParam(required = false) String status) {
        LambdaQueryWrapper<ClassInfo> wrapper = new LambdaQueryWrapper<>();
        if (orgId != null) {
            wrapper.eq(ClassInfo::getOrgId, orgId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq(ClassInfo::getStatus, status);
        }
        wrapper.orderByAsc(ClassInfo::getOrderNum);
        return Result.ok(classMapper.selectList(wrapper));
    }

    @Operation(summary = "新增班级")
    @PostMapping
    public Result<Void> add(@RequestBody ClassInfo classInfo) {
        classInfo.setStatus("0");
        classMapper.insert(classInfo);
        return Result.ok();
    }

    @Operation(summary = "修改班级")
    @PutMapping
    public Result<Void> edit(@RequestBody ClassInfo classInfo) {
        classMapper.updateById(classInfo);
        return Result.ok();
    }

    @Operation(summary = "删除班级")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        classMapper.deleteById(id);
        return Result.ok();
    }
}
