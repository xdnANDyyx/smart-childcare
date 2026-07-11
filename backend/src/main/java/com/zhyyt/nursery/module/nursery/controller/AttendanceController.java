package com.zhyyt.nursery.module.nursery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhyyt.nursery.common.api.PageResult;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.nursery.entity.Attendance;
import com.zhyyt.nursery.module.nursery.mapper.AttendanceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "出勤管理")
@RestController
@RequestMapping("/nursery/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceMapper attendanceMapper;

    @Operation(summary = "出勤列表")
    @GetMapping("/list")
    public Result<PageResult<Attendance>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        Page<Attendance> p = new Page<>(page, size);
        LambdaQueryWrapper<Attendance> wrapper = new LambdaQueryWrapper<>();
        if (studentId != null) {
            wrapper.eq(Attendance::getStudentId, studentId);
        }
        if (classId != null) {
            wrapper.eq(Attendance::getClassId, classId);
        }
        if (startDate != null) {
            wrapper.ge(Attendance::getAttendanceDate, startDate);
        }
        if (endDate != null) {
            wrapper.le(Attendance::getAttendanceDate, endDate);
        }
        wrapper.orderByDesc(Attendance::getAttendanceDate);
        Page<Attendance> result = attendanceMapper.selectPage(p, wrapper);
        return Result.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @Operation(summary = "新增出勤记录")
    @PostMapping
    public Result<Void> add(@RequestBody Attendance attendance) {
        attendanceMapper.insert(attendance);
        return Result.ok();
    }

    @Operation(summary = "修改出勤记录")
    @PutMapping
    public Result<Void> edit(@RequestBody Attendance attendance) {
        attendanceMapper.updateById(attendance);
        return Result.ok();
    }

    @Operation(summary = "删除出勤记录")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        attendanceMapper.deleteById(id);
        return Result.ok();
    }
}
