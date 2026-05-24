# CareerNavigator-AI

> AI驱动的大学生就业信息分析与职业规划平台

[![CI](https://github.com/tianqi1107/CareerNavigator-AI/actions/workflows/ci.yml/badge.svg)](https://github.com/tianqi1107/CareerNavigator-AI/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 系统架构

```
┌─────────────────────────────────────────────────────┐
│                    前端 (Vue3 + TypeScript)           │
│  Element Plus · ECharts · TailwindCSS · Pinia       │
├─────────────────────────────────────────────────────┤
│                 REST API (Spring Boot 3)             │
│  JWT Auth · RBAC · Swagger/OpenAPI                   │
├──────────┬──────────┬───────────────────────────────┤
│  MySQL   │  Redis   │  AI Provider Adapter          │
│  (数据)  │  (缓存)  │  OpenAI/DeepSeek/智谱/MiMo    │
└──────────┴──────────┴───────────────────────────────┘
```

## 核心功能

| 模块 | 说明 | 状态 |
|------|------|------|
| 用户系统 | 注册/登录/JWT认证/RBAC权限 | ✅ |
| 专业查询 | 模糊搜索/详情/热门推荐/关联岗位 | ✅ |
| 岗位分类 | 二级分类(公务员/国企/央企/私企/外企/军工...) | ✅ |
| AI职业规划 | 多Provider适配/职业分析/学习路线/考研考公建议 | ✅ |
| AI聊天顾问 | 就业问答/岗位推荐/竞争分析 | ✅ |
| 数据可视化 | ECharts仪表盘/趋势/分布/薪资对比 | ✅ |
| 管理后台 | 用户/专业/岗位管理 | ✅ |

## 技术栈

**前端:** Vue3 + TypeScript + Vite + Pinia + Vue Router + Element Plus + ECharts + TailwindCSS

**后端:** Spring Boot 3 + Spring Security + JWT + MyBatis Plus + MySQL + Redis + Swagger/OpenAPI

**AI模块:** 统一适配器支持 OpenAI / DeepSeek / 智谱AI / XiaomiMiMo

## 快速开始

### 环境要求

- Java 21+
- Maven 3.9+
- MySQL 8.0+
- Node.js 20+
- Redis (可选, 开发时跳过)

### 1. 初始化数据库

```bash
mysql -u root -p < backend/src/main/resources/db/schema.sql
mysql -u root -p careernavigator < backend/src/main/resources/db/data.sql
```

### 2. 配置环境变量

复制 `.env.example` 为 `.env`，填入你的配置:

```bash
cp .env.example .env
# 编辑 .env，填入数据库密码和AI API Key
```

### 3. 启动后端

```bash
cd backend
mvn clean package -DskipTests
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

后端启动后访问: http://localhost:8080/swagger-ui.html

### 4. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端访问: http://localhost:5173

### Docker 一键启动

```bash
# 填写 .env 文件后
docker-compose up -d
```

## 目录结构

```
CareerNavigator-AI/
├── backend/                          # Spring Boot 后端
│   ├── src/main/java/com/careernavigator/
│   │   ├── ai/                       # AI Provider 适配器
│   │   ├── common/                   # 通用组件
│   │   ├── config/                   # 配置类
│   │   ├── controller/               # REST API
│   │   ├── entity/                   # 实体类
│   │   ├── mapper/                   # MyBatis Plus Mapper
│   │   ├── service/                  # 业务逻辑
│   │   ├── security/                 # JWT 安全模块
│   │   ├── dto/                      # 请求DTO
│   │   └── vo/                       # 响应VO
│   └── src/main/resources/
│       ├── application*.yml          # 配置文件
│       └── db/                       # SQL脚本
├── frontend/                         # Vue3 前端
│   └── src/
│       ├── api/                      # Axios API封装
│       ├── components/               # 组件
│       ├── router/                   # 路由
│       ├── stores/                   # Pinia状态管理
│       ├── types/                    # TypeScript类型
│       ├── views/                    # 页面
│       └── styles/                   # 样式
├── docker-compose.yml
└── .github/workflows/ci.yml         # CI/CD
```

## API 文档

启动后端后访问 Swagger UI: http://localhost:8080/swagger-ui.html

### 主要接口

| 模块 | 接口 | 说明 |
|------|------|------|
| 认证 | `POST /api/auth/login` | 登录 |
| 认证 | `POST /api/auth/register` | 注册 |
| 专业 | `GET /api/majors` | 搜索专业 |
| 专业 | `GET /api/majors/hot` | 热门专业 |
| 岗位 | `GET /api/jobs` | 搜索岗位 |
| 岗位 | `GET /api/jobs/{id}` | 岗位详情 |
| 分类 | `GET /api/categories/tree` | 分类树 |
| AI | `POST /api/ai/career-plan` | AI职业规划 |
| AI | `POST /api/ai/chat` | AI聊天 |
| 数据 | `GET /api/dashboard` | 仪表盘数据 |

## AI Provider 配置

在 `.env` 中配置你的AI API Key:

```bash
# 选择默认供应商
AI_DEFAULT_PROVIDER=deepseek

# DeepSeek
DEEPSEEK_API_KEY=sk-xxx

# OpenAI
OPENAI_API_KEY=sk-xxx

# 智谱AI
ZHIPU_API_KEY=xxx.xxx
```

支持的供应商: OpenAI, DeepSeek, 智谱AI, XiaomiMiMo

## 部署

详见 [部署文档](docs/deployment.md)

## License

MIT
