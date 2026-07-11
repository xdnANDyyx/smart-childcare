package com.zhyyt.nursery.module.nursery.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 儿童健康档案
 */
@Data
@TableName("nur_health_record")
public class HealthRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 儿童ID */
    private Long studentId;

    /** 机构ID */
    private Long orgId;

    /** 记录日期 */
    private LocalDate recordDate;

    /** 记录类型(0体检 1体测 2专案 3日常) */
    private String recordType;

    /** 身高(cm) */
    private BigDecimal height;

    /** 体重(kg) */
    private BigDecimal weight;

    /** 头围(cm) */
    private BigDecimal headCircumference;

    /** 胸围(cm) */
    private BigDecimal chestCircumference;

    /** 视力左 */
    private String visionLeft;

    /** 视力右 */
    private String visionRight;

    /** 听力左 */
    private String hearingLeft;

    /** 听力右 */
    private String hearingRight;

    /** 体温 */
    private String temperature;

    /** 血型 */
    private String bloodType;

    /** 过敏信息 */
    private String allergyInfo;

    /** 既往病史 */
    private String medicalHistory;

    /** 生长发育评价 */
    private String growthEvaluation;

    /** 专案类型(0体弱儿 1肥胖儿 2过敏 3其他) */
    private String specialType;

    /** 专案状态(0进行中 1已结案) */
    private String specialStatus;

    /** 备注 */
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
