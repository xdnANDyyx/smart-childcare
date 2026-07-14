-- ============================================
-- 智慧托育综合应用平台 - 数据库初始化脚本
-- 修复：添加 SET NAMES 解决中文乱码问题
-- ============================================

-- 关键修复：强制客户端使用 UTF8MB4 编码读取本文件
SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS nursery DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE nursery;

-- ======== 系统管理表 ========

-- 部门表
CREATE TABLE IF NOT EXISTS sys_dept (
    dept_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id    BIGINT DEFAULT 0,
    dept_name    VARCHAR(50)  NOT NULL,
    order_num    INT          DEFAULT 0,
    leader       VARCHAR(20),
    phone        VARCHAR(20),
    email        VARCHAR(50),
    status       CHAR(1)      DEFAULT '0',
    del_flag     CHAR(1)      DEFAULT '0',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='部门表';

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    user_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    dept_id      BIGINT,
    username     VARCHAR(50)  NOT NULL,
    nickname     VARCHAR(50),
    password     VARCHAR(100) NOT NULL,
    phone        VARCHAR(20),
    email        VARCHAR(50),
    sex          CHAR(1)      DEFAULT '2',
    avatar       VARCHAR(200),
    status       CHAR(1)      DEFAULT '0',
    del_flag     CHAR(1)      DEFAULT '0',
    login_ip     VARCHAR(50),
    login_date   DATETIME,
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by    VARCHAR(50),
    update_by    VARCHAR(50),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    role_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name    VARCHAR(50)  NOT NULL,
    role_key     VARCHAR(100) NOT NULL,
    role_sort    INT          DEFAULT 0,
    status       CHAR(1)      DEFAULT '0',
    del_flag     CHAR(1)      DEFAULT '0',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by    VARCHAR(50),
    update_by    VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='角色表';

-- 菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    menu_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id    BIGINT       DEFAULT 0,
    menu_name    VARCHAR(50)  NOT NULL,
    menu_type    CHAR(1)      DEFAULT 'C',
    order_num    INT          DEFAULT 0,
    path         VARCHAR(200),
    component    VARCHAR(255),
    perms        VARCHAR(100),
    icon         VARCHAR(100),
    visible      CHAR(1)      DEFAULT '0',
    status       CHAR(1)      DEFAULT '0',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by    VARCHAR(50),
    update_by    VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='菜单表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id  BIGINT NOT NULL,
    role_id  BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id  BIGINT NOT NULL,
    menu_id  BIGINT NOT NULL,
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='角色菜单关联表';

-- 字典类型表
CREATE TABLE IF NOT EXISTS sys_dict_type (
    dict_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    dict_name    VARCHAR(100) NOT NULL,
    dict_type    VARCHAR(100) NOT NULL,
    status       CHAR(1)      DEFAULT '0',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by    VARCHAR(50),
    update_by    VARCHAR(50),
    UNIQUE KEY uk_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE IF NOT EXISTS sys_dict_data (
    dict_code    BIGINT AUTO_INCREMENT PRIMARY KEY,
    dict_sort    INT          DEFAULT 0,
    dict_label   VARCHAR(100) NOT NULL,
    dict_value   VARCHAR(100) NOT NULL,
    dict_type    VARCHAR(100) NOT NULL,
    css_class    VARCHAR(100),
    list_class   VARCHAR(100),
    is_default   CHAR(1)      DEFAULT 'N',
    status       CHAR(1)      DEFAULT '0',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='字典数据表';

-- ======== 托育业务表 ========

-- 机构表
CREATE TABLE IF NOT EXISTS nur_organization (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    org_name     VARCHAR(100) NOT NULL,
    org_type     VARCHAR(10),
    credit_code  VARCHAR(50),
    address      VARCHAR(200),
    phone        VARCHAR(20),
    principal    VARCHAR(50),
    logo         VARCHAR(200),
    longitude    DECIMAL(10,6),
    latitude     DECIMAL(10,6),
    status       CHAR(1)      DEFAULT '0',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='托育机构表';

-- 班级表
CREATE TABLE IF NOT EXISTS nur_class (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    org_id            BIGINT,
    class_name        VARCHAR(50)  NOT NULL,
    class_type        VARCHAR(10),
    age_range         VARCHAR(20),
    capacity          INT          DEFAULT 0,
    main_teacher_id   BIGINT,
    assistant_teacher_id BIGINT,
    caregiver_id      BIGINT,
    status            CHAR(1)      DEFAULT '0',
    order_num         INT          DEFAULT 0,
    del_flag          CHAR(1)      DEFAULT '0',
    create_time       DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='班级表';

-- 儿童表
CREATE TABLE IF NOT EXISTS nur_student (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    org_id        BIGINT,
    class_id      BIGINT,
    name          VARCHAR(50)  NOT NULL,
    gender        CHAR(1),
    birth_date    DATE,
    id_card_type  VARCHAR(10),
    id_card_no    VARCHAR(50),
    nationality   VARCHAR(50),
    nation        VARCHAR(20),
    household_type CHAR(1),
    enroll_date   DATE,
    leave_date    DATE,
    status        CHAR(1)      DEFAULT '2',
    avatar        VARCHAR(200),
    remark        VARCHAR(500),
    del_flag      CHAR(1)      DEFAULT '0',
    create_time   DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by     VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='儿童表';

-- 出勤记录表
CREATE TABLE IF NOT EXISTS nur_attendance (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id       BIGINT,
    class_id         BIGINT,
    org_id           BIGINT,
    attendance_date  DATE,
    check_in_time    DATETIME,
    check_out_time   DATETIME,
    status           CHAR(1)   DEFAULT '0',
    check_type       CHAR(1)   DEFAULT '0',
    remark           VARCHAR(200),
    create_time      DATETIME  DEFAULT CURRENT_TIMESTAMP,
    update_time      DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='出勤记录表';

-- 保育日志表
CREATE TABLE IF NOT EXISTS nur_nursery_log (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id    BIGINT,
    class_id      BIGINT,
    org_id        BIGINT,
    log_date      DATE,
    log_type      VARCHAR(20),
    content       TEXT,
    temperature   VARCHAR(10),
    recorder_id   BIGINT,
    recorder_name VARCHAR(50),
    images        TEXT,
    create_time   DATETIME  DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='保育日志表';

-- 招生线索表
CREATE TABLE IF NOT EXISTS nur_recruitment (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    org_id              BIGINT,
    child_name          VARCHAR(50),
    parent_name         VARCHAR(50),
    phone               VARCHAR(20),
    gender              CHAR(1),
    birth_date          DATE,
    intended_class_type VARCHAR(10),
    intention_level     INT          DEFAULT 1,
    channel             VARCHAR(50),
    tags                VARCHAR(500),
    follow_status       CHAR(1)      DEFAULT '0',
    last_follow_time    DATETIME,
    last_follow_content VARCHAR(500),
    owner_id            BIGINT,
    owner_name          VARCHAR(50),
    remark              VARCHAR(500),
    create_time         DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='招生线索表';

-- 收费项目表
CREATE TABLE IF NOT EXISTS nur_fee_item (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    org_id        BIGINT,
    item_name     VARCHAR(100) NOT NULL,
    item_type     VARCHAR(10),
    standard_fee  DECIMAL(10,2),
    billing_cycle VARCHAR(10),
    status        CHAR(1)      DEFAULT '0',
    order_num     INT          DEFAULT 0,
    remark        VARCHAR(200),
    create_time   DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='收费项目表';

-- 健康档案表
CREATE TABLE IF NOT EXISTS nur_health_record (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id        BIGINT,
    org_id            BIGINT,
    record_date       DATE,
    record_type       VARCHAR(10),
    height            DECIMAL(5,1),
    weight            DECIMAL(5,1),
    head_circumference DECIMAL(5,1),
    chest_circumference DECIMAL(5,1),
    vision_left       VARCHAR(10),
    vision_right      VARCHAR(10),
    hearing_left      VARCHAR(10),
    hearing_right     VARCHAR(10),
    temperature       VARCHAR(10),
    blood_type        VARCHAR(10),
    allergy_info      VARCHAR(500),
    medical_history   VARCHAR(500),
    growth_evaluation VARCHAR(500),
    special_type      VARCHAR(10),
    special_status    VARCHAR(10),
    remark            VARCHAR(500),
    create_time       DATETIME  DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT='健康档案表';

-- ======== 初始化数据 ========

-- 默认部门
INSERT INTO sys_dept (dept_id, parent_id, dept_name, order_num, leader, status) VALUES
(1, 0, '智慧托育中心', 0, '管理员', '0'),
(2, 1, '行政部', 1, NULL, '0'),
(3, 1, '教务部', 2, NULL, '0'),
(4, 1, '保健部', 3, NULL, '0'),
(5, 1, '后勤部', 4, NULL, '0');

-- 默认用户 (密码: admin123 的MD5)
INSERT INTO sys_user (user_id, dept_id, username, nickname, password, phone, sex, status, create_by) VALUES
(1, 1, 'admin', '超级管理员', '0192023a7bbd73250516f069df18b500', '13800138000', '0', '0', 'admin');

-- 默认角色
INSERT INTO sys_role (role_id, role_name, role_key, role_sort, status, create_by) VALUES
(1, '超级管理员', 'admin', 1, '0', 'admin'),
(2, '园长', 'principal', 2, '0', 'admin'),
(3, '老师', 'teacher', 3, '0', 'admin'),
(4, '保健医', 'doctor', 4, '0', 'admin'),
(5, '家长', 'parent', 5, '0', 'admin');

-- 默认用户角色关联
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 默认菜单
INSERT INTO sys_menu (menu_id, parent_id, menu_name, menu_type, order_num, path, component, perms, icon, visible, status, create_by) VALUES
-- 目录
(1,  0,   '工作台',     'M', 0, '/dashboard',  NULL,                 NULL,                  'Dashboard',       '0', '0', 'admin'),
(2,  0,   '系统管理',   'M', 1, '/system',    NULL,                 NULL,                  'Setting',         '0', '0', 'admin'),
(3,  0,   '托育管理',   'M', 2, '/nursery',   NULL,                 NULL,                  'School',          '0', '0', 'admin'),
(4,  0,   '招生管理',   'M', 3, '/recruit',   NULL,                 NULL,                  'Promotion',       '0', '0', 'admin'),
(5,  0,   '收费管理',   'M', 4, '/finance',   NULL,                 NULL,                  'Money',           '0', '0', 'admin'),
(6,  0,   '健康档案',   'M', 5, '/health',    NULL,                 NULL,                  'FirstAidKit',     '0', '0', 'admin'),
(7,  0,   '出勤管理',   'M', 6, '/attendance',NULL,                 NULL,                  'Calendar',        '0', '0', 'admin'),
(8,  0,   'IoT设备',    'M', 7, '/iot',       NULL,                 NULL,                  'Monitor',         '0', '0', 'admin'),
-- 工作台
(10, 1,   '控制台',     'C', 0, 'index',      'dashboard/index',    'dashboard:view',      'Odometer',        '0', '0', 'admin'),
-- 系统管理
(20, 2,   '用户管理',   'C', 0, 'user',       'system/user/index',  'system:user:list',   'User',            '0', '0', 'admin'),
(21, 2,   '角色管理',   'C', 1, 'role',       'system/role/index',  'system:role:list',   'UserFilled',      '0', '0', 'admin'),
(22, 2,   '菜单管理',   'C', 2, 'menu',       'system/menu/index',  'system:menu:list',   'Menu',            '0', '0', 'admin'),
(23, 2,   '部门管理',   'C', 3, 'dept',       'system/dept/index',  'system:dept:list',   'OfficeBuilding',  '0', '0', 'admin'),
(24, 2,   '字典管理',   'C', 4, 'dict',       'system/dict/index',  'system:dict:list',   'Collection',      '0', '0', 'admin'),
-- 托育管理
(30, 3,   '机构管理',   'C', 0, 'org',        'nursery/org/index',   'nursery:org:list',   'OfficeBuilding',  '0', '0', 'admin'),
(31, 3,   '班级管理',   'C', 1, 'class',      'nursery/class/index', 'nursery:class:list', 'Grid',            '0', '0', 'admin'),
(32, 3,   '儿童管理',   'C', 2, 'student',    'nursery/student/index','nursery:student:list','User',           '0', '0', 'admin'),
(33, 3,   '保育日志',   'C', 3, 'log',        'nursery/log/index',   'nursery:log:list',   'Document',        '0', '0', 'admin'),
-- 招生管理
(40, 4,   '招生线索',   'C', 0, 'lead',       'recruit/lead/index',  'recruit:lead:list',  'Pointer',         '0', '0', 'admin'),
-- 收费管理
(50, 5,   '收费项目',   'C', 0, 'item',       'finance/item/index',  'finance:item:list',  'Wallet',          '0', '0', 'admin'),
-- 健康档案
(60, 6,   '健康档案',   'C', 0, 'record',     'health/record/index', 'health:record:list', 'FirstAidKit',     '0', '0', 'admin'),
-- 出勤管理
(70, 7,   '考勤记录',   'C', 0, 'record',     'attendance/record/index','attendance:list','Calendar',        '0', '0', 'admin'),
-- IoT设备
(80, 8,   '设备管理',   'C', 0, 'device',     'iot/device/index',    'iot:device:list',    'Monitor',         '0', '0', 'admin');

-- 超级管理员拥有所有菜单权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu;

-- 字典数据
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by) VALUES
('性别', 'sys_user_sex', '0', 'admin'),
('用户状态', 'sys_normal_disable', '0', 'admin'),
('班级类型', 'nur_class_type', '0', 'admin'),
('儿童状态', 'nur_student_status', '0', 'admin'),
('出勤状态', 'nur_attendance_status', '0', 'admin'),
('日志类型', 'nur_log_type', '0', 'admin'),
('意向等级', 'nur_intention_level', '0', 'admin'),
('跟进状态', 'nur_follow_status', '0', 'admin'),
('收费类型', 'nur_fee_type', '0', 'admin'),
('计费周期', 'nur_billing_cycle', '0', 'admin'),
('健康记录类型', 'nur_health_record_type', '0', 'admin'),
('专案类型', 'nur_special_type', '0', 'admin');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, is_default, status) VALUES
(0, '男', '0', 'sys_user_sex', 'Y', '0'),
(1, '女', '1', 'sys_user_sex', 'N', '0'),
(2, '未知', '2', 'sys_user_sex', 'N', '0'),
(0, '正常', '0', 'sys_normal_disable', 'Y', '0'),
(1, '停用', '1', 'sys_normal_disable', 'N', '0'),
(0, '全天班', '1', 'nur_class_type', 'Y', '0'),
(1, '半天班', '0', 'nur_class_type', 'N', '0'),
(0, '在托', '0', 'nur_student_status', 'N', '0'),
(1, '离托', '1', 'nur_student_status', 'N', '0'),
(2, '待入托', '2', 'nur_student_status', 'Y', '0'),
(0, '正常', '0', 'nur_attendance_status', 'Y', '0'),
(1, '请假', '1', 'nur_attendance_status', 'N', '0'),
(2, '缺勤', '2', 'nur_attendance_status', 'N', '0'),
(3, '迟到', '3', 'nur_attendance_status', 'N', '0'),
(4, '早退', '4', 'nur_attendance_status', 'N', '0'),
(0, '进餐', 'meal', 'nur_log_type', 'N', '0'),
(1, '饮水', 'water', 'nur_log_type', 'N', '0'),
(2, '睡眠', 'sleep', 'nur_log_type', 'N', '0'),
(3, '如厕', 'toilet', 'nur_log_type', 'N', '0'),
(4, '情绪', 'mood', 'nur_log_type', 'N', '0'),
(5, '活动', 'activity', 'nur_log_type', 'N', '0'),
(1, '低', '1', 'nur_intention_level', 'N', '0'),
(2, '中', '2', 'nur_intention_level', 'Y', '0'),
(3, '高', '3', 'nur_intention_level', 'N', '0'),
(0, '新线索', '0', 'nur_follow_status', 'Y', '0'),
(1, '跟进中', '1', 'nur_follow_status', 'N', '0'),
(2, '已到访', '2', 'nur_follow_status', 'N', '0'),
(3, '已转化', '3', 'nur_follow_status', 'N', '0'),
(4, '已流失', '4', 'nur_follow_status', 'N', '0'),
(0, '托育费', '0', 'nur_fee_type', 'Y', '0'),
(1, '餐费', '1', 'nur_fee_type', 'N', '0'),
(2, '住宿费', '2', 'nur_fee_type', 'N', '0'),
(3, '教材费', '3', 'nur_fee_type', 'N', '0'),
(4, '保险费', '4', 'nur_fee_type', 'N', '0'),
(5, '其他', '5', 'nur_fee_type', 'N', '0'),
(0, '月', '0', 'nur_billing_cycle', 'Y', '0'),
(1, '季', '1', 'nur_billing_cycle', 'N', '0'),
(2, '学期', '2', 'nur_billing_cycle', 'N', '0'),
(3, '年', '3', 'nur_billing_cycle', 'N', '0'),
(4, '次', '4', 'nur_billing_cycle', 'N', '0'),
(0, '体检', '0', 'nur_health_record_type', 'Y', '0'),
(1, '体测', '1', 'nur_health_record_type', 'N', '0'),
(2, '专案', '2', 'nur_health_record_type', 'N', '0'),
(3, '日常', '3', 'nur_health_record_type', 'N', '0'),
(0, '体弱儿', '0', 'nur_special_type', 'N', '0'),
(1, '肥胖儿', '1', 'nur_special_type', 'N', '0'),
(2, '过敏', '2', 'nur_special_type', 'N', '0'),
(3, '其他', '3', 'nur_special_type', 'N', '0');