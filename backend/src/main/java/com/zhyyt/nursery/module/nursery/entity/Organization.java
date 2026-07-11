package com.zhyyt.nursery.module.nursery.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 托育机构
 */
@Data
@TableName("nur_organization")
public class Organization implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 机构名称 */
    private String orgName;

    /** 机构类型(0全日托 1半日托 2计时托 3临时托) */
    private String orgType;

    /** 统一社会信用代码 */
    private String creditCode;

    /** 机构地址 */
    private String address;

    /** 联系电话 */
    private String phone;

    /** 负责人 */
    private String principal;

    /** 机构logo */
    private String logo;

    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;

    /** 状态(0正常 1停用) */
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
