# CloudMuseum / 登州博物馆项目上下文

## 项目定位

这是一个“登州博物馆·云端漫游”数字博物馆系统：面向游客提供全景/3D 展示、馆藏文物浏览、历史介绍、公告资讯、研学课程与团体预约；面向管理员提供内容、用户、课程、预约和访问统计管理。

## 当前代码结构

- `src/main/java`: Spring Boot + MyBatis 后端，按 controller/service/mapper/entity 分层。
- `src/main/resources/static`: Spring Boot 可直接托管的前端静态页、管理后台、全景和 PlayCanvas 3D 资源。
- `dengzhou-museum-frontend`: Vite 多页面前端源码，入口为 `index.html`、`admin.html`、`tour.html`。
- `cloud_museum.sql`: MySQL 初始化脚本，包含 8 张表和演示数据。
- `museum_knowledge_base.json`: AI 导览知识库摘要（103 件文物、10 篇文章、6 门课程）。
- `uploads`: 用户上传及全景/Unity/图片资源；`target`: 已编译产物，不应作为源码修改入口。

## 主要能力

1. 游客首页：蓬莱阁 360° 全景、馆舍概况、文物卡片/详情、公告和研学动态。
2. 研学业务：课程展示、团体预约、招募报名；后端负责课程名额占用和预约状态流转。
3. AI 导览：`/api/chat/ask` 对接本地文本服务；前端以“登州小吏”角色维护对话历史并提供不可用时的兜底文案。
4. 智能服务：高德天气代理、基于天气与兴趣的旅行建议、DashScope 实时语音识别。
5. 管理后台：管理员登录、内容 CRUD、课程/预约/招募管理、访问统计。

## 运行约定

- 后端端口 `8081`，数据库为本机 MySQL `cloud_museum`。
- 前端开发服务器端口 `5173`，Vite 将 `/api` 和 `/uploads` 代理到 `http://localhost:8081`。
- 前端构建：在 `dengzhou-museum-frontend` 执行 `npm run build`；构建入口为三个 HTML 页面。
- 后端运行前需准备 Java 17、Maven、MySQL，并执行 `cloud_museum.sql`；AI 文本服务默认监听 `localhost:5000`。

## 已验证状态（2026-08-25）

- 前端 `npm run build` 成功；产物输出到 `dengzhou-museum-frontend/dist`。
- MySQL80 正常运行；`cloud_museum` 数据库及 `cloudmuseum / 123456` 账号可连接。
- 已将 `application.properties` 数据库账号改为 `cloudmuseum`，并重新打包成功。
- 后端新 jar 已启动于 `http://localhost:8081/`（当前 PID 55000）；`/api/relics?page=1&size=2` 与 `/api/courses` 均返回 HTTP 200。
- 前端 Vite 运行于 `http://localhost:5173/`，管理后台为 `http://localhost:5173/admin.html`。
- PowerShell 控制台显示接口中文为 `??`，但接口状态和数据结构已验证；如浏览器仍显示问号，再检查数据库表/连接字符集。

## 下一步优先级

1. 立即移除并轮换配置文件中的数据库密码、DashScope Key 和高德 Key，改用环境变量或本地未提交配置。
2. 准备 MySQL 数据库和 Java/Maven 环境，启动后端后逐项验证 API、上传、预约名额和 AI 依赖。
3. 用浏览器验证游客首页、全景、管理后台登录和移动端布局，而不仅是静态构建。
4. 将 `target`、`node_modules`、`dist` 和大体积上传资源从版本管理中明确区分，避免把编译/运行产物当源码维护。
