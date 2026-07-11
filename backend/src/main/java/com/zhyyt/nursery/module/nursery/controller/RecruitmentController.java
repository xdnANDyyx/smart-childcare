package com.zhyyt.nursery.module.nursery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhyyt.nursery.common.api.PageResult;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.nursery.entity.Recruitment;
import com.zhyyt.nursery.module.nursery.mapper.RecruitmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "招生管理")
@RestController
@RequestMapping("/nursery/recruitment")
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentMapper recruitmentMapper;

    @Operation(summary = "招生线索列表")
    @GetMapping("/list")
    public Result<PageResult<Recruitment>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String followStatus,
            @RequestParam(required = false) Integer intentionLevel) {
        Page<Recruitment> p = new Page<>(page, size);
        LambdaQueryWrapper<Recruitment> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Recruitment::getChildName, keyword)
                    .or().like(Recruitment::getParentName, keyword)
                    .or().like(Recruitment::getPhone, keyword);
        }
        if (followStatus != null && !followStatus.isEmpty()) {
            wrapper.eq(Recruitment::getFollowStatus, followStatus);
        }
        if (intentionLevel != null) {
            wrapper.eq(Recruitment::getIntentionLevel, intentionLevel);
        }
        wrapper.orderByDesc(Recruitment::getCreateTime);
        Page<Recruitment> result = recruitmentMapper.selectPage(p, wrapper);
        return Result.ok(new PageResult<>(result.getTotal(), result.getRecords()));
    }

    @Operation(summary = "新增招生线索")
    @PostMapping
    public Result<Void> add(@RequestBody Recruitment recruitment) {
        if (recruitment.getFollowStatus() == null) {
            recruitment.setFollowStatus("0");
        }
        recruitmentMapper.insert(recruitment);
        return Result.ok();
    }

    @Operation(summary = "修改招生线索")
    @PutMapping
    public Result<Void> edit(@RequestBody Recruitment recruitment) {
        recruitmentMapper.updateById(recruitment);
        return Result.ok();
    }

    @Operation(summary = "删除招生线索")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        recruitmentMapper.deleteById(id);
        return Result.ok();
    }
}
