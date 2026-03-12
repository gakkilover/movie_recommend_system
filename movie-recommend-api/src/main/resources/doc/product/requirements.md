# 电影推荐系统需求文档

## 项目概述
电影推荐系统是一个基于Web的电影推荐平台，通过分析用户行为（如评分、收藏、浏览记录等）为用户提供个性化的电影推荐服务。

## 功能需求

### 1. 用户管理
- **用户注册与登录**：支持用户通过手机验证码注册，用户名/邮箱登录；登录成功后在Session中存储用户信息
- **用户个人信息维护**：用户可以修改个人资料（昵称、性别、年龄、电话、邮箱、头像等）
- **用户退出登录**：清除Session中的用户信息，返回到未登录状态
- **游客访问支持（自动生成临时用户ID）**：未登录用户访问时，系统自动根据IP生成临时用户ID，用于记录行为数据

### 2. 电影信息管理
- **电影基本信息展示**：展示电影名称、导演、主演、上映时间、国家、图片、简介等基本信息
- **电影详情页面**：包括电影评分、简介、标签、背景图等详细信息
- **电影搜索功能**：支持按电影名称进行模糊搜索，返回匹配结果列表
- **电影分类浏览**：按电影标签/类型进行筛选展示，支持多标签组合查询
- **电影排序功能**：支持按评分、热度（评分人数）、上映时间等多种方式排序

### 3. 推荐系统
- **基于用户行为的个性化推荐**：根据用户的历史评分、收藏、浏览记录等行为数据，计算用户兴趣画像，推荐符合用户兴趣的电影
- **基于内容的相似电影推荐**：根据电影的标签、类型等特征计算电影之间的相似度，为当前电影推荐相似电影
- **热门电影推荐**：根据电影的评分人数、平均评分等指标计算热度值，推荐当前热门电影
- **混合推荐算法（结合个性化推荐和热门电影）**：在个性化推荐结果中适当混入热门电影，以提高推荐的多样性和覆盖率

### 4. 用户行为记录
- **电影评分（星级评分）及评论**：用户可以对电影进行1-5星评分，并可选填写评论内容，评论时间自动记录
- **电影收藏功能**：用户可以收藏喜欢的电影，收藏时间自动记录；支持取消收藏
- **搜索历史纪录**：记录用户的搜索关键词和搜索时间，用于个性化推荐和搜索历史展示
- **浏览历史纪录**：记录用户浏览过的电影及浏览时间，用于个性化推荐

### 5. 个性化功能
- **个人中心（展示用户信息、收藏电影、影评等）**：展示用户基本信息、收藏的电影列表、发表的影评列表等
- **根据用户历史行为更新推荐结果**：当用户进行评分、收藏、搜索等行为时，实时更新用户兴趣画像和推荐结果
- **标签-based推荐（基于用户选择的电影标签）**：新用户注册时选择感兴趣的电影标签，系统根据这些标签推荐相应类型的电影

## 推荐算法详细逻辑（以首页推荐为例）

### 已登录用户推荐逻辑：
1. 检查用户是否有历史推荐记录
2. 如果有推荐记录：
   - 从ALS协同过滤表获取该用户的历史推荐电影
   - 获取热门电影Top10列表
   - 使用相似度计算算法（CalSemblance）将历史推荐电影和热门电影合并，去重并按推荐优先级排序
3. 如果没有推荐记录：
   - 检查用户是否选择了电影标签（注册时）
   - 如果选择了标签：获取每个标签下热度最高的电影，合并去重后按热度排序
   - 如果没有选择标签：直接返回热门电影Top10列表
4. 将最终推荐电影列表存入Session，同时创建电影ID到列表索引的映射JSON存入Session

### 未登录用户（游客）推荐逻辑：
1. 根据用户IP获取或创建临时游客用户
2. 检查游客是否有搜索历史
3. 如果有搜索历史：
   - 根据搜索历史获取相关电影标签
   - 获取每个标签下热度最高的电影，合并去重
   - 获取热门电影Top10列表
   - 使用相似度计算算法将标签电影和热门电影合并
4. 如果没有搜索历史：直接返回热门电影Top10列表
5. 将最终推荐电影列表存入Session，同时创建电影ID到列表索引的映射JSON存入Session

## 非功能需求

### 性能要求
- 响应时间：主要接口响应时间小于2秒
- 并发支持：支持至少1000个并发用户
- 缓存机制：使用Redis缓存热门电影数据、电影详情信息和用户会话，减少数据库访问压力

### 安全要求
- 用户登录状态管理：采用Session机制，登录成功后在服务器端存储用户信息，客户端保存SessionID
- 敏感信息保护：密码采用加密存储，手机验证码等敏感信息不在日志中输出
- 防止常见Web攻击：通过Spring Security框架防范XSS、CSRF等常见攻击

### 兼容性要求
- 支持主流浏览器：Chrome, Firefox, Safari, Edge等主流浏览器的最新版本
- 响应式设计：采用Bootstrap框架，自适应不同屏幕尺寸（桌面、平板、手机）

### 可维护性要求
- 代码结构清晰：遵循MVC架构，清晰分离控制器（Controller）、服务（Service）、数据访问层（DAO）和实体（Entity）
- 充分的注释和文档：关键业务逻辑和复杂算法有详细注释，维护文档及时更新
- 模块化设计：功能模块解耦，便于单元测试和功能扩展

## 系统架构

### 技术栈
- 后端：Spring Boot框架（版本1.5.x）
- 数据库：MySQL 5.6+
- 缓存：Redis 3.0+
- ORM：MyBatis Plus（增强版MyBatis）
- API文档：Swagger2
- 前端：Bootstrap + jQuery + 原生JavaScript
- 模板引擎：Spring MVC内置视图解析器（JSP）

### 模块划分
1. 电影推荐API模块（movie-recommend-api）：提供RESTful API接口和页面视图
2. 电影推荐公共模块（movie-recommend-common）：公共工具类、常量定义和基础实体类
3. 电影推荐服务模块（理论上应存在但当前代码中未分离）：业务逻辑服务层

## 接口约定
- 采用RESTful风格设计API（虽然部分接口返回视图名，但核心数据接口遵循REST原则）
- 请求和响应采用JSON格式（通过ResultView对象封装）
- 统一的返回码和消息格式：所有API通过ResultView返回{status, message, data}结构
- 使用HTTP状态码表示操作结果：200表示成功，其他表示各种失败情况
- 支持跨域请求：通过Spring MVC的CORS配置支持跨域访问

## 数据模型详细说明

### 核心实体关系
```
用户(UserEntity) 1---N 收藏记录(CollectRecordEntity)
用户(UserEntity) 1---N 评论记录(CommentRecordEntity)
用户(UserEntity) 1---N 用户标签(UserTagEntity)
用户(UserEntity) 1---N 推荐记录(RecommendRecordEntity)
电影(MovieEntity) 1---N 收藏明细(CollectDetailEntity)
电影(MovieEntity) 1---N 评论记录(CommentRecordEntity)
电影(MovieEntity) 1---N 电影标签关系(MovieTagRelationEntity)
电影(MovieEntity) 1---N 电影相似度(MovieSimilarEntity)
标签(TagEntity) 1---N 电影标签关系(MovieTagRelationEntity)
标签(TagEntity) 1---N 用户标签(UserTagEntity)
```

### 实体字段详细说明

#### 用户表（UserEntity）
- userId: 主键ID，自增
- userName: 用户名，登录凭证
- userPassword: 密码（加密存储）
- userSex: 性别（0未知,1男,2女）
- userAge: 年龄
- userPhone: 手机号码，用于验证码登录
- userEmail: 邮箱地址
- userPic: 头像图片URL
- userRegisterDate: 注册日期时间
- lastLoginDate: 最后登录日期时间
- statusCd: 用户状态（0正常,1禁用等）
- phoneCode: 手机验证码（临时存储）

#### 电影表（MovieEntity）
- movieId: 主键ID，自增
- movieName: 电影名称
- movieShowyear: 上映年份
- nation: 制作国家/地区
- movieDirector: 导演姓名
- movieLeadactors: 主要演员，逗号分隔
- screen: 制片公司或出品方
- moviePic: 电影海报图片URL
- movieAverating: 平均评分（1-5分）
- movieRateNum: 评分人数
- movieDescription: 电影简介描述
- movieTags: 电影标签，逗号分隔（如“动作,科幻,冒险”）
- backpost: 背景图片URL（用于详情页展示）
- recommendPriority: 推荐优先级（临时计算字段，不持久化）
- sumRate: 评分总和（用于计算平均分）
- tagId: 临时关联的标签ID（用于查询时关联）

#### 标签表（TagEntity）
- tagId: 主键ID，自增
- tagName: 标签名称（如“动作、喜剧、爱情”等）
- tagType: 标签类型（1电影类型,2情感类型,3时代类型等）

#### 收藏记录表（CollectRecordEntity）
- collectRecordId: 主键ID，自增
- userId: 外键，关联用户表
- movieIds: 收藏的电影ID列表，逗号分隔并以点开头结尾（如“.1,2,3.”）
- collectDate: 收藏日期时间

#### 收藏明细表（CollectDetailEntity）
- id: 主键ID，自增
- userId: 外键，关联用户表
- movieId: 外键，关联电影表

#### 评论记录表（CommentRecordEntity）
- id: 主键ID，自增
- userId: 外键，关联用户表
- movieId: 外键，关联电影表
- commentStar: 评分星级（1-5分）
- commentDate: 评论日期时间
- commentDescription: 评论文本内容

#### 推荐记录表（RecommendRecordEntity）
- id: 主键ID，自增
- userId: 外键，关联用户表
- movieIds: 推荐的电影ID列表，逗号分隔（如“1,2,3,4,5”）
- recommendDate: 推荐日期时间

#### 电影标签关系表（MovieTagRelationEntity）
- id: 主键ID，自增
- movieId: 外键，关联电影表
- tagId: 外键，关联标签表

#### 电影相似度表（MovieSimilarEntity）
- id: 主键ID，自增
- movieId: 外键，关联电影表（当前电影）
- similarMovieId: 外键，关联电影表（相似电影）
- similarity: 相似度分数（0-1之间的浮点数）

### 数据访问层接口概览
- UserService: 用户注册、登录、信息更新、验证等操作
- MovieService: 电影查询、搜索、标签分类查询、评分更新等操作
- TagService: 标签列表查询、标签电影查询等操作
- CollectRecordService/CollectDetailService: 电影收藏相关操作
- CommentRecordService: 电影评论和评分相关操作
- RecommendRecordService/RecommendRelationService: 推荐记录和推荐关系管理
- HeatMovieService: 热门电影查询
- MovieSimilarService: 电影相似度查询
- SearchRecordService: 搜索历史纪录
- UserTagService: 用户标签关联管理