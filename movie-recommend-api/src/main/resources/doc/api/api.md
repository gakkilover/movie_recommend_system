# 电影推荐系统API文档

## 概述
本文档描述了电影推荐系统的RESTful API接口，所有接口基于HTTP协议，采用JSON格式进行数据交换。

## 基础信息
- **基础URL**: `/` (相对于应用根路径)
- **数据格式**: JSON
- **字符编码**: UTF-8
- **请求方法**: GET, POST
- **响应格式**: 统一返回 `ResultView` 对象

## 通用响应格式
所有API接口统一返回以下格式的JSON响应：
```json
{
  "status": 200,
  "message": "成功提示信息",
  "data": {/* 实际数据内容 */}
}
```

其中：
- `status`: HTTP状态码，200表示成功，其他表示失败
- `message`: 提示信息
- `data`: 返回的实际数据，可为对象、数组或基本类型

## 认证机制
部分接口需要用户登录后才能访问，通过以下方式进行身份验证：
1. 用户登录成功后，服务器在Session中存储用户信息
2. 后续请求通过Session维持登录状态
3. 部分接口会检查Session中的`user`或`userId`属性

## 接口列表

### 用户相关接口
#### 用户控制器 (UserController)

| 接口路径 | 请求方法 | 功能描述 | 是否需要登录 |
|----------|----------|----------|--------------|
| `/page/login` | GET | 跳转到登录界面 | 否 |
| `/customer/login` | POST | 用户登录 | 否 |
| `/customer/logout` | GET | 用户退出登录 | 是 |
| `/home` | GET | 跳转到主页 | 否 |
| `/customer/register/movieSubmit` | POST | 新用户选择喜欢的电影 | 否 (需要session中有userId) |
| `/updateUser` | POST | 更新用户个人信息 | 是 |
| `/editUser` | GET | 获取用户信息用于编辑 | 是 |
| `/profile` | GET | 跳转到个人中心页面 | 是 |
| `/customer/profile` | POST | 获取个人中心数据 | 是 |

### 电影相关接口
#### 电影控制器 (MovieController)

| 接口路径 | 请求方法 | 功能描述 | 是否需要登录 |
|----------|----------|----------|--------------|
| `/search` | POST | 按名称搜索电影 | 否 |

#### 客户控制器 (CustomerController) - 电影相关

| 接口路径 | 请求方法 | 功能描述 | 是否需要登录 |
|----------|----------|----------|--------------|
| `/homepage` | GET | 显示个性化主页推荐 | 是 |
| `/index` | GET | 显示电影索引页（所有分类和电影） | 否 |
| `/Customer/Description` | POST | 电影详情传值（点击电影后处理） | 是（部分逻辑需要） |
| `/MovieDescription` | GET | 显示电影详情界面 | 否 |
| `/loadingmore` | POST | 通过类型标签加载更多电影 | 否 |
| `/typesortmovie` | POST | 按类型和排序方式获取电影 | 否 |
| `/getstar` | POST | 电影评星和评论 | 是 |
| `/getSimiMovies` | POST | 获取相似电影 | 否 |
| `/likedmovie` | POST | 用户喜欢电影（收藏） | 是 |

### 标签相关接口
#### 标签控制器 (TagController)

| 接口路径 | 请求方法 | 功能描述 | 是否需要登录 |
|----------|----------|----------|--------------|
| `/tag/tagList` | GET | 查询所有电影标签 | 否 |

### 注册相关接口
#### 注册控制器 (RegisterController)

| 接口路径 | 请求方法 | 功能描述 | 是否需要登录 |
|----------|----------|----------|--------------|
| `/customer/register` | GET | 进入注册页面 | 否 |
| `/customer/check/{param}/{type}` | GET | 检查用户名/邮箱是否符合规范 | 否 |
| `/customer/checkboth/{paramName}/{paramEmail}/{type}` | GET | 检查用户名和邮箱 | 否 |
| `/customer/register` | POST | 用户注册 | 否 |
| `/sendCode` | POST | 发送手机验证码 | 否 |

### 用户标签相关接口
#### 用户标签控制器 (UserTagController)

| 接口路径 | 请求方法 | 功能描述 | 是否需要登录 |
|----------|----------|----------|--------------|
| `/likedTags` | POST | 用户注册完成，选择喜欢的电影标签 | 是 |

### 电影榜单相关接口
#### 榜单控制器 (RankController)

| 接口路径 | 请求方法 | 功能描述 | 是否需要登录 |
|----------|----------|----------|--------------|
| `/api/rank/list` | GET | 获取榜单列表 | 否 |
| `/api/rank/{rankType}` | GET | 根据类型获取榜单 | 否 |
| `/api/rank/top250` | GET | 获取Top250榜单 | 否 |
| `/api/rank/monthly` | GET | 获取本月榜单 | 否 |
| `/api/rank/monthly/{year}/{month}` | GET | 获取指定月份榜单 | 否 |
| `/api/rank/genre/{genre}` | GET | 获取分类榜单 | 否 |

### 观看状态相关接口（想看/在看/已看）
#### 观看状态控制器 (WatchStatusController)

| 接口路径 | 请求方法 | 功能描述 | 是否需要登录 |
|----------|----------|----------|--------------|
| `/api/watch/setStatus` | POST | 设置观看状态 | 是 |
| `/api/watch/wishList` | GET | 获取想看列表 | 是 |
| `/api/watch/watchingList` | GET | 获取在看列表 | 是 |
| `/api/watch/watchedList` | GET | 获取已看列表 | 是 |
| `/api/watch/remove` | POST | 移除观看状态 | 是 |
| `/api/watch/status` | GET | 获取电影观看状态 | 是 |

## 详细接口说明

### 用户登录
**路径**: `/customer/login`  
**方法**: POST  
**参数**:
- `userName` (String): 用户名，必填
- `userPassword` (String): 密码，必填  
**返回**: 
- 成功: ResultView对象，status=200，data包含UserEntity对象
- 失败: ResultView对象，status非200，message包含错误信息
**示例**:
请求:
```
POST /customer/login
Content-Type: application/x-www-form-urlencoded
userName=john_doe&userPassword=123456
```
响应:
```json
{
  "status": 200,
  "message": "登录成功",
  "data": {
    "userId": 123,
    "userName": "john_doe",
    "userEmail": "john@example.com",
    "userSex": 1,
    "userAge": 28
  }
}
```
**设置Session属性**: 登录成功后将UserEntity对象存入Session，键为"user"

### 电影搜索
**路径**: `/search`  
**方法**: POST  
**参数**:
- `search_text` (String): 电影名称搜索关键词  
**返回**: ResultView对象，data包含MovieEntity列表（前10条）

### 用户评分电影
**路径**: `/getstar`  
**方法**: POST  
**参数**:
- `userId` (Long): 用户ID，必填，从Session获取
- `movieId` (Long): 电影ID，必填
- `star` (Double): 评分星级，必填，范围1.0-5.0
- `commentDescription` (String): 评论内容，可选
- `time` (String): 评论时间，格式为"yyyy-MM-dd HH:mm:ss"，必填  
**返回**: 
- 成功: 返回字符串"success"
- 失败: ResultView对象，status非200，message包含错误信息
**功能说明**:
1. 保存用户对电影的评分和评论
2. 更新电影的平均评分和评分人数
3. 如果评分>=3.5，触发推荐系统更新用户推荐列表
4. 同时更新用户的相似电影推荐
**示例**:
请求:
```
POST /getstar
Content-Type: application/x-www-form-urlencoded
userId=123&movieId=456&star=4.5&commentDescription=很好的电影&time=2023-05-20 14:30:00
```
响应:
```
success
```
**设置Session属性**:
- `userstar`: 当前评分记录对象
- `flag`: 是否已收藏该电影的布尔值
- 如果star>3.5，还会设置`TopDefaultMovie`为新的推荐电影列表

### 获取相似电影
**路径**: `/getSimiMovies`  
**方法**: POST  
**参数**:
- `id` (Long): 电影ID  
**返回**: ResultView对象，data包含相似MovieEntity列表

### 用户喜欢电影（收藏）
**路径**: `/likedmovie`  
**方法**: POST  
**参数**:
- `movieId` (String): 电影ID
- `userId` (Long): 用户ID
- `boollike` (Integer): 是否喜欢标志  
**返回**: 成功返回"success"字符串

### 获取电影标签列表
**路径**: `/tag/tagList`  
**方法**: GET  
**参数**: 无  
**返回**: ResultView对象，data包含TagEntity列表

### 获取个性化主页推荐
**路径**: `/homepage`  
**方法**: GET  
**参数**: 无（从Session获取用户信息）  
**返回**: 视图名称"Home"，同时设置Session属性：
- `TopDefaultMovie`: 推荐电影列表
- `TopDefaultMovieMap`: 电影ID到索引的映射JSON字符串
**说明**: 根据用户登录状态和行为历史生成个性化推荐，已登录用户基于历史推荐和热门电影合并，未登录用户基于搜索历史或直接返回热门电影

### 用户注册
**路径**: `/customer/register`  
**方法**: GET  
**描述**: 进入注册页面，会设置Session属性：
- `TopRegDefaultMovie`: 默认推荐电影列表（用于新用户选择喜欢的电影）
- `tagList`: 电影标签列表（用于注册完成后选择感兴趣的标签）  
**返回**: 视图名称"register"
**说明**: 此接口仅返回注册页面视图，实际注册逻辑在POST方法中处理

### 检查用户名/邮箱
**路径**: `/customer/check/{param}/{type}`  
**方法**: GET  
**参数**:
- `param` (String): 要检查的参数值（用户名或邮箱）
- `type` (Integer): 检查类型（1为用户名，2为邮箱）  
**返回**: ResultView对象

### 检查用户名和邮箱
**路径**: `/customer/checkboth/{paramName}/{paramEmail}/{type}`  
**方法**: GET  
**参数**:
- `paramName` (String): 用户名
- `paramEmail` (String): 邮箱
- `type` (Integer): 检查类型  
**返回**: ResultView对象

### 执行用户注册
**路径**: `/customer/register`  
**方法**: POST  
**参数**:
- `user` (UserEntity): 用户对象（包含用户名、密码、邮箱、手机等信息），必填
  - userName: 用户名
  - userPassword: 密码
  - userEmail: 邮箱
  - userPhone: 手机号码
- `request` (HttpServletRequest): 包含Session中的验证码，必填  
**返回**: 
- 成功: ResultView对象，status=200，data包含新注册用户的ID（Long）
- 失败: ResultView对象，status非200，message包含错误信息
**验证流程**:
1. 检查Session中的验证码是否正确
2. 验证用户名和邮箱是否已被注册
3. 保存用户信息到数据库
4. 将新用户ID存入Session
**示例**:
请求:
```
POST /customer/register
Content-Type: application/x-www-form-urlencoded
userName=new_user&userPassword=secure123&userEmail=new@example.com&userPhone=13800138000
```
响应:
```json
{
  "status": 200,
  "message": "注册成功",
  "data": 12345
}
```
**设置Session属性**: 注册成功后将userId存入Session，键为"userId"

### 发送手机验证码
**路径**: `/sendCode`  
**方法**: POST  
**参数**:
- `userPhone` (String): 手机号码  
**返回**: ResultView对象

### 用户选择喜欢的电影标签
**路径**: `/likedTags`  
**方法**: POST  
**参数**:
- `tagIds` (String): 标签ID列表（逗号分隔）
- `userId` (Long): 从Session获取的用户ID  
**返回**: 成功返回Final.SUCCESS常量

### 电影详情处理
**路径**: `/Customer/Description`  
**方法**: POST  
**参数**:
- `id` (String): 电影ID  
**功能**:
1. 获取电影详情信息（从Redis或数据库）
2. 处理用户对电影的评星状态
3. 更新搜索记录
4. 更新推荐记录
5. 设置Session属性用于电影详情页面显示  
**返回**: 成功返回"success"字符串
**设置Session属性**:
- `moviedescription`: 电影详情实体对象
- `userstar`: 用户对该电影的评分记录(如有)
- `description`: 评分记录对象
- `flag`: 是否已收藏该电影的布尔值
- `booluserunlikedmovie`: 用户是否喜欢该电影的标记

### 加载更多电影
**路径**: `/loadingmore`  
**方法**: POST  
**参数**:
- `type` (String): 类型ID
- `molimit` (String): 限制数量
- `sort` (String): 排序方式  
**返回**: ResultView对象，data包含MovieEntity列表

### 按类型和排序获取电影
**路径**: `/typesortmovie`  
**方法**: POST  
**参数**:
- `type` (String): 类型ID
- `molimit` (String): 限制数量
- `sort` (String): 排序方式  
**返回**: ResultView对象，data包含MovieEntity列表

### 个人中心数据获取
**路径**: `/customer/profile`  
**方法**: POST  
**功能**:
1. 获取当前用户信息
2. 获取用户的影评列表
3. 获取用户喜欢的电影列表
4. 为影评列表添加电影图片URL
5. 设置Session属性：
   - `movies`: 用户喜欢的电影列表
   - `commentRecordEntities`: 用户的影评列表  
**返回**: 视图名称"success"

## 错误码说明
- 200: 成功
- 400: 请求参数错误（如验证码不正确、参数缺失或格式错误）
- 401: 未授权（需要登录但未提供有效凭据）
- 403: 禁止访问（权限不足）
- 404: 资源未找到
- 500: 服务器内部错误
- 其他状态码: 失败，具体信息参见message字段

## 注意事项
1. 部分需要用户登录的接口会检查Session中的`user`或`userId`属性，未登录时可能返回错误或重定向到登录页
2. 所有POST请求参数均通过表单形式提交（application/x-www-form-urlencoded）
3. 日期时间参数格式请 strictly 遵循指定格式
4. 长整型(ID)参数请确保在有效范围内
5. 部分接口会设置Session属性用于页面间数据传递