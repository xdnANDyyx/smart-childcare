package com.zhyyt.nursery.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhyyt.nursery.module.system.entity.SysDict;
import com.zhyyt.nursery.module.system.entity.SysDictData;
import com.zhyyt.nursery.module.system.mapper.SysDictMapper;
import com.zhyyt.nursery.module.system.mapper.SysDictDataMapper;
import com.zhyyt.nursery.module.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.zhyyt.nursery.common.constant.Constants;

@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final SysDictMapper dictMapper;
    private final SysDictDataMapper dictDataMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<SysDict> listDicts(String keyword) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(SysDict::getDictName, keyword).or().like(SysDict::getDictType, keyword);
        }
        wrapper.orderByDesc(SysDict::getCreateTime);
        return dictMapper.selectList(wrapper);
    }

    @Override
    public void createDict(SysDict dict) {
        dict.setStatus("0");
        dictMapper.insert(dict);
    }

    @Override
    public void updateDict(SysDict dict) {
        // 清除旧缓存
        SysDict old = dictMapper.selectById(dict.getDictId());
        if (old != null && !old.getDictType().equals(dict.getDictType())) {
            redisTemplate.delete(Constants.CACHE_PREFIX + "dict:" + old.getDictType());
        }
        dictMapper.updateById(dict);
        redisTemplate.delete(Constants.CACHE_PREFIX + "dict:" + dict.getDictType());
    }

    @Override
    public void deleteDict(Long dictId) {
        SysDict dict = dictMapper.selectById(dictId);
        if (dict != null) {
            redisTemplate.delete(Constants.CACHE_PREFIX + "dict:" + dict.getDictType());
        }
        dictMapper.deleteById(dictId);
    }

    @Override
    public List<SysDictData> getDictDataByType(String dictType) {
        String cacheKey = Constants.CACHE_PREFIX + "dict:" + dictType;
        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return (List<SysDictData>) cached;
        }
        List<SysDictData> list = dictMapper.selectDictDataByType(dictType);
        redisTemplate.opsForValue().set(cacheKey, list, 1, TimeUnit.HOURS);
        return list;
    }

    @Override
    public List<SysDictData> listDictData(String dictType) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dictType)) {
            wrapper.eq(SysDictData::getDictType, dictType);
        }
        wrapper.orderByAsc(SysDictData::getDictSort);
        return dictDataMapper.selectList(wrapper);
    }

    @Override
    public void createDictData(SysDictData dictData) {
        dictDataMapper.insert(dictData);
        redisTemplate.delete(Constants.CACHE_PREFIX + "dict:" + dictData.getDictType());
    }

    @Override
    public void updateDictData(SysDictData dictData) {
        dictDataMapper.updateById(dictData);
        redisTemplate.delete(Constants.CACHE_PREFIX + "dict:" + dictData.getDictType());
    }

    @Override
    public void deleteDictData(Long dictCode) {
        dictDataMapper.deleteById(dictCode);
    }
}
