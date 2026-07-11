#!/bin/bash
# ============================================
# 数据恢复脚本
# 用法: ./restore.sh <备份目录路径>
# 例如: ./restore.sh ./backup/20260710_140000
# ============================================

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

BACKUP_PATH=$1

if [ -z "$BACKUP_PATH" ]; then
    echo "用法: $0 <备份目录路径>"
    echo "可用备份:"
    ls -1 ./backup/ 2>/dev/null || echo "  暂无备份"
    exit 1
fi

if [ ! -d "$BACKUP_PATH" ]; then
    echo "错误: 备份目录不存在: $BACKUP_PATH"
    exit 1
fi

# 加载环境变量
if [ -f .env ]; then
    export $(grep -v '^#' .env | xargs)
fi

MYSQL_DB="${MYSQL_DATABASE:-nursery}"
MYSQL_USER="${MYSQL_USER:-nursery}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-nursery123}"

echo "=========================================="
echo "  智慧托育平台 - 数据恢复"
echo "  备份目录: $BACKUP_PATH"
echo "=========================================="
echo ""
echo "⚠️  警告: 此操作将覆盖现有数据！"
read -p "确认恢复? (输入 yes 继续): " CONFIRM
if [ "$CONFIRM" != "yes" ]; then
    echo "已取消"
    exit 0
fi

# ======== 恢复MySQL ========
SQL_FILE=$(find "$BACKUP_PATH" -name "mysql_*.sql.gz" | head -1)
if [ -n "$SQL_FILE" ]; then
    echo "[1/2] 恢复MySQL数据库..."
    echo "  解压: $SQL_FILE"
    gunzip -k "$SQL_FILE"
    SQL_FILE_UNZIPPED="${SQL_FILE%.gz}"

    echo "  导入数据..."
    docker exec -i nursery-mysql \
        mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$MYSQL_DB" < "$SQL_FILE_UNZIPPED"
    rm -f "$SQL_FILE_UNZIPPED"
    echo "  ✓ MySQL恢复完成"
else
    echo "[1/2] 未找到MySQL备份文件，跳过"
fi

# ======== 恢复MinIO ========
MINIO_FILE=$(find "$BACKUP_PATH" -name "minio_data_*.tar.gz" | head -1)
if [ -n "$MINIO_FILE" ]; then
    echo "[2/2] 恢复MinIO文件..."
    docker cp "$MINIO_FILE" nursery-minio:/tmp/restore.tar.gz
    docker exec nursery-minio tar xzf /tmp/restore.tar.gz -C /
    docker exec nursery-minio rm /tmp/restore.tar.gz
    echo "  ✓ MinIO恢复完成"
else
    echo "[2/2] 未找到MinIO备份文件，跳过"
fi

echo ""
echo "=========================================="
echo "  数据恢复完成！"
echo "  建议重启后端服务: docker restart nursery-backend"
echo "=========================================="
