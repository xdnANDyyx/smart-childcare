package com.zhyyt.nursery.module.nursery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhyyt.nursery.common.api.PageResult;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.nursery.entity.NurseryLog;
import com.zhyyt.nursery.module.nursery.mapper.NurseryLogMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "保育日志")
@RestController
@RequestMapping("/nursery/log")
@RequiredArgsConstructor
public class NurseryLogController {

    private final NurseryLogMapper logMapper;

    @Operation(summary = "保育日志列表")
    @GetMapping("/list")
    public Result<PageResult<NurseryLog>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) String logType,
            @RequestParam(required = false) LocalDate logDate) {
        Page<NurseryLog> p = new Page<>(page, size);
        LambdaQueryWrapper<NurseryLog> wrapper = new LambdaQueryWrapper<>();
        if (studentId != null) {
            wrapper.eq(NurseryLog::getStudentId, studentId);
        }
        if (classId != null) {
            wrapper.eq(NurseryLog::getClassId, classId);
        }
        if (logType != null && !logType.isEmpty()) {
            wrapper.eq(NurseryLog::getLogType, logType);
        }
        if (logDate != null) {
            wrapper.eq(NurseryLog::getLogDate, logDate);
        }
        wrapper.orderByDesc(NurseryLog::getCreateTime);
        Page<NurseryLog> result = logMapper.selectPage(p, wrapper);
        return Result.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @Operation(summary = "新增保育日志")
    @PostMapping
    public Result<Void> add(@RequestBody NurseryLog log) {
        logMapper.insert(log);
        return Result.ok();
    }

    @Operation(summary = "修改保育日志")
    @PutMapping
    public Result<Void> edit(@RequestBody NurseryLog log) {
        logMapper.updateById(log);
        return Result.ok();
    }

    @Operation(summary = "删除保育日志")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        logMapper.deleteById(id);
        return Result.ok();
    }
}
