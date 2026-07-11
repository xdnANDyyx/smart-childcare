package com.zhyyt.nursery.module.nursery.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 出勤记录
 */
@Data
@TableName("nur_attendance")
public class Attendance implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 儿童ID */
    private Long studentId;

    /** 班级ID */
    private Long classId;

    /** 机构ID */
    private Long orgId;

    /** 考勤日期 */
    private LocalDate attendanceDate;

    /** 入园时间 */
    private LocalDateTime checkInTime;

    /** 离园时间 */
    private LocalDateTime checkOutTime;

    /** 出勤状态(0正常 1请假 2缺勤 3迟到 4早退) */
    private String status;

    /** 打卡方式(0人脸 1手工 2卡片) */
    private String checkType;

    /** 备注 */
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
