# 智慧托育综合应用平台

> 面向托育机构的综合管理解决方案，包含 PC Web 管理端、移动端（老师/家长/管理者）和 IoT 设备集成。

## 技术栈

### 后端
| 组件 | 说明 |
|------|------|
| Java 17 | 开发语言 |
| Spring Boot 3.2 | 主框架 |
| MyBatis-Plus 3.5 | ORM 框架 |
| Sa-Token 1.38 | 权限认证 |
| RabbitMQ | 消息队列 |
| Redis | 缓存/会话 |
| MySQL 8.0 | 主数据库 |
| MinIO | 对象存储（照片/文件） |
| Knife4j | API 文档 |
| Druid | 数据库连接池 |
| Hutool | Java 工具集 |

### 前端 Web 管理端
| 组件 | 说明 |
|------|------|
| Vue 3 | 前端框架 |
| Vite 5 | 构建工具 |
| Element Plus | UI 组件库 |
| Pinia | 状态管理 |
| Vue Router 4 | 路由 |
| Axios | HTTP 请求 |
| ECharts | 图表 |

### 移动端
| 组件 | 说明 |
|------|------|
| uni-app (Vue 3) | 跨平台框架 |
| 微信小程序 / H5 / App | 多端支持 |

### 基础设施
| 组件 | 说明 |
|------|------|
| Docker Compose | 容器编排 |
| Nginx | 反向代理 |
| MySQL 8.0 | 数据库 |
| Redis 7 | 缓存 |
| RabbitMQ 3.13 | 消息队列 |
| MinIO | 对象存储 |

## 项目结构

```
1.5Wpro/
├── backend/                         # 后端 Spring Boot 项目
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/zhyyt/nursery/
│       │   ├── NurseryApplication.java       # 启动类
│       │   ├── common/                       # 公共模块（Result/异常/常量）
│       │   ├── config/                       # 配置类（MyBatis/Sa-Token/RabbitMQ/Redis/MinIO/WebSocket）
│       │   ├── module/
│       │   │   ├── system/                   # 系统管理模块
│       │   │   │   ├── controller/           # Auth/User/Role/Menu/Dept/Dict 控制器
│       │   │   │   ├── service/              # 服务接口及实现
│       │   │   │   ├── mapper/               # MyBatis Mapper
│       │   │   │   └── entity/               # 实体类
│       │   │   ├── nursery/                  # 托育业务模块
│       │   │   │   ├── controller/           # 机构/班级/儿童/出勤/保育日志/招生/收费/健康档案
│       │   │   │   ├── mapper/
│       │   │   │   └── entity/
│       │   │   └── iot/                      # IoT 设备集成模块
│       │   │       ├── controller/           # 人脸识别/穿戴设备/体测设备
│       │   │       └── mqtt/                 # MQTT 消息处理
│       │   └── utils/                        # 工具类（MinIO/WebSocket）
│       └── resources/
│           ├── application.yml               # 主配置
│           ├── application-dev.yml           # 开发环境
│           ├── application-prod.yml          # 生产环境
│           └── mapper/                       # MyBatis XML
│
├── frontend-web/                    # Vue 3 Web 管理端
│   ├── package.json
│   ├── vite.config.ts
│   ├── index.html
│   └── src/
│       ├── main.ts                           # 入口
│       ├── App.vue
│       ├── router/                           # 路由配置
│       ├── store/                            # Pinia 状态管理
│       ├── api/                              # API 接口定义
│       ├── utils/                            # 工具（请求封装）
│       ├── layout/                           # 布局组件
│       ├── views/
│       │   ├── login/                        # 登录页
│       │   ├── dashboard/                    # 工作台
│       │   ├── system/                       # 系统管理（用户/角色/菜单/部门/字典）
│       │   └── nursery/                      # 托育管理（机构/班级/儿童/日志/招生/收费/健康/出勤）
│       └── assets/                           # 样式/图片
│
├── frontend-mobile/                 # uni-app 移动端
│   ├── package.json
│   ├── manifest.json
│   ├── pages.json                           # 页面路由配置
│   ├── App.vue
│   ├── main.js
│   ├── pages/
│   │   ├── login/                            # 登录页
│   │   ├── teacher/                          # 老师端（首页/花名册/保育日志/通知）
│   │   ├── parent/                           # 家长端（首页/每日报告/考勤/账单/相册）
│   │   └── admin/                            # 管理者端（运营看板）
│   ├── api/
│   ├── store/
│   └── utils/
│
├── docker/                          # Docker 部署
│   ├── docker-compose.yml
│   ├── nginx/nginx.conf
│   └── mysql/
│
├── sql/
│   └── init.sql                             # 数据库初始化脚本
│
└── README.md
```

## 快速开始

### 1. 启动基础设施

```bash
cd docker
docker-compose up -d mysql redis rabbitmq minio
```

### 2. 初始化数据库

```bash
# 执行 SQL 脚本
mysql -h localhost -u root -proot123 nursery < sql/init.sql
```

### 3. 启动后端

```bash
cd backend
mvn clean package -DskipTests
java -jar target/nursery-platform.jar
# 或开发模式
mvn spring-boot:run
```

后端启动后访问：http://localhost:8080/api/doc.html (API 文档)

默认管理员账号：`admin` / `admin123`

### 4. 启动前端 Web

```bash
cd frontend-web
npm install
npm run dev
```

访问：http://localhost:3000

### 5. 启动移动端

```bash
cd frontend-mobile
npm install
# H5 开发模式
npm run dev:h5
# 微信小程序
npm run dev:mp-weixin
```

## 功能模块

### PC Web 管理端
- ✅ 工作台（数据看板）
- ✅ 系统管理（用户/角色/菜单/部门/字典）
- ✅ 托育管理（机构/班级/儿童/保育日志）
- ✅ 招生管理（线索管理）
- ✅ 收费管理（收费项目）
- ✅ 健康档案
- ✅ 出勤管理
- ✅ IoT 设备管理

### 移动端 - 老师端
- ✅ 工作台（快捷操作/待办）
- ✅ 儿童花名册
- ✅ 保育日志（进餐/饮水/睡眠/如厕/情绪/活动）
- ✅ 通知公告

### 移动端 - 家长端
- ✅ 首页（儿童信息/今日动态/通知）
- ✅ 每日报告
- ✅ 考勤记录（日历视图）
- ✅ 账单缴费
- ✅ 成长相册/作品集

### 移动端 - 管理者端
- ✅ 运营看板（KPI/收费统计/班级出勤）

### IoT 设备集成
- ✅ 人脸识别门禁（Webhook 回调）
- ✅ 穿戴式设备（定位/心率/运动量/睡眠）
- ✅ 体测设备

## API 接口

所有 API 以 `/api` 为前缀，主要接口包括：

| 模块 | 接口前缀 | 说明 |
|------|----------|------|
| 认证 | /api/auth | 登录/登出/验证码/用户信息 |
| 用户管理 | /api/system/user | 用户 CRUD |
| 角色管理 | /api/system/role | 角色 CRUD |
| 菜单管理 | /api/system/menu | 菜单 CRUD |
| 部门管理 | /api/system/dept | 部门 CRUD |
| 字典管理 | /api/system/dict | 字典 CRUD |
| 机构管理 | /api/nursery/org | 机构 CRUD |
| 班级管理 | /api/nursery/class | 班级 CRUD |
| 儿童管理 | /api/nursery/student | 儿童 CRUD |
| 出勤管理 | /api/nursery/attendance | 出勤记录 |
| 保育日志 | /api/nursery/log | 日志 CRUD |
| 招生管理 | /api/nursery/recruitment | 线索 CRUD |
| 收费项目 | /api/nursery/fee-item | 收费项目 CRUD |
| 健康档案 | /api/nursery/health | 健康档案 CRUD |
| IoT 设备 | /api/iot | 人脸/穿戴/体测回调 |
| 文件上传 | /api/file | 文件上传 |

## 默认账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 超级管理员 | admin | admin123 |

## 部署说明

### 开发环境
1. 启动 Docker 基础设施（MySQL/Redis/RabbitMQ/MinIO）
2. 后端 `mvn spring-boot:run`
3. 前端 `npm run dev`

### 生产环境
1. 后端打包：`mvn clean package -DskipTests`
2. 前端打包：`npm run build`
3. 执行 `docker-compose up -d` 启动全部服务
4. 修改 `application-prod.yml` 中的数据库/Redis/RabbitMQ 地址

## 开源组件引用

| 组件 | 许可证 |
|------|--------|
| Spring Boot | Apache 2.0 |
| MyBatis-Plus | Apache 2.0 |
| Sa-Token | MIT |
| Hutool | MulanPSL2 |
| Element Plus | MIT |
| Vue 3 | MIT |
| uni-app | Apache 2.0 |
