# 部署文档

## 本地开发部署

### 前置条件

| 工具 | 版本 | 用途 |
|------|------|------|
| Java | 21+ | 后端运行 |
| Maven | 3.9+ | 构建后端 |
| MySQL | 8.0+ | 数据库 |
| Node.js | 20+ | 前端构建 |
| Redis | 7+ | 缓存(可选) |

### 步骤

1. **创建数据库**
```bash
mysql -u root -p
CREATE DATABASE careernavigator CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit;

mysql -u root -p careernavigator < backend/src/main/resources/db/schema.sql
mysql -u root -p careernavigator < backend/src/main/resources/db/data.sql
```

2. **配置后端**
```bash
cd backend
# 编辑 src/main/resources/application-dev.yml 中的数据库密码
```

3. **启动后端**
```bash
mvn clean package -DskipTests
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# 访问 http://localhost:8080/swagger-ui.html
```

4. **启动前端**
```bash
cd frontend
npm install
npm run dev
# 访问 http://localhost:5173
```

## Docker 部署

```bash
# 1. 配置 .env
cp .env.example .env
# 编辑 .env

# 2. 启动所有服务
docker-compose up -d

# 3. 访问
# 前端: http://localhost
# 后端: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

## 生产部署

1. 修改 `application-prod.yml` 配置
2. 设置 `JWT_SECRET` 环境变量
3. 配置 AI API Key
4. 使用 Docker Compose 或 Kubernetes 部署
