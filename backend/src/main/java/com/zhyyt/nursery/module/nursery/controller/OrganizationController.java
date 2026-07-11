package com.zhyyt.nursery.module.nursery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhyyt.nursery.common.api.PageResult;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.nursery.entity.Organization;
import com.zhyyt.nursery.module.nursery.mapper.OrganizationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "机构管理")
@RestController
@RequestMapping("/nursery/org")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationMapper orgMapper;

    @Operation(summary = "机构列表")
    @GetMapping("/list")
    public Result<PageResult<Organization>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        Page<Organization> p = new Page<>(page, size);
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Organization::getOrgName, keyword);
        }
        wrapper.orderByDesc(Organization::getCreateTime);
        Page<Organization> result = orgMapper.selectPage(p, wrapper);
        return Result.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @Operation(summary = "新增机构")
    @PostMapping
    public Result<Void> add(@RequestBody Organization org) {
        org.setStatus("0");
        orgMapper.insert(org);
        return Result.ok();
    }

    @Operation(summary = "修改机构")
    @PutMapping
    public Result<Void> edit(@RequestBody Organization org) {
        orgMapper.updateById(org);
        return Result.ok();
    }

    @Operation(summary = "删除机构")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        orgMapper.deleteById(id);
        return Result.ok();
    }

    @Operation(summary = "机构详情")
    @GetMapping("/{id}")
    public Result<Organization> detail(@PathVariable Long id) {
        return Result.ok(orgMapper.selectById(id));
    }

    @Operation(summary = "全部机构(下拉框)")
    @GetMapping("/all")
    public Result<List<Organization>> all() {
        return Result.ok(orgMapper.selectList(new LambdaQueryWrapper<Organization>()
                .eq(Organization::getStatus, "0")
                .orderByDesc(Organization::getCreateTime)));
    }
}
