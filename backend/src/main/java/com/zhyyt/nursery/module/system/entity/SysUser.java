package com.zhyyt.nursery.module.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 用户账号 */
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 密码 */
    private String password;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 性别(0男 1女 2未知) */
    private String sex;

    /** 头像URL */
    private String avatar;

    /** 账号状态(0正常 1停用) */
    private String status;

    /** 删除标志(0存在 2删除) */
    @TableLogic
    private String delFlag;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private LocalDateTime loginDate;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
}
