package com.zhyyt.nursery.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhyyt.nursery.module.system.entity.SysDept;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    List<SysDept> listDepts(String keyword);

    void createDept(SysDept dept);

    void updateDept(SysDept dept);

    void deleteDept(Long deptId);

    List<SysDept> buildTree(List<SysDept> depts);
}
