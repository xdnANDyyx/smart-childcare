package com.zhyyt.nursery.module.system.controller;

import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.module.system.entity.SysDict;
import com.zhyyt.nursery.module.system.entity.SysDictData;
import com.zhyyt.nursery.module.system.service.SysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "字典管理")
@RestController
@RequestMapping("/system/dict")
@RequiredArgsConstructor
public class SysDictController {

    private final SysDictService dictService;

    @Operation(summary = "字典类型列表")
    @GetMapping("/list")
    public Result<List<SysDict>> list(@RequestParam(required = false) String keyword) {
        return Result.ok(dictService.listDicts(keyword));
    }

    @Operation(summary = "新增字典类型")
    @PostMapping
    public Result<Void> add(@RequestBody SysDict dict) {
        dictService.createDict(dict);
        return Result.ok();
    }

    @Operation(summary = "修改字典类型")
    @PutMapping
    public Result<Void> edit(@RequestBody SysDict dict) {
        dictService.updateDict(dict);
        return Result.ok();
    }

    @Operation(summary = "删除字典类型")
    @DeleteMapping("/{dictId}")
    public Result<Void> remove(@PathVariable Long dictId) {
        dictService.deleteDict(dictId);
        return Result.ok();
    }

    @Operation(summary = "根据类型获取字典数据")
    @GetMapping("/data/{dictType}")
    public Result<List<SysDictData>> getDictData(@PathVariable String dictType) {
        return Result.ok(dictService.getDictDataByType(dictType));
    }

    @Operation(summary = "字典数据列表")
    @GetMapping("/data/list")
    public Result<List<SysDictData>> listDictData(@RequestParam String dictType) {
        return Result.ok(dictService.listDictData(dictType));
    }

    @Operation(summary = "新增字典数据")
    @PostMapping("/data")
    public Result<Void> addDictData(@RequestBody SysDictData dictData) {
        dictService.createDictData(dictData);
        return Result.ok();
    }

    @Operation(summary = "修改字典数据")
    @PutMapping("/data")
    public Result<Void> editDictData(@RequestBody SysDictData dictData) {
        dictService.updateDictData(dictData);
        return Result.ok();
    }

    @Operation(summary = "删除字典数据")
    @DeleteMapping("/data/{dictCode}")
    public Result<Void> removeDictData(@PathVariable Long dictCode) {
        dictService.deleteDictData(dictCode);
        return Result.ok();
    }
}
