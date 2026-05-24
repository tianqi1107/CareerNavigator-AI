-- CareerNavigator-AI Database Schema
-- MySQL 8.0+

CREATE DATABASE IF NOT EXISTS careernavigator DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE careernavigator;

-- 1. 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
  `email` VARCHAR(100) UNIQUE COMMENT '邮箱',
  `phone` VARCHAR(20) UNIQUE COMMENT '手机号',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `avatar` VARCHAR(500) COMMENT '头像URL',
  `role` VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色: ADMIN/USER',
  `major` VARCHAR(100) COMMENT '专业',
  `education` VARCHAR(20) COMMENT '学历: 本科/硕士/博士',
  `university` VARCHAR(100) COMMENT '学校',
  `graduation_year` INT COMMENT '毕业年份',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
  `last_login_time` DATETIME COMMENT '最后登录时间',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX `idx_email` (`email`),
  INDEX `idx_phone` (`phone`),
  INDEX `idx_major` (`major`),
  INDEX `idx_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 专业表
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '专业ID',
  `major_code` VARCHAR(20) NOT NULL UNIQUE COMMENT '专业代码(如080701)',
  `major_name` VARCHAR(100) NOT NULL COMMENT '专业名称',
  `category` VARCHAR(50) COMMENT '学科门类(工学/理学/管理学等)',
  `first_level` VARCHAR(50) COMMENT '一级学科',
  `description` TEXT COMMENT '专业描述',
  `employment_direction` TEXT COMMENT '就业方向',
  `avg_salary` DECIMAL(10,2) COMMENT '平均年薪(万元)',
  `employment_rate` DECIMAL(5,2) COMMENT '就业率(%)',
  `hot_score` INT NOT NULL DEFAULT 0 COMMENT '热度评分',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_code` (`major_code`),
  INDEX `idx_name` (`major_name`),
  INDEX `idx_category` (`category`),
  INDEX `idx_hot` (`hot_score` DESC),
  FULLTEXT INDEX `ft_major` (`major_name`, `description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='专业表';

-- 3. 岗位分类表
DROP TABLE IF EXISTS `job_category`;
CREATE TABLE `job_category` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
  `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父分类ID, 0为一级分类',
  `name` VARCHAR(100) NOT NULL COMMENT '分类名称',
  `level` TINYINT NOT NULL COMMENT '层级: 1一级 2二级',
  `icon` VARCHAR(200) COMMENT '图标标识',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序序号',
  `description` TEXT COMMENT '分类描述',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `idx_parent` (`parent_id`),
  INDEX `idx_level` (`level`),
  INDEX `idx_sort` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='岗位分类表';

-- 4. 岗位表
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '岗位ID',
  `category_id` BIGINT NOT NULL COMMENT '分类ID',
  `job_name` VARCHAR(200) NOT NULL COMMENT '岗位名称',
  `company_name` VARCHAR(200) COMMENT '公司/单位名称',
  `company_type` VARCHAR(50) COMMENT '单位类型(国企/央企/私企/外企/事业单位/公务员)',
  `salary_min` DECIMAL(10,2) COMMENT '年薪下限(万元)',
  `salary_max` DECIMAL(10,2) COMMENT '年薪上限(万元)',
  `city` VARCHAR(50) COMMENT '工作城市',
  `province` VARCHAR(50) COMMENT '省份',
  `education_req` VARCHAR(20) COMMENT '学历要求(本科/硕士/博士)',
  `experience_req` VARCHAR(50) COMMENT '经验要求',
  `skills_required` JSON COMMENT '技能要求(JSON数组)',
  `description` TEXT COMMENT '岗位描述',
  `requirements` TEXT COMMENT '任职要求',
  `benefits` TEXT COMMENT '福利待遇',
  `is_civil_service` TINYINT NOT NULL DEFAULT 0 COMMENT '是否公务员岗位',
  `is_state_owned` TINYINT NOT NULL DEFAULT 0 COMMENT '是否国企/央企',
  `publish_date` DATE COMMENT '发布日期',
  `deadline` DATE COMMENT '截止日期',
  `source` VARCHAR(50) COMMENT '信息来源',
  `source_url` VARCHAR(500) COMMENT '来源链接',
  `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `hot_score` INT NOT NULL DEFAULT 0 COMMENT '热度评分',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0下架 1有效',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`category_id`) REFERENCES `job_category`(`id`),
  INDEX `idx_category` (`category_id`),
  INDEX `idx_city` (`city`),
  INDEX `idx_province` (`province`),
  INDEX `idx_company_type` (`company_type`),
  INDEX `idx_salary` (`salary_min`, `salary_max`),
  INDEX `idx_publish_date` (`publish_date`),
  INDEX `idx_hot_score` (`hot_score` DESC),
  INDEX `idx_civil_service` (`is_civil_service`),
  INDEX `idx_state_owned` (`is_state_owned`),
  FULLTEXT INDEX `ft_job` (`job_name`, `company_name`, `description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='岗位表';

-- 5. 专业-岗位关联表
DROP TABLE IF EXISTS `major_job_mapping`;
CREATE TABLE `major_job_mapping` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `major_id` BIGINT NOT NULL COMMENT '专业ID',
  `job_category_id` BIGINT NOT NULL COMMENT '岗位分类ID',
  `relevance_score` INT NOT NULL DEFAULT 50 COMMENT '匹配度评分(0-100)',
  `score_factors` JSON COMMENT '评分明细JSON',
  `recommendation_level` VARCHAR(10) COMMENT '推荐等级: S/A/B/C',
  `description` TEXT COMMENT '匹配说明',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`major_id`) REFERENCES `major`(`id`),
  FOREIGN KEY (`job_category_id`) REFERENCES `job_category`(`id`),
  UNIQUE INDEX `uk_major_job` (`major_id`, `job_category_id`),
  INDEX `idx_score` (`relevance_score` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='专业岗位关联表';

-- 6. AI分析记录表
DROP TABLE IF EXISTS `ai_analysis`;
CREATE TABLE `ai_analysis` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `analysis_type` VARCHAR(30) NOT NULL COMMENT '类型: CAREER_PLAN/RESUME_ANALYSIS/CHAT',
  `input_data` JSON COMMENT '输入参数',
  `output_data` JSON COMMENT 'AI输出结果',
  `ai_provider` VARCHAR(30) COMMENT 'AI供应商名称',
  `ai_model` VARCHAR(50) COMMENT 'AI模型名称',
  `tokens_used` INT COMMENT 'Token消耗量',
  `response_time` INT COMMENT '响应时间(ms)',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0失败 1成功',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  INDEX `idx_user_type` (`user_id`, `analysis_type`),
  INDEX `idx_created` (`created_at` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI分析记录表';

-- 7. 招聘信息表
DROP TABLE IF EXISTS `recruitment`;
CREATE TABLE `recruitment` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(300) NOT NULL COMMENT '招聘标题',
  `company` VARCHAR(200) COMMENT '招聘单位',
  `category_id` BIGINT COMMENT '分类ID',
  `positions` JSON COMMENT '招聘岗位列表',
  `headcount` INT COMMENT '招聘人数',
  `city` VARCHAR(50) COMMENT '城市',
  `education_req` VARCHAR(20) COMMENT '学历要求',
  `major_req` TEXT COMMENT '专业要求',
  `salary_range` VARCHAR(50) COMMENT '薪资范围',
  `publish_date` DATE COMMENT '发布日期',
  `deadline` DATE COMMENT '截止日期',
  `source` VARCHAR(50) COMMENT '来源平台',
  `source_url` VARCHAR(500) COMMENT '来源链接',
  `content` LONGTEXT COMMENT '招聘详情HTML',
  `status` TINYINT NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`category_id`) REFERENCES `job_category`(`id`),
  INDEX `idx_company` (`company`),
  INDEX `idx_deadline` (`deadline`),
  FULLTEXT INDEX `ft_recruit` (`title`, `company`, `major_req`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招聘信息表';

-- 8. 简历分析表
DROP TABLE IF EXISTS `resume_analysis`;
CREATE TABLE `resume_analysis` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `resume_file_url` VARCHAR(500) COMMENT '简历文件URL',
  `parsed_content` JSON COMMENT '解析后的简历内容',
  `target_job_id` BIGINT COMMENT '目标岗位ID',
  `ats_score` INT COMMENT 'ATS匹配分(0-100)',
  `missing_skills` JSON COMMENT '缺失技能列表',
  `ai_suggestions` JSON COMMENT 'AI优化建议',
  `status` TINYINT NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`target_job_id`) REFERENCES `job`(`id`),
  INDEX `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='简历分析表';

-- 9. 职业路线表
DROP TABLE IF EXISTS `career_path`;
CREATE TABLE `career_path` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `major_id` BIGINT COMMENT '关联专业ID',
  `job_category_id` BIGINT COMMENT '关联岗位分类ID',
  `path_name` VARCHAR(200) NOT NULL COMMENT '路线名称',
  `path_stages` JSON COMMENT '阶段列表JSON',
  `avg_years_to_senior` INT COMMENT '平均晋升年限',
  `difficulty_level` VARCHAR(10) COMMENT '难度: 低/中/高',
  `required_certs` JSON COMMENT '需要的证书JSON',
  `suggested_education` VARCHAR(20) COMMENT '建议学历',
  `description` TEXT COMMENT '路线描述',
  `status` TINYINT NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`major_id`) REFERENCES `major`(`id`),
  FOREIGN KEY (`job_category_id`) REFERENCES `job_category`(`id`),
  INDEX `idx_major` (`major_id`),
  INDEX `idx_category` (`job_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='职业路线表';
