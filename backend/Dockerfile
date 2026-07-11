# ============================================
# 后端多阶段构建 Dockerfile
# 阶段1: Maven编译打包
# 阶段2: 运行时镜像（更小）
# ============================================

# ---- 阶段1: 构建 ----
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
# 先复制pom.xml，利用Docker缓存加速依赖下载
COPY pom.xml .
RUN mvn dependency:go-offline -B
# 复制源码并打包
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---- 阶段2: 运行 ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# 安装时区
RUN apt-get update && apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# 复制jar包
COPY --from=builder /build/target/nursery-platform.jar app.jar

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/api/auth/captcha || exit 1

EXPOSE 8080

ENTRYPOINT ["java", \
    "-Xms256m", \
    "-Xmx512m", \
    "-Dspring.profiles.active=${SPRING_PROFILE:-prod}", \
    "-jar", "app.jar"]
