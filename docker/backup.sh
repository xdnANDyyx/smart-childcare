#!/bin/bash
# ============================================
# 数据备份脚本
# 备份: MySQL数据库 + MinIO文件
# 用法: ./backup.sh [all|mysql|minio]
# ============================================

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

BACKUP_DIR="./backup"
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_SUBDIR="$BACKUP_DIR/$DATE"

mkdir -p "$BACKUP_SUBDIR"

# 加载环境变量
if [ -f .env ]; then
    export $(grep -v '^#' .env | xargs)
fi

BACKUP_TYPE=${1:-all}

echo "=========================================="
echo "  智慧托育平台 - 数据备份"
echo "  时间: $(date '+%Y-%m-%d %H:%M:%S')"
echo "  类型: $BACKUP_TYPE"
echo "=========================================="

# ======== MySQL备份 ========
if [ "$BACKUP_TYPE" = "all" ] || [ "$BACKUP_TYPE" = "mysql" ]; then
    echo "[1/2] 备份MySQL数据库..."

    MYSQL_CONTAINER="nursery-mysql"
    MYSQL_DB="${MYSQL_DATABASE:-nursery}"
    MYSQL_USER="${MYSQL_USER:-nursery}"
    MYSQL_PASSWORD="${MYSQL_PASSWORD:-nursery123}"

    if docker ps | grep -q "$MYSQL_CONTAINER"; then
        docker exec "$MYSQL_CONTAINER" \
            mysqldump -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" \
            --single-transaction --routines --triggers --events \
            "$MYSQL_DB" > "$BACKUP_SUBDIR/mysql_${MYSQL_DB}_${DATE}.sql"

        # 压缩
        gzip "$BACKUP_SUBDIR/mysql_${MYSQL_DB}_${DATE}.sql"
        SIZE=$(du -h "$BACKUP_SUBDIR/mysql_${MYSQL_DB}_${DATE}.sql.gz" | awk '{print $1}')
        echo "  ✓ MySQL备份完成: mysql_${MYSQL_DB}_${DATE}.sql.gz ($SIZE)"
    else
        echo "  ✗ MySQL容器未运行，跳过"
    fi
fi

# ======== MinIO备份 ========
if [ "$BACKUP_TYPE" = "all" ] || [ "$BACKUP_TYPE" = "minio" ]; then
    echo "[2/2] 备份MinIO文件..."

    MINIO_CONTAINER="nursery-minio"
    if docker ps | grep -q "$MINIO_CONTAINER"; then
        docker exec "$MINIO_CONTAINER" tar czf - /data > "$BACKUP_SUBDIR/minio_data_${DATE}.tar.gz"
        SIZE=$(du -h "$BACKUP_SUBDIR/minio_data_${DATE}.tar.gz" | awk '{print $1}')
        echo "  ✓ MinIO备份完成: minio_data_${DATE}.tar.gz ($SIZE)"
    else
        echo "  ✗ MinIO容器未运行，跳过"
    fi
fi

# ======== 清理旧备份（保留30天） ========
echo ""
echo "清理30天前的旧备份..."
find "$BACKUP_DIR" -type d -mtime +30 -exec rm -rf {} \; 2>/dev/null || true

echo ""
echo "=========================================="
echo "  备份完成！"
echo "  位置: $BACKUP_SUBDIR"
echo "=========================================="
ls -lh "$BACKUP_SUBDIR"
