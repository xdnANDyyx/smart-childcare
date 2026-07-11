package com.zhyyt.nursery.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhyyt.nursery.module.system.entity.SysDict;
import com.zhyyt.nursery.module.system.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    List<SysDictData> selectDictDataByType(@Param("dictType") String dictType);
}
