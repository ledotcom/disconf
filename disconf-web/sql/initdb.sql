/** 创建表sql  **/
CREATE DATABASE IF NOT EXISTS `disconf`;
USE `disconf`;

CREATE TABLE `app` (
  `app_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一的ID（没有啥意义，主键，自增长而已）',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT 'APP名(一般是产品线+服务名)',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '介绍',
  `create_time` varchar(14) NOT NULL DEFAULT '99991231235959' COMMENT '生成时间',
  `update_time` varchar(14) NOT NULL DEFAULT '99991231235959' COMMENT '修改时',
  `emails` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱列表逗号分隔',
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='app';

CREATE TABLE `config` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一的ID（没有啥意义，主键，自增长而已）',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '配置文件/配置项',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '配置文件名/配置项KeY名',
  `value` text NOT NULL COMMENT '0 配置文件：文件的内容，1 配置项：配置值',
  `app_id` bigint(20) NOT NULL COMMENT 'appid',
  `version` varchar(255) NOT NULL DEFAULT 'DEFAULT_VERSION' COMMENT '版本',
  `env_id` bigint(20) NOT NULL COMMENT 'envid',
  `user_id` BIGINT(20) NOT NULL COMMENT 'userid',
  `create_time` varchar(14) NOT NULL DEFAULT '99991231235959' COMMENT '生成时间',
  `update_time` varchar(14) NOT NULL DEFAULT '99991231235959' COMMENT '修改时间',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8 COMMENT='配置';


CREATE TABLE `env` (
  `env_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '环境ID（主键，自增长）',
  `name` varchar(255) NOT NULL DEFAULT 'DEFAULT_ENV' COMMENT '环境名字',
  PRIMARY KEY (`env_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='rd/qa/local可以自定义，默认为 DEFAULT_ENV';

CREATE TABLE `role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名',
  `create_time` varchar(14) NOT NULL DEFAULT '99991231235959' COMMENT '创建时间',
  `create_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` varchar(14) NOT NULL DEFAULT '99991231235959' COMMENT '更新时间',
  `update_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `role_resource` (
  `role_res_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'role-resource id',
  `role_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户角色id',
  `url_pattern` varchar(200) NOT NULL DEFAULT '' COMMENT 'controller_requestMapping_value + method_requestMapping_value',
  `url_description` varchar(200) NOT NULL DEFAULT '' COMMENT 'url功能描述',
  `method_mask` varchar(4) NOT NULL DEFAULT '' COMMENT 'GET, PUT, POST, DELETE, 1: accessible',
  `update_time` varchar(14) NOT NULL DEFAULT '99991231235959' COMMENT '更新时间',
  PRIMARY KEY (`role_res_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='用户角色_url访问权限表';

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `token` varchar(255) NOT NULL COMMENT 'token',
  `ownapps` varchar(255) NOT NULL DEFAULT '' COMMENT '能操作的APPID,逗号分隔',
  `role_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '角色ID',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `config_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `config_id` BIGINT NOT NULL,
  `old_value` LONGTEXT NOT NULL,
  `new_value` LONGTEXT NOT NULL,
  `create_time` VARCHAR(14) NOT NULL DEFAULT '99991231235959',
  PRIMARY KEY (`id`)
)DEFAULT CHARSET=utf8 ENGINE=InnoDB;

ALTER TABLE `config`
	ADD COLUMN `status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '状态：1是正常 0是删除' AFTER `type`;

ALTER TABLE `config_history`
ADD COLUMN `update_by` BIGINT(20) NULL DEFAULT NULL
AFTER `create_time`;

INSERT INTO `role_resource` (`role_id`, `url_pattern`, `url_description`, `method_mask`) VALUES
  (1,'/api/account/password' , '修改密码' , '0100'),
  (2,'/api/account/password' , '修改密码' , '0100'),
  (3,'/api/account/password' , '修改密码' , '0000');

/** 初始化表sql **/

INSERT INTO `env` (`env_id`, `name`)
VALUES
    (1, 'local'),
    (2, 'test'),
    (3, 'preview'),
    (4, 'online');

INSERT INTO `role` (`role_id`, `role_name`, `create_time`, `create_by`, `update_time`, `update_by`)
VALUES
    (1, '普通人', '99991231235959', 2, '99991231235959', 2),
    (2, '管理员', '99991231235959', 2, '99991231235959', 2),
    (3, '测试管理员', '99991231235959', 2, '99991231235959', 2);

INSERT INTO `role_resource` (`role_res_id`, `role_id`, `url_pattern`, `url_description`, `method_mask`, `update_time`)
VALUES
    (1, 1, '/api/app/list', 'app列表', '1000', '99991231235959'),
    (2, 2, '/api/app/list', 'app列表', '1000', '99991231235959'),
    (3, 3, '/api/app/list', 'app列表', '1000', '99991231235959'),
    (4, 1, '/api/app', '生成一个app', '0010', '99991231235959'),
    (5, 2, '/api/app', '生成一个app', '0010', '99991231235959'),
    (6, 3, '/api/app', '生成一个app', '0000', '99991231235959'),
    (7, 1, '/api/env/list', 'env-list', '1000', '99991231235959'),
    (8, 2, '/api/env/list', 'env-list', '1000', '99991231235959'),
    (9, 3, '/api/env/list', 'env-list', '1000', '99991231235959'),
    (10, 1, '/api/account/session', '会话', '1000', '99991231235959'),
    (11, 2, '/api/account/session', '会话', '1000', '99991231235959'),
    (12, 3, '/api/account/session', '会话', '1000', '99991231235959'),
    (13, 1, '/api/account/signin', '登录', '1000', '99991231235959'),
    (14, 2, '/api/account/signin', '登录', '1000', '99991231235959'),
    (15, 3, '/api/account/signin', '登录', '1000', '99991231235959'),
    (16, 1, '/api/account/signout', '登出', '1000', '99991231235959'),
    (17, 2, '/api/account/signout', '登出', '1000', '99991231235959'),
    (18, 3, '/api/account/signout', '登出', '1000', '99991231235959'),
    (19, 1, '/api/config/item', '获取配置项', '1000', '99991231235959'),
    (20, 2, '/api/config/item', '获取配置项', '1000', '99991231235959'),
    (21, 3, '/api/config/item', '获取配置项', '1000', '99991231235959'),
    (22, 1, '/api/config/file', '获取配置文件', '1000', '99991231235959'),
    (23, 2, '/api/config/file', '获取配置文件', '1000', '99991231235959'),
    (24, 3, '/api/config/file', '获取配置文件', '1000', '99991231235959'),
    (25, 1, '/api/zoo/hosts', 'zoo', '1000', '99991231235959'),
    (26, 2, '/api/zoo/hosts', 'zoo', '1000', '99991231235959'),
    (27, 3, '/api/zoo/hosts', 'zoo', '1000', '99991231235959'),
    (28, 1, '/api/zoo/prefix', 'zoo', '1000', '99991231235959'),
    (29, 2, '/api/zoo/prefix', 'zoo', '1000', '99991231235959'),
    (30, 3, '/api/zoo/prefix', 'zoo', '1000', '99991231235959'),
    (31, 1, '/api/zoo/zkdeploy', 'zoo', '1000', '99991231235959'),
    (32, 2, '/api/zoo/zkdeploy', 'zoo', '1000', '99991231235959'),
    (33, 3, '/api/zoo/zkdeploy', 'zoo', '1000', '99991231235959'),
    (34, 1, '/api/web/config/item', '创建item-config', '0010', '99991231235959'),
    (35, 2, '/api/web/config/item', '创建item-config', '0010', '99991231235959'),
    (36, 3, '/api/web/config/item', '创建item-config', '0000', '99991231235959'),
    (37, 1, '/api/web/config/file', '创建file-config', '0010', '99991231235959'),
    (38, 2, '/api/web/config/file', '创建file-config', '0010', '99991231235959'),
    (39, 3, '/api/web/config/file', '创建file-config', '0000', '99991231235959'),
    (40, 1, '/api/web/config/filetext', '创建file-config', '0010', '99991231235959'),
    (41, 2, '/api/web/config/filetext', '创建file-config', '0010', '99991231235959'),
    (42, 3, '/api/web/config/filetext', '创建file-config', '0000', '99991231235959'),
    (43, 1, '/api/web/config/versionlist', '版本list', '1000', '99991231235959'),
    (44, 2, '/api/web/config/versionlist', '版本list', '1000', '99991231235959'),
    (45, 3, '/api/web/config/versionlist', '版本list', '1000', '99991231235959'),
    (46, 1, '/api/web/config/list', 'config-list', '1000', '99991231235959'),
    (47, 2, '/api/web/config/list', 'config-list', '1000', '99991231235959'),
    (48, 3, '/api/web/config/list', 'config-list', '1000', '99991231235959'),
    (49, 1, '/api/web/config/simple/list', 'config-list', '1000', '99991231235959'),
    (50, 2, '/api/web/config/simple/list', 'config-list', '1000', '99991231235959'),
    (51, 3, '/api/web/config/simple/list', 'config-list', '1000', '99991231235959'),
    (52, 1, '/api/web/config/{configId}', 'get/post', '1001', '99991231235959'),
    (53, 2, '/api/web/config/{configId}', 'get/post', '1001', '99991231235959'),
    (54, 3, '/api/web/config/{configId}', 'get/post', '1000', '99991231235959'),
    (55, 1, '/api/web/config/zk/{configId}', 'get-zk', '1000', '99991231235959'),
    (56, 2, '/api/web/config/zk/{configId}', 'get-zk', '1000', '99991231235959'),
    (57, 3, '/api/web/config/zk/{configId}', 'get-zk', '1000', '99991231235959'),
    (58, 1, '/api/web/config/download/{configId}', 'download', '1000', '99991231235959'),
    (59, 2, '/api/web/config/download/{configId}', 'download', '1000', '99991231235959'),
    (60, 3, '/api/web/config/download/{configId}', 'download', '1000', '99991231235959'),
    (61, 1, '/api/web/config/downloadfilebatch', 'download', '1000', '99991231235959'),
    (62, 2, '/api/web/config/downloadfilebatch', 'download', '1000', '99991231235959'),
    (63, 3, '/api/web/config/downloadfilebatch', 'download', '1000', '99991231235959'),
    (64, 1, '/api/web/config/item/{configId}', 'update', '0100', '99991231235959'),
    (65, 2, '/api/web/config/item/{configId}', 'update', '0100', '99991231235959'),
    (66, 3, '/api/web/config/item/{configId}', 'update', '0000', '99991231235959'),
    (67, 1, '/api/web/config/file/{configId}', 'update/post', '0010', '99991231235959'),
    (68, 2, '/api/web/config/file/{configId}', 'update/post', '0010', '99991231235959'),
    (69, 3, '/api/web/config/file/{configId}', 'update/post', '0000', '99991231235959'),
    (70, 1, '/api/web/config/filetext/{configId}', 'update', '0100', '99991231235959'),
    (71, 2, '/api/web/config/filetext/{configId}', 'update', '0100', '99991231235959'),
    (72, 3, '/api/web/config/filetext/{configId}', 'update', '0000', '99991231235959');

/* admin_read  111111*/
/* admin    admin*/
/* test  111111*/
/** user_id值不能变化 */    
INSERT INTO `user` (`user_id`, `name`, `password`, `token`, `ownapps`, `role_id`)
VALUES
    (1, 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'f28d164d23291c732f64134e6b7d92be3ff8b1b3', '', 2),
    (2, 'admin_read', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d', '2022ab9c2754d62f9ddba5fded91e4238247ebaf', '', 3),
    (3, 'test', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d', 'b9070d385a13357efa09e50e080607c2b299241b', '', 1),
    (4, 'operation', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d', 'b9070d385a13357efa09e50e080607c2b299241b', '', 1);  
    
    
