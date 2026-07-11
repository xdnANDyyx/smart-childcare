package com.zhyyt.nursery.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhyyt.nursery.module.system.entity.SysDict;
import com.zhyyt.nursery.module.system.entity.SysDictData;

import java.util.List;

public interface SysDictService extends IService<SysDict> {

    List<SysDict> listDicts(String keyword);

    void createDict(SysDict dict);

    void updateDict(SysDict dict);

    void deleteDict(Long dictId);

    List<SysDictData> getDictDataByType(String dictType);

    List<SysDictData> listDictData(String dictType);

    void createDictData(SysDictData dictData);

    void updateDictData(SysDictData dictData);

    void deleteDictData(Long dictCode);
}
