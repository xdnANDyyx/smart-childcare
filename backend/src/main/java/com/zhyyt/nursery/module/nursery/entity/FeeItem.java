package com.zhyyt.nursery.module.nursery.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收费项目
 */
@Data
@TableName("nur_fee_item")
public class FeeItem implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 机构ID */
    private Long orgId;

    /** 项目名称 */
    private String itemName;

    /** 项目类型(0托育费 1餐费 2住宿费 3教材费 4保险费 5其他) */
    private String itemType;

    /** 收费标准(元) */
    private BigDecimal standardFee;

    /** 计费周期(0月 1季 2学期 3年 4次) */
    private String billingCycle;

    /** 状态(0正常 1停用) */
    private String status;

    /** 排序 */
    private Integer orderNum;

    /** 备注 */
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
