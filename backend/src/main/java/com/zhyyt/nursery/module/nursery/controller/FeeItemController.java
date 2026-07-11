package com.zhyyt.nursery.module.nursery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.nursery.entity.FeeItem;
import com.zhyyt.nursery.module.nursery.mapper.FeeItemMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "收费项目管理")
@RestController
@RequestMapping("/nursery/fee-item")
@RequiredArgsConstructor
public class FeeItemController {

    private final FeeItemMapper feeItemMapper;

    @Operation(summary = "收费项目列表")
    @GetMapping("/list")
    public Result<List<FeeItem>> list(
            @RequestParam(required = false) Long orgId,
            @RequestParam(required = false) String status) {
        LambdaQueryWrapper<FeeItem> wrapper = new LambdaQueryWrapper<>();
        if (orgId != null) {
            wrapper.eq(FeeItem::getOrgId, orgId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq(FeeItem::getStatus, status);
        }
        wrapper.orderByAsc(FeeItem::getOrderNum);
        return Result.ok(feeItemMapper.selectList(wrapper));
    }

    @Operation(summary = "新增收费项目")
    @PostMapping
    public Result<Void> add(@RequestBody FeeItem feeItem) {
        feeItem.setStatus("0");
        feeItemMapper.insert(feeItem);
        return Result.ok();
    }

    @Operation(summary = "修改收费项目")
    @PutMapping
    public Result<Void> edit(@RequestBody FeeItem feeItem) {
        feeItemMapper.updateById(feeItem);
        return Result.ok();
    }

    @Operation(summary = "删除收费项目")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        feeItemMapper.deleteById(id);
        return Result.ok();
    }
}
