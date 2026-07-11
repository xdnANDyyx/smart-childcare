#!/bin/bash
# ============================================
# 智慧托育平台 - 一键部署脚本
# 在服务器上执行
# ============================================

set -e

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

print_info()  { echo -e "${CYAN}[INFO]${NC}  $1"; }
print_ok()    { echo -e "${GREEN}[OK]${NC}    $1"; }
print_warn()  { echo -e "${YELLOW}[WARN]${NC}  $1"; }
print_error() { echo -e "${RED}[ERROR]${NC} $1"; }

# 当前目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# ========== 检查 ==========
print_info "检查Docker环境..."
if ! command -v docker &> /dev/null; then
    print_error "Docker未安装！请先安装Docker。"
    print_info  "安装命令: curl -fsSL https://get.docker.com | sh"
    exit 1
fi
if ! docker compose version &> /dev/null; then
    print_error "Docker Compose未安装！请安装Docker Compose V2。"
    exit 1
fi
print_ok "Docker环境正常"

# ========== 加载环境变量 ==========
if [ ! -f .env ]; then
    print_warn ".env文件不存在，从模板创建..."
    cp .env.example .env
    print_warn "请编辑 .env 文件修改密码后重新运行此脚本！"
    print_info  "命令: vi .env"
    exit 0
fi

export $(grep -v '^#' .env | xargs)

# ========== 选择部署模式 ==========
echo ""
echo "请选择部署模式："
echo "  1) 单机部署（全部服务在一台机器，适合开发测试）"
echo "  2) 数据服务器（MySQL + RabbitMQ）"
echo "  3) 应用服务器（Backend + Redis + MinIO + Nginx）"
echo ""
read -p "请输入 [1-3]: " DEPLOY_MODE

case $DEPLOY_MODE in
    1)
        COMPOSE_FILE="docker-compose.yml"
        print_info "选择: 单机部署"
        ;;
    2)
        COMPOSE_FILE="docker-compose-data.yml"
        print_info "选择: 数据服务器部署"
        ;;
    3)
        COMPOSE_FILE="docker-compose-app.yml"
        print_info "选择: 应用服务器部署"
        # 应用服务器需要先构建后端镜像
        print_info "构建后端Docker镜像（可能需要几分钟）..."
        docker compose -f $COMPOSE_FILE build backend
        print_ok "后端镜像构建完成"
        ;;
    *)
        print_error "无效选择"
        exit 1
        ;;
esac

# ========== 初始化数据库（首次部署） ==========
if [ "$DEPLOY_MODE" = "1" ] || [ "$DEPLOY_MODE" = "2" ]; then
    print_info "检查数据库是否需要初始化..."
    # 复制SQL到MySQL初始化目录
    if [ -f ../sql/init.sql ]; then
        cp ../sql/init.sql ./mysql/init/init.sql
        print_ok "数据库初始化脚本已就绪（仅首次启动自动执行）"
    fi
fi

# ========== 启动服务 ==========
print_info "启动Docker服务..."
docker compose -f $COMPOSE_FILE --env-file .env up -d

print_info "等待服务启动..."
sleep 10

# ========== 检查状态 ==========
print_info "服务状态："
docker compose -f $COMPOSE_FILE ps

# ========== 输出访问信息 ==========
echo ""
echo "=============================================="
case $DEPLOY_MODE in
    1)
        echo "  单机部署完成！"
        echo ""
        echo "  Web管理端:  http://$(hostname -I | awk '{print $1}')"
        echo "  API文档:    http://$(hostname -I | awk '{print $1}')/api/doc.html"
        echo "  MySQL:      $(hostname -I | awk '{print $1}'):3306"
        echo "  RabbitMQ:   http://$(hostname -I | awk '{print $1}'):15672"
        echo "  MinIO:      http://$(hostname -I | awk '{print $1}'):9001"
        echo ""
        echo "  默认账号: admin / admin123"
        ;;
    2)
        echo "  数据服务器部署完成！"
        echo ""
        echo "  MySQL:    $(hostname -I | awk '{print $1}'):3306"
        echo "  RabbitMQ: http://$(hostname -I | awk '{print $1}'):15672"
        echo ""
        echo "  请在应用服务器的.env中设置 DATA_SERVER_IP=$(hostname -I | awk '{print $1}')"
        ;;
    3)
        echo "  应用服务器部署完成！"
        echo ""
        echo "  Web管理端:  http://$(hostname -I | awk '{print $1}')"
        echo "  API文档:    http://$(hostname -I | awk '{print $1}')/api/doc.html"
        echo ""
        echo "  默认账号: admin / admin123"
        ;;
esac
echo "=============================================="
echo ""
print_ok "部署完成！"
