-- 扩展收藏详情表，添加观看状态字段
-- 用于实现"想看"/"在看"/"已看"功能

ALTER TABLE `collect_detail` 
ADD COLUMN `watch_status` tinyint(1) DEFAULT 0 COMMENT '观看状态: 0-想看, 1-在看, 2-已看, 3-取消/不再想看' AFTER `collect_date`,
ADD COLUMN `update_time` datetime DEFAULT NULL COMMENT '状态更新时间' AFTER `watch_status`;

-- 创建索引提升查询性能
ALTER TABLE `collect_detail`
ADD INDEX `idx_user_watch_status` (`user_id`, `watch_status`),
ADD INDEX `idx_watch_status` (`watch_status`);
