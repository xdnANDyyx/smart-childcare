package com.zhyyt.nursery.common.constant;

/**
 * 系统常量
 */
public class Constants {

    /** 超级管理员ID */
    public static final long SUPER_ADMIN_ID = 1L;

    /** 超级管理员角色编码 */
    public static final String SUPER_ADMIN_ROLE = "admin";

    /** 缓存前缀 */
    public static final String CACHE_PREFIX = "nursery:";

    /** 验证码缓存key */
    public static final String CAPTCHA_KEY = CACHE_PREFIX + "captcha:";

    /** 登录验证码Redis key */
    public static final String LOGIN_TOKEN_KEY = CACHE_PREFIX + "login:token:";

    /** 默认密码 */
    public static final String DEFAULT_PASSWORD = "123456";

    /** 菜单类型 - 目录 */
    public static final String MENU_TYPE_DIR = "M";
    /** 菜单类型 - 菜单 */
    public static final String MENU_TYPE_MENU = "C";
    /** 菜单类型 - 按钮 */
    public static final String MENU_TYPE_BUTTON = "F";

    /** 是 */
    public static final int YES = 1;
    /** 否 */
    public static final int NO = 0;
}
