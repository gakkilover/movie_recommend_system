-- ----------------------------
-- 数据库建表脚本
-- ----------------------------

-- ----------------------------
-- 1. 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `user_name` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
  `user_password` VARCHAR(100) DEFAULT NULL COMMENT '密码',
  `user_sex` VARCHAR(10) DEFAULT NULL COMMENT '性别',
  `user_age` BIGINT(20) DEFAULT NULL COMMENT '年龄',
  `user_phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `user_email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
  `user_pic` VARCHAR(200) DEFAULT NULL COMMENT '头像',
  `user_register_date` DATETIME DEFAULT NULL COMMENT '注册日期',
  `last_login_date` DATETIME DEFAULT NULL COMMENT '最后登录日期',
  `status_cd` VARCHAR(10) DEFAULT NULL COMMENT '状态',
  `phone_code` VARCHAR(10) DEFAULT NULL COMMENT '手机验证码',
  PRIMARY KEY (`user_id`),
  INDEX `idx_user_name` (`user_name`),
  INDEX `idx_user_phone` (`user_phone`),
  INDEX `idx_user_email` (`user_email`),
  INDEX `idx_status_cd` (`status_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- 2. 电影表
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
  `movie_id` BIGINT(20) NOT NULL COMMENT '电影ID',
  `movie_name` VARCHAR(100) DEFAULT NULL COMMENT '电影名称',
  `movie_showyear` DATETIME DEFAULT NULL COMMENT '上映年份',
  `nation` VARCHAR(50) DEFAULT NULL COMMENT '国家',
  `movie_director` VARCHAR(100) DEFAULT NULL COMMENT '导演',
  `movie_leadactors` VARCHAR(200) DEFAULT NULL COMMENT '主演',
  `screen` VARCHAR(50) DEFAULT NULL COMMENT '片长',
  `movie_pic` VARCHAR(200) DEFAULT NULL COMMENT '海报',
  `movie_averating` DOUBLE DEFAULT NULL COMMENT '平均评分',
  `movie_rate_num` BIGINT(20) DEFAULT NULL COMMENT '评分人数',
  `movie_description` TEXT DEFAULT NULL COMMENT '简介',
  `movie_tags` VARCHAR(200) DEFAULT NULL COMMENT '标签',
  `backpost` VARCHAR(200) DEFAULT NULL COMMENT '背景图',
  `sum_rate` DOUBLE DEFAULT NULL COMMENT '总评分',
  PRIMARY KEY (`movie_id`),
  INDEX `idx_movie_name` (`movie_name`),
  INDEX `idx_movie_showyear` (`movie_showyear`),
  INDEX `idx_nation` (`nation`),
  INDEX `idx_movie_director` (`movie_director`),
  INDEX `idx_movie_averating` (`movie_averating`),
  FULLTEXT INDEX `ft_movie_name` (`movie_name`),
  FULLTEXT INDEX `ft_movie_tags` (`movie_tags`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电影表';

-- ----------------------------
-- 3. 标签表
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tag_id` BIGINT(20) NOT NULL COMMENT '标签ID',
  `tag_name` VARCHAR(50) DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`tag_id`),
  INDEX `idx_tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- ----------------------------
-- 4. 收藏记录表
-- ----------------------------
DROP TABLE IF EXISTS `collect_record`;
CREATE TABLE `collect_record` (
  `collect_record_id` BIGINT(20) NOT NULL COMMENT '收藏记录ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `movie_ids` VARCHAR(500) DEFAULT NULL COMMENT '电影ID集合',
  `collect_date` DATETIME DEFAULT NULL COMMENT '收藏日期',
  PRIMARY KEY (`collect_record_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_collect_date` (`collect_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏记录表';

-- ----------------------------
-- 5. 评论记录表
-- ----------------------------
DROP TABLE IF EXISTS `comment_record`;
CREATE TABLE `comment_record` (
  `comment_id` BIGINT(20) NOT NULL COMMENT '评论ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `movie_id` BIGINT(20) DEFAULT NULL COMMENT '电影ID',
  `comment_description` TEXT DEFAULT NULL COMMENT '评论内容',
  `comment_star` DOUBLE DEFAULT NULL COMMENT '评分',
  `comment_date` DATETIME DEFAULT NULL COMMENT '评论日期',
  `comment_thumbup_num` BIGINT(20) DEFAULT NULL COMMENT '点赞数',
  `movie_pic` VARCHAR(200) DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`comment_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_movie_id` (`movie_id`),
  INDEX `idx_comment_date` (`comment_date`),
  INDEX `idx_comment_star` (`comment_star`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论记录表';

-- ----------------------------
-- 6. 搜索记录表
-- ----------------------------
DROP TABLE IF EXISTS `search_record`;
CREATE TABLE `search_record` (
  `search_record_id` BIGINT(20) NOT NULL COMMENT '搜索记录ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `search_info` VARCHAR(100) DEFAULT NULL COMMENT '搜索内容',
  `search_date` DATETIME DEFAULT NULL COMMENT '搜索日期',
  `movie_id` BIGINT(20) DEFAULT NULL COMMENT '电影ID',
  PRIMARY KEY (`search_record_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_search_info` (`search_info`),
  INDEX `idx_search_date` (`search_date`),
  INDEX `idx_movie_id` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='搜索记录表';

-- ----------------------------
-- 7. 推荐记录表
-- ----------------------------
DROP TABLE IF EXISTS `recommend_record`;
CREATE TABLE `recommend_record` (
  `recommend_record_id` BIGINT(20) NOT NULL COMMENT '推荐记录ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `recommend_date` DATETIME DEFAULT NULL COMMENT '推荐日期',
  `movie_ids` VARCHAR(500) DEFAULT NULL COMMENT '电影ID集合',
  PRIMARY KEY (`recommend_record_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_recommend_date` (`recommend_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推荐记录表';

-- ----------------------------
-- 8. 电影榜单表
-- ----------------------------
DROP TABLE IF EXISTS `movie_rank`;
CREATE TABLE `movie_rank` (
  `rank_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '榜单ID',
  `rank_name` VARCHAR(50) DEFAULT NULL COMMENT '榜单名称',
  `rank_type` VARCHAR(20) DEFAULT NULL COMMENT '榜单类型',
  `movie_id` BIGINT(20) DEFAULT NULL COMMENT '电影ID',
  `rank_position` INT(11) DEFAULT NULL COMMENT '排名位置',
  `genre` VARCHAR(50) DEFAULT NULL COMMENT '类型',
  `year` INT(11) DEFAULT NULL COMMENT '年份',
  `month` INT(11) DEFAULT NULL COMMENT '月份',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`rank_id`),
  INDEX `idx_rank_name` (`rank_name`),
  INDEX `idx_rank_type` (`rank_type`),
  INDEX `idx_movie_id` (`movie_id`),
  INDEX `idx_rank_position` (`rank_position`),
  INDEX `idx_genre` (`genre`),
  INDEX `idx_year_month` (`year`, `month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电影榜单表';

-- ----------------------------
-- 9. 用户标签表
-- ----------------------------
DROP TABLE IF EXISTS `user_tag`;
CREATE TABLE `user_tag` (
  `user_tag_id` BIGINT(20) NOT NULL COMMENT '用户标签ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `tag_id` VARCHAR(50) DEFAULT NULL COMMENT '标签ID',
  `record_date` DATETIME DEFAULT NULL COMMENT '记录日期',
  PRIMARY KEY (`user_tag_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标签表';

-- ----------------------------
-- 10. 电影标签关联表
-- ----------------------------
DROP TABLE IF EXISTS `movie_tag_relation`;
CREATE TABLE `movie_tag_relation` (
  `movie_tag_id` BIGINT(20) NOT NULL COMMENT '电影标签ID',
  `movie_id` BIGINT(20) DEFAULT NULL COMMENT '电影ID',
  `tag_id` BIGINT(20) DEFAULT NULL COMMENT '标签ID',
  PRIMARY KEY (`movie_tag_id`),
  INDEX `idx_movie_id` (`movie_id`),
  INDEX `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电影标签关联表';

-- ----------------------------
-- 11. 电影相似度表
-- ----------------------------
DROP TABLE IF EXISTS `movie_similar`;
CREATE TABLE `movie_similar` (
  `movie_id1` BIGINT(20) NOT NULL COMMENT '电影ID1',
  `movie_id2` BIGINT(20) NOT NULL COMMENT '电影ID2',
  `similar_rate` DOUBLE DEFAULT NULL COMMENT '相似度',
  PRIMARY KEY (`movie_id1`, `movie_id2`),
  INDEX `idx_movie_id2` (`movie_id2`),
  INDEX `idx_similar_rate` (`similar_rate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电影相似度表';

-- ----------------------------
-- 12. 热门电影表
-- ----------------------------
DROP TABLE IF EXISTS `heat_movie`;
CREATE TABLE `heat_movie` (
  `heat_id` BIGINT(20) NOT NULL COMMENT '热度ID',
  `movie_id` BIGINT(20) DEFAULT NULL COMMENT '电影ID',
  `heat_number` DOUBLE DEFAULT NULL COMMENT '热度值',
  `update_date` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`heat_id`),
  INDEX `idx_movie_id` (`movie_id`),
  INDEX `idx_heat_number` (`heat_number`),
  INDEX `idx_update_date` (`update_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='热门电影表';

-- ----------------------------
-- 13. 推荐关系表
-- ----------------------------
DROP TABLE IF EXISTS `recommend_relation`;
CREATE TABLE `recommend_relation` (
  `recommend_relation_id` BIGINT(20) NOT NULL COMMENT '推荐关系ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `movie_id` BIGINT(20) DEFAULT NULL COMMENT '电影ID',
  `recommend_score` DOUBLE DEFAULT NULL COMMENT '推荐分数',
  PRIMARY KEY (`recommend_relation_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_movie_id` (`movie_id`),
  INDEX `idx_recommend_score` (`recommend_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推荐关系表';

-- ----------------------------
-- 14. 收藏详情表
-- ----------------------------
DROP TABLE IF EXISTS `collect_detail`;
CREATE TABLE `collect_detail` (
  `collect_detail_id` BIGINT(20) NOT NULL COMMENT '收藏详情ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `movie_id` BIGINT(20) DEFAULT NULL COMMENT '电影ID',
  `collect_date` DATETIME DEFAULT NULL COMMENT '收藏日期',
  `watch_status` INT(11) DEFAULT NULL COMMENT '观看状态(0:未看 1:在看 2:已看)',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`collect_detail_id`),
  UNIQUE INDEX `uk_user_movie` (`user_id`, `movie_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_movie_id` (`movie_id`),
  INDEX `idx_watch_status` (`watch_status`),
  INDEX `idx_collect_date` (`collect_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏详情表';

-- ----------------------------
-- 15. 默认推荐表
-- ----------------------------
DROP TABLE IF EXISTS `default_recommend`;
CREATE TABLE `default_recommend` (
  `default_recommend_id` BIGINT(20) NOT NULL COMMENT '默认推荐ID',
  `movie_id` BIGINT(20) DEFAULT NULL COMMENT '电影ID',
  PRIMARY KEY (`default_recommend_id`),
  INDEX `idx_movie_id` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='默认推荐表';
