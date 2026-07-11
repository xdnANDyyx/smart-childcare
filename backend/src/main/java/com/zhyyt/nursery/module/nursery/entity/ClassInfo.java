package com.zhyyt.nursery.module.nursery.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 班级信息
 */
@Data
@TableName("nur_class")
public class ClassInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 机构ID */
    private Long orgId;

    /** 班级名称 */
    private String className;

    /** 班级类型(0半天班 1全天班) */
    private String classType;

    /** 适合月龄范围 */
    private String ageRange;

    /** 班容量 */
    private Integer capacity;

    /** 主班老师ID */
    private Long mainTeacherId;

    /** 配班老师ID */
    private Long assistantTeacherId;

    /** 保育员ID */
    private Long caregiverId;

    /** 状态(0正常 1停用) */
    private String status;

    /** 排序 */
    private Integer orderNum;

    @TableLogic
    private String delFlag;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
