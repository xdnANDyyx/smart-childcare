# 智慧托育平台 - 部署操作手册

## 目录

1. [整体架构](#1-整体架构)
2. [开发机准备（Windows）](#2-开发机准备windows)
3. [单机部署（开发测试）](#3-单机部署开发测试)
4. [双机部署（生产环境）](#4-双机部署生产环境)
5. [日常运维操作](#5-日常运维操作)
6. [数据备份与恢复](#6-数据备份与恢复)
7. [迁移到新服务器](#7-迁移到新服务器)
8. [更新版本](#8-更新版本)
9. [常见问题](#9-常见问题)

---

## 1. 整体架构

### 单机模式（开发/测试）

```
┌──────────── 一台服务器 ────────────┐
│                                   │
│  Nginx(:80) ──→ Backend(:8080)    │
│       │              │            │
│       │     MySQL(:3306)          │
│       │     Redis(:6379)          │
│       │     RabbitMQ(:5672)       │
│       │     MinIO(:9000)          │
│                                   │
│  使用: docker-compose.yml         │
└───────────────────────────────────┘
```

### 双机模式（生产环境）

```
┌──── 数据服务器（一体机2）──────┐   ┌──── 应用服务器（一体机1）──────┐
│                                │   │                                │
│  MySQL(:3306)                  │   │  Nginx(:80) ──→ Backend(:8080) │
│  RabbitMQ(:5672)               │←→│  Redis(:6379)                  │
│                                │   │  MinIO(:9000)                  │
│  Xeon 4309Y / 16GB            │   │  Xeon 4314 / 32GB             │
│  使用: docker-compose-data.yml │   │  使用: docker-compose-app.yml │
└────────────────────────────────┘   └────────────────────────────────┘
```

---

## 2. 开发机准备（Windows）

### 2.1 安装必要工具

在你的 Windows 开发机上需要安装：

| 工具 | 用途 | 下载地址 |
|------|------|----------|
| JDK 17 | 编译后端 | https://adoptium.net/ |
| Maven | 后端依赖管理 | https://maven.apache.org/ |
| Node.js 18+ | 编译前端 | https://nodejs.org/ |
| Git | 版本管理 | https://git-scm.com/ |

### 2.2 构建前端

```powershell
# 在项目根目录执行
cd d:\Business\1.5Wpro
.\build-frontend.ps1
```

或者手动执行：
```powershell
cd frontend-web
npm install
npm run build
# 构建产物在 frontend-web\dist\ 目录
```

### 2.3 上传到服务器

将整个项目目录上传到服务器：

```powershell
# 方法1: 使用 scp
scp -r d:\Business\1.5Wpro root@服务器IP:/opt/nursery

# 方法2: 打包后上传（推荐）
cd d:\Business
tar -czf nursery.tar.gz 1.5Wpro
# 然后用 WinSCP/FileZilla 上传 nursery.tar.gz 到服务器
# 在服务器上解压:
#   tar -xzf nursery.tar.gz -C /opt/
#   mv /opt/1.5Wpro /opt/nursery
```

---

## 3. 单机部署（开发测试）

> 全部服务跑在一台机器上，最简单的部署方式。

### 3.1 服务器安装 Docker

```bash
# 在服务器上执行（Ubuntu/CentOS通用）
curl -fsSL https://get.docker.com | sh
systemctl enable docker
systemctl start docker

# 验证
docker --version
docker compose version
```

### 3.2 配置环境变量

```bash
cd /opt/nursery/docker

# 从模板创建配置文件
cp .env.example .env

# 编辑配置（修改密码等）
vi .env
```

`.env` 文件内容示例：
```ini
MYSQL_ROOT_PASSWORD=Root@Nursery2026
MYSQL_DATABASE=nursery
MYSQL_USER=nursery
MYSQL_PASSWORD=Nursery@Prod2026
REDIS_PASSWORD=Redis@Nursery2026
RABBITMQ_USER=nursery
RABBITMQ_PASSWORD=Rabbitmq@Nursery2026
MINIO_ROOT_USER=nursery_minio
MINIO_ROOT_PASSWORD=Minio@Nursery2026
MINIO_BUCKET=nursery
```

### 3.3 一键部署

```bash
cd /opt/nursery/docker

# 给脚本执行权限
chmod +x deploy.sh backup.sh restore.sh

# 执行部署
./deploy.sh
# 选择 1) 单机部署
```

脚本会自动：
1. 检查 Docker 环境
2. 构建后端 Docker 镜像
3. 复制 SQL 初始化脚本
4. 启动全部容器
5. 显示访问信息

### 3.4 验证部署

```bash
# 查看容器状态
docker compose -f docker-compose.yml ps

# 查看后端日志
docker logs nursery-backend -f --tail 100

# 访问验证
curl http://localhost/api/auth/captcha
# 应返回JSON数据
```

浏览器访问：
- Web管理端: `http://服务器IP`
- API文档: `http://服务器IP/api/doc.html`
- RabbitMQ管理: `http://服务器IP:15672`
- MinIO控制台: `http://服务器IP:9001`
- 默认账号: `admin` / `admin123`

---

## 4. 双机部署（生产环境）

> 按照硬件规格，部署在两台一体机上。

### 4.1 准备网络

确保两台服务器在同一局域网，互相能访问：

```bash
# 在数据服务器上查看IP
ip addr
# 假设: 192.168.1.100

# 在应用服务器上查看IP
ip addr
# 假设: 192.168.1.101

# 互相ping测试
ping 192.168.1.100  # 从应用服务器ping数据服务器
ping 192.168.1.101  # 从数据服务器ping应用服务器
```

### 4.2 部署数据服务器（一体机2）

```bash
# SSH登录数据服务器
ssh root@192.168.1.100

# 安装Docker
curl -fsSL https://get.docker.com | sh

# 上传项目代码到数据服务器
# （参考第2.3节的上传方法）

# 进入部署目录
cd /opt/nursery/docker
cp .env.example .env
vi .env  # 修改密码

# 部署
./deploy.sh
# 选择 2) 数据服务器
```

验证：
```bash
# MySQL可以连接
docker exec nursery-mysql mysql -unursery -p你的密码 -e "SHOW DATABASES;"

# RabbitMQ管理页面
curl http://localhost:15672
```

### 4.3 部署应用服务器（一体机1）

```bash
# SSH登录应用服务器
ssh root@192.168.1.101

# 安装Docker
curl -fsSL https://get.docker.com | sh

# 上传项目代码

# 进入部署目录
cd /opt/nursery/docker
cp .env.example .env
vi .env
```

**关键配置**：在 `.env` 中设置数据服务器的IP：
```ini
# 添加这一行，指向数据服务器的IP
DATA_SERVER_IP=192.168.1.100

# 同时确保密码与数据服务器一致
MYSQL_USER=nursery
MYSQL_PASSWORD=Nursery@Prod2026
RABBITMQ_USER=nursery
RABBITMQ_PASSWORD=Rabbitmq@Nursery2026
```

部署：
```bash
./deploy.sh
# 选择 3) 应用服务器
```

验证：
```bash
# 检查后端能否连接数据服务器的MySQL
docker logs nursery-backend -f --tail 50
# 看到 "启动成功" 即可

# 浏览器访问
# http://192.168.1.101
```

---

## 5. 日常运维操作

### 5.1 常用命令

```bash
cd /opt/nursery/docker

# ---- 查看状态 ----
docker compose -f docker-compose.yml ps          # 查看容器状态
docker stats                                      # 查看资源占用
docker logs nursery-backend -f --tail 100         # 查看后端日志
docker logs nursery-mysql -f --tail 50            # 查看MySQL日志

# ---- 启停服务 ----
docker compose -f docker-compose.yml start        # 启动全部
docker compose -f docker-compose.yml stop         # 停止全部
docker compose -f docker-compose.yml restart      # 重启全部
docker restart nursery-backend                    # 只重启后端

# ---- 单个服务操作 ----
docker compose -f docker-compose.yml stop backend # 停止后端
docker compose -f docker-compose.yml start backend # 启动后端
```

### 5.2 查看各服务管理界面

| 服务 | 地址 | 默认账号 |
|------|------|----------|
| Web管理端 | http://服务器IP | admin / admin123 |
| API文档 | http://服务器IP/api/doc.html | - |
| RabbitMQ | http://服务器IP:15672 | 见.env配置 |
| MinIO | http://服务器IP:9001 | 见.env配置 |
| MySQL | 服务器IP:3306 | 见.env配置 |

### 5.3 进入容器调试

```bash
# 进入MySQL容器
docker exec -it nursery-mysql mysql -unursery -p

# 进入后端容器
docker exec -it nursery-backend bash

# 进入Redis容器
docker exec -it nursery-redis redis-cli

# 进入Nginx容器
docker exec -it nursery-nginx sh
```

---

## 6. 数据备份与恢复

### 6.1 手动备份

```bash
cd /opt/nursery/docker

# 备份全部（MySQL + MinIO）
./backup.sh all

# 只备份MySQL
./backup.sh mysql

# 只备份MinIO
./backup.sh minio
```

备份文件保存在 `docker/backup/YYYYMMDD_HHMMSS/` 目录。

### 6.2 自动定时备份

```bash
# 编辑crontab
crontab -e

# 每天凌晨2点自动备份
0 2 * * * cd /opt/nursery/docker && ./backup.sh all >> /var/log/nursery-backup.log 2>&1

# 每周日凌晨3点备份（全量）
0 3 * * 0 cd /opt/nursery/docker && ./backup.sh all >> /var/log/nursery-backup.log 2>&1
```

### 6.3 恢复数据

```bash
cd /opt/nursery/docker

# 查看可用备份
ls backup/

# 恢复指定备份
./restore.sh ./backup/20260710_140000

# 恢复后重启服务
docker restart nursery-backend
```

---

## 7. 迁移到新服务器

> 这是 Docker 部署最大的优势——迁移极其简单。

### 步骤

```bash
# ===== 第1步: 在旧服务器上备份 =====
cd /opt/nursery/docker
./backup.sh all
# 确认备份文件存在
ls -lh backup/

# ===== 第2步: 在新服务器上安装Docker =====
curl -fsSL https://get.docker.com | sh

# ===== 第3步: 将项目代码传到新服务器 =====
# 方法1: scp直接传
scp -r /opt/nursery root@新服务器IP:/opt/nursery

# 方法2: 打包传输
tar -czf nursery-full.tar.gz /opt/nursery
scp nursery-full.tar.gz root@新服务器IP:/
# 在新服务器解压
# tar -xzf /nursery-full.tar.gz -C /

# ===== 第4步: 将备份文件传到新服务器 =====
scp -r /opt/nursery/docker/backup root@新服务器IP:/opt/nursery/docker/

# ===== 第5步: 在新服务器上启动服务 =====
cd /opt/nursery/docker
./deploy.sh
# 选择对应的部署模式

# ===== 第6步: 恢复数据 =====
# 等服务全部启动后
./restore.sh ./backup/最新的备份目录名/

# ===== 第7步: 验证 =====
docker compose ps
curl http://localhost/api/auth/captcha
# 浏览器访问 http://新服务器IP
```

**整个过程只需10-15分钟，无需重新配置环境。**

---

## 8. 更新版本

### 8.1 只更新后端代码

```bash
# 在开发机上重新打包
cd d:\Business\1.5Wpro\backend
mvn clean package -DskipTests

# 上传jar包到服务器
scp target/nursery-platform.jar root@服务器IP:/opt/nursery/backend/target/

# 在服务器上重新构建镜像并重启
cd /opt/nursery/docker
docker compose -f docker-compose.yml build backend
docker compose -f docker-compose.yml up -d backend
```

### 8.2 只更新前端代码

```bash
# 在开发机上重新构建前端
cd d:\Business\1.5Wpro\frontend-web
npm run build

# 上传dist目录到服务器
scp -r dist/* root@服务器IP:/opt/nursery/frontend-web/dist/

# 不需要重启任何服务，Nginx会直接读取新文件
# 如果浏览器缓存，Ctrl+F5刷新
```

### 8.3 更新全部代码

```bash
# 开发机: 构建前端
.\build-frontend.ps1

# 上传整个项目到服务器（覆盖旧代码）
scp -r d:\Business\1.5Wpro\* root@服务器IP:/opt/nursery/

# 服务器: 重新构建并重启
cd /opt/nursery/docker
docker compose -f docker-compose.yml build backend
docker compose -f docker-compose.yml up -d
```

---

## 9. 常见问题

### Q: 后端启动后立即退出

```bash
# 查看日志
docker logs nursery-backend

# 常见原因:
# 1. MySQL还没启动好 → 等30秒后重启: docker restart nursery-backend
# 2. 密码不对 → 检查 .env 文件
# 3. 端口被占用 → netstat -tlnp | grep 8080
```

### Q: MySQL首次启动很慢

```bash
# 正常现象，MySQL需要初始化数据库和用户
# 查看 initialization 进度:
docker logs nursery-mysql -f
# 看到 "ready for connections" 即可
```

### Q: 前端页面空白

```bash
# 检查 dist 目录是否有文件
ls -la /opt/nursery/frontend-web/dist/

# 如果为空，说明前端没有构建
# 在开发机上执行 npm run build，然后上传 dist 目录
```

### Q: 双机部署后端连不上MySQL

```bash
# 1. 检查数据服务器防火墙
ufw allow 3306  # Ubuntu
# 或
firewall-cmd --add-port=3306/tcp --permanent  # CentOS
firewall-cmd --reload

# 2. 检查网络连通性
# 在应用服务器上执行:
ping 数据服务器IP
telnet 数据服务器IP 3306

# 3. 检查 .env 中的 DATA_SERVER_IP 是否正确
```

### Q: Docker占用磁盘空间过大

```bash
# 清理无用的镜像和容器
docker system prune -a

# 查看磁盘占用
docker system df

# 清理旧备份
cd /opt/nursery/docker
find backup/ -type d -mtime +30 -exec rm -rf {} \;
```

### Q: 如何修改端口

```bash
# 编辑 docker-compose.yml，修改 ports 映射
# 例如将80改为8080:
#   ports:
#     - "8080:80"    # 左边是宿主机端口，右边是容器端口

# 修改后重启
docker compose -f docker-compose.yml up -d
```

### Q: 如何查看数据库内容

```bash
# 方法1: 命令行
docker exec -it nursery-mysql mysql -unursery -p密码 nursery
mysql> SHOW TABLES;
mysql> SELECT * FROM sys_user;

# 方法2: 用Navicat/DBeaver从开发机连接
# 主机: 服务器IP  端口: 3306  用户: nursery  密码: 见.env
```

---

## 快速参考卡

```bash
# === 部署 ===
cd /opt/nursery/docker && ./deploy.sh

# === 状态 ===
docker compose -f docker-compose.yml ps

# === 日志 ===
docker logs nursery-backend -f --tail 100

# === 备份 ===
./backup.sh all

# === 恢复 ===
./restore.sh ./backup/20260710_140000

# === 重启 ===
docker compose -f docker-compose.yml restart

# === 停止 ===
docker compose -f docker-compose.yml stop

# === 更新后端 ===
docker compose -f docker-compose.yml build backend && docker compose -f docker-compose.yml up -d backend
```
