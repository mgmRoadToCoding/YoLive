-- 论坛模块表结构

-- 帖子表
CREATE TABLE IF NOT EXISTS `cms_forum_post` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `images` TEXT DEFAULT NULL COMMENT '帖子图片，逗号分隔的资源路径',
  `user_id` BIGINT NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0待审核 1已通过 2已驳回',
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `audit_reason` VARCHAR(255) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `update_time` DATETIME DEFAULT NULL,
  KEY `idx_post_user` (`user_id`),
  KEY `idx_post_status` (`status`),
  KEY `idx_post_ctime` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论表
CREATE TABLE IF NOT EXISTS `cms_forum_comment` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `content` TEXT NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `create_time` DATETIME DEFAULT NULL,
  `update_time` DATETIME DEFAULT NULL,
  KEY `idx_comment_post` (`post_id`),
  KEY `idx_comment_user` (`user_id`),
  KEY `idx_comment_ctime` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 收藏表
CREATE TABLE IF NOT EXISTS `cms_forum_favorite` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `update_time` DATETIME DEFAULT NULL,
  UNIQUE KEY `uk_fav_user_post` (`user_id`, `post_id`),
  KEY `idx_fav_ctime` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;