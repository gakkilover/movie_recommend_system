-- 电影榜单表
-- 用于存储各类榜单数据：Top250、月度榜单、分类榜单等

CREATE TABLE IF NOT EXISTS `movie_rank` (
  `rank_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '榜单ID',
  `rank_name` varchar(100) NOT NULL COMMENT '榜单名称',
  `rank_type` varchar(50) NOT NULL COMMENT '榜单类型: top250(月度榜单)/genre(分类榜单)',
  `movie_id` bigint(20) NOT NULL COMMENT '电影ID',
  `rank_position` int(11) NOT NULL COMMENT '榜单排名',
  `genre` varchar(50) DEFAULT NULL COMMENT '电影类型/标签',
  `year` int(11) DEFAULT NULL COMMENT '年份',
  `month` int(11) DEFAULT NULL COMMENT '月份',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`rank_id`),
  KEY `idx_rank_type` (`rank_type`),
  KEY `idx_movie_id` (`movie_id`),
  KEY `idx_rank_type_position` (`rank_type`, `rank_position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电影榜单表';

-- 初始化Top250榜单数据
-- 从movie表中选取评分和评分数综合排名前250的电影

INSERT INTO `movie_rank` (`rank_name`, `rank_type`, `movie_id`, `rank_position`, `genre`, `year`, `month`)
SELECT 
    'Top250' AS rank_name,
    'top250' AS rank_type,
    movie_id,
    (@row_number := @row_number + 1) AS rank_position,
    NULL AS genre,
    YEAR(movie_showyear) AS year,
    NULL AS month
FROM movie, (SELECT @row_number := 0) AS t
WHERE movie_rate_num > 100
ORDER BY (movie_averating * LOG(movie_rate_num + 1)) DESC
LIMIT 250;
