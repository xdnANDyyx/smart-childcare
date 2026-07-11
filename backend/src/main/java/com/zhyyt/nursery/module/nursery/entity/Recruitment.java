package com.zhyyt.nursery.module.nursery.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 招生线索
 */
@Data
@TableName("nur_recruitment")
public class Recruitment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 机构ID */
    private Long orgId;

    /** 儿童姓名 */
    private String childName;

    /** 家长姓名 */
    private String parentName;

    /** 联系电话 */
    private String phone;

    /** 性别 */
    private String gender;

    /** 出生日期 */
    private LocalDate birthDate;

    /** 意向班级类型 */
    private String intendedClassType;

    /** 意向等级(1低 2中 3高) */
    private Integer intentionLevel;

    /** 招生渠道 */
    private String channel;

    /** 标签(JSON数组) */
    private String tags;

    /** 跟进状态(0新线索 1跟进中 2已到访 3已转化 4已流失) */
    private String followStatus;

    /** 最后跟进时间 */
    private LocalDateTime lastFollowTime;

    /** 最后跟进内容 */
    private String lastFollowContent;

    /** 负责人ID */
    private Long ownerId;

    /** 负责人姓名 */
    private String ownerName;

    /** 备注 */
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
