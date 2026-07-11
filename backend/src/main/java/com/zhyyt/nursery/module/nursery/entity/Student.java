package com.zhyyt.nursery.module.nursery.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 托育儿童
 */
@Data
@TableName("nur_student")
public class Student implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 所属机构ID */
    private Long orgId;

    /** 班级ID */
    private Long classId;

    /** 儿童姓名 */
    private String name;

    /** 性别(0男 1女) */
    private String gender;

    /** 出生日期 */
    private LocalDate birthDate;

    /** 证件类型 */
    private String idCardType;

    /** 证件号码 */
    private String idCardNo;

    /** 国籍 */
    private String nationality;

    /** 民族 */
    private String nation;

    /** 户籍类型(0城镇 1农村) */
    private String householdType;

    /** 入托日期 */
    private LocalDate enrollDate;

    /** 退托日期 */
    private LocalDate leaveDate;

    /** 状态(0在托 1离托 2待入托) */
    private String status;

    /** 头像 */
    private String avatar;

    /** 备注 */
    private String remark;

    @TableLogic
    private String delFlag;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;
}
