package com.zhyyt.nursery.module.nursery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhyyt.nursery.common.api.PageResult;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.nursery.entity.Student;
import com.zhyyt.nursery.module.nursery.mapper.StudentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "儿童管理")
@RestController
@RequestMapping("/nursery/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentMapper studentMapper;

    @Operation(summary = "儿童列表")
    @GetMapping("/list")
    public Result<PageResult<Student>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) String status) {
        Page<Student> p = new Page<>(page, size);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Student::getName, keyword);
        }
        if (classId != null) {
            wrapper.eq(Student::getClassId, classId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq(Student::getStatus, status);
        }
        wrapper.orderByDesc(Student::getCreateTime);
        Page<Student> result = studentMapper.selectPage(p, wrapper);
        return Result.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @Operation(summary = "新增儿童")
    @PostMapping
    public Result<Void> add(@RequestBody Student student) {
        if (student.getStatus() == null) {
            student.setStatus("2"); // 待入托
        }
        studentMapper.insert(student);
        return Result.ok();
    }

    @Operation(summary = "修改儿童")
    @PutMapping
    public Result<Void> edit(@RequestBody Student student) {
        studentMapper.updateById(student);
        return Result.ok();
    }

    @Operation(summary = "删除儿童")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        studentMapper.deleteById(id);
        return Result.ok();
    }

    @Operation(summary = "儿童详情")
    @GetMapping("/{id}")
    public Result<Student> detail(@PathVariable Long id) {
        return Result.ok(studentMapper.selectById(id));
    }
}
