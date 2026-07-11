package com.zhyyt.nursery.module.nursery.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 保育教育工作日志
 */
@Data
@TableName("nur_nursery_log")
public class NurseryLog implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 儿童ID */
    private Long studentId;

    /** 班级ID */
    private Long classId;

    /** 机构ID */
    private Long orgId;

    /** 记录日期 */
    private LocalDate logDate;

    /** 记录类型(meal进餐 water饮水 sleep睡眠 toilet如厕 mood情绪 activity活动) */
    private String logType;

    /** 记录内容 */
    private String content;

    /** 体温 */
    private String temperature;

    /** 记录人ID */
    private Long recorderId;

    /** 记录人姓名 */
    private String recorderName;

    /** 图片URL列表(JSON) */
    private String images;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
