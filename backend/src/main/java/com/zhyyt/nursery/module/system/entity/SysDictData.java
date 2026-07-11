package com.zhyyt.nursery.module.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统字典数据
 */
@Data
@TableName("sys_dict_data")
public class SysDictData implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long dictCode;

    /** 字典排序 */
    private Integer dictSort;

    /** 字典标签 */
    private String dictLabel;

    /** 字典键值 */
    private String dictValue;

    /** 字典类型 */
    private String dictType;

    /** 样式属性 */
    private String cssClass;

    /** 回显样式 */
    private String listClass;

    /** 是否默认(0否 1是) */
    private String isDefault;

    /** 状态(0正常 1停用) */
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
