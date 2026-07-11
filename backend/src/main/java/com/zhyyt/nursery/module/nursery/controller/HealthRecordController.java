package com.zhyyt.nursery.module.nursery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhyyt.nursery.common.api.PageResult;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.nursery.entity.HealthRecord;
import com.zhyyt.nursery.module.nursery.mapper.HealthRecordMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "健康档案")
@RestController
@RequestMapping("/nursery/health")
@RequiredArgsConstructor
public class HealthRecordController {

    private final HealthRecordMapper healthMapper;

    @Operation(summary = "健康档案列表")
    @GetMapping("/list")
    public Result<PageResult<HealthRecord>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String recordType,
            @RequestParam(required = false) String specialType) {
        Page<HealthRecord> p = new Page<>(page, size);
        LambdaQueryWrapper<HealthRecord> wrapper = new LambdaQueryWrapper<>();
        if (studentId != null) {
            wrapper.eq(HealthRecord::getStudentId, studentId);
        }
        if (recordType != null && !recordType.isEmpty()) {
            wrapper.eq(HealthRecord::getRecordType, recordType);
        }
        if (specialType != null && !specialType.isEmpty()) {
            wrapper.eq(HealthRecord::getSpecialType, specialType);
        }
        wrapper.orderByDesc(HealthRecord::getRecordDate);
        Page<HealthRecord> result = healthMapper.selectPage(p, wrapper);
        return Result.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @Operation(summary = "新增健康档案")
    @PostMapping
    public Result<Void> add(@RequestBody HealthRecord record) {
        healthMapper.insert(record);
        return Result.ok();
    }

    @Operation(summary = "修改健康档案")
    @PutMapping
    public Result<Void> edit(@RequestBody HealthRecord record) {
        healthMapper.updateById(record);
        return Result.ok();
    }

    @Operation(summary = "删除健康档案")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        healthMapper.deleteById(id);
        return Result.ok();
    }
}
