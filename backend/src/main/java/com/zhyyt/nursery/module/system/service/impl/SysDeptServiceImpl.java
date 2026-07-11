package com.zhyyt.nursery.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhyyt.nursery.common.exception.BusinessException;
import com.zhyyt.nursery.module.system.entity.SysDept;
import com.zhyyt.nursery.module.system.mapper.SysDeptMapper;
import com.zhyyt.nursery.module.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper deptMapper;

    @Override
    public List<SysDept> listDepts(String keyword) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(SysDept::getDeptName, keyword);
        }
        wrapper.orderByAsc(SysDept::getOrderNum);
        List<SysDept> depts = deptMapper.selectList(wrapper);
        return buildTree(depts);
    }

    @Override
    public void createDept(SysDept dept) {
        dept.setStatus("0");
        deptMapper.insert(dept);
    }

    @Override
    public void updateDept(SysDept dept) {
        deptMapper.updateById(dept);
    }

    @Override
    public void deleteDept(Long deptId) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getParentId, deptId);
        if (deptMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("存在子部门，不允许删除");
        }
        deptMapper.deleteById(deptId);
    }

    @Override
    public List<SysDept> buildTree(List<SysDept> depts) {
        return depts.stream()
                .filter(d -> d.getParentId() == null || d.getParentId() == 0L)
                .peek(d -> {
                    d.setChildren(findChildren(d, depts));
                    if (d.getChildren().isEmpty()) {
                        d.setChildren(null);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<SysDept> findChildren(SysDept parent, List<SysDept> all) {
        return all.stream()
                .filter(d -> parent.getDeptId().equals(d.getParentId()))
                .peek(d -> {
                    d.setChildren(findChildren(d, all));
                    if (d.getChildren().isEmpty()) {
                        d.setChildren(null);
                    }
                })
                .collect(Collectors.toList());
    }
}
