/*
 Navicat MySQL Data Transfer

 Source Server         : hong
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : worldma

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 28/09/2017 14:11:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity_img_path
-- ----------------------------
DROP TABLE IF EXISTS `activity_img_path`;
CREATE TABLE `activity_img_path`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_img_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '保存用户上传的图片消息在服务器中的路径',
  `activity_msg_id` int(11) NOT NULL COMMENT 'activity_msg表主键ID',
  `user_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '图片消息上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_msg
-- ----------------------------
DROP TABLE IF EXISTS `activity_msg`;
CREATE TABLE `activity_msg`  (
  `id` int(11) UNSIGNED NOT NULL,
  `msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '待发送的活动信息',
  `type` enum('1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '消息的类型（1文字或2图片）',
  `status` enum('1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '1表示要发送的活动消息，2表示以过期的活动消息',
  `user_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '添加消息的用户账号',
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '存放扫码用户的主键ID',
  `nick_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扫码客户的昵称',
  `sex` enum('0','1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '扫码客户性别0保密1男2女',
  `city` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扫码客户地址',
  `friend_total` int(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '扫码客户好友总数',
  `uin` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '扫码者的uin号',
  `send_success_count` smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '本次发送成功计数',
  `send_fail_count` smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '本次发送失败计数',
  `user_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该扫码者隶属于该user_number',
  `friend_count` smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '扫码客户好友数',
  `group_count` smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '扫码客户群组数',
  `public_count` smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '扫码客户公众号数',
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '客户扫码的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for client_count
-- ----------------------------
DROP TABLE IF EXISTS `client_count`;
CREATE TABLE `client_count`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '扫码客户计数表主键ID',
  `count` smallint(6) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录该扫码者第几次扫码',
  `uin` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '扫码客户的uin号',
  `user_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该计数隶属于该user_number',
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '扫码时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for client_friends
-- ----------------------------
DROP TABLE IF EXISTS `client_friends`;
CREATE TABLE `client_friends`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户好友的昵称',
  `remark_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户好友的备注名称',
  `sex` enum('0','1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '客户性别0保密1男2女',
  `city` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户好友的地址',
  `client_id` int(10) UNSIGNED NOT NULL COMMENT 'client表主键ID',
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_authorities
-- ----------------------------
DROP TABLE IF EXISTS `group_authorities`;
CREATE TABLE `group_authorities`  (
  `group_id` bigint(20) NOT NULL,
  `authority` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `fk_group_authorities_group`(`group_id`) USING BTREE,
  CONSTRAINT `fk_group_authorities_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_members
-- ----------------------------
DROP TABLE IF EXISTS `group_members`;
CREATE TABLE `group_members`  (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_group_members_group`(`group_id`) USING BTREE,
  CONSTRAINT `fk_group_members_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups`  (
  `id` bigint(20) NOT NULL,
  `group_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_send_count
-- ----------------------------
DROP TABLE IF EXISTS `user_send_count`;
CREATE TABLE `user_send_count`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '该user_number发送消息总数量统计表ID',
  `total` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录总共发送多少次',
  `day_count` smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '今天发送次数',
  `user_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该信息隶属于该user_number',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wm_user_authorities
-- ----------------------------
DROP TABLE IF EXISTS `wm_user_authorities`;
CREATE TABLE `wm_user_authorities`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户权限表主键ID',
  `authority` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该用户所拥有的角色',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ix_auth_usernumber`(`username`, `authority`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_user_authorities
-- ----------------------------
INSERT INTO `wm_user_authorities` VALUES (4, 'ROLE_ADMIN', 'abc');
INSERT INTO `wm_user_authorities` VALUES (5, 'ROLE_USER', 'abc');
INSERT INTO `wm_user_authorities` VALUES (2, 'ROLE_ADMIN', 'bbc');
INSERT INTO `wm_user_authorities` VALUES (3, 'ROLE_USER', 'bbc');
INSERT INTO `wm_user_authorities` VALUES (1, 'ROLE_USER', 'bob');

-- ----------------------------
-- Table structure for wm_users
-- ----------------------------
DROP TABLE IF EXISTS `wm_users`;
CREATE TABLE `wm_users`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'World码用户表主键ID',
  `tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册用户手机号',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '注册用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '注册用户密码',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '账号可用状态',
  `user_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '注册用户号',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册用户邮箱地址',
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '账号创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `wm_user_number`(`user_number`) USING BTREE,
  UNIQUE INDEX `wm_user_tel`(`tel`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_users
-- ----------------------------
INSERT INTO `wm_users` VALUES (1, '13888888888', 'abc', 'abc', 1, '1234567', NULL, '2017-09-23 10:32:15');
INSERT INTO `wm_users` VALUES (2, '13555555555', 'bob', 'bob', 1, '7654321', NULL, '2017-09-23 10:32:15');
INSERT INTO `wm_users` VALUES (3, '13777777777', 'bbc', 'bbc', 1, '2345678', NULL, '2017-09-23 19:34:33');

-- ----------------------------
-- Event structure for update_userSendCount
-- ----------------------------
DROP EVENT IF EXISTS `update_userSendCount`;
delimiter ;;
CREATE DEFINER = `root`@`localhost` EVENT `update_userSendCount`
ON SCHEDULE
EVERY '1' DAY STARTS '2017-09-24 23:59:59'
COMMENT '每天自动清除（user_send_count表）客户当天发生消息数量'
DO update worldma.user_send_count set day_count=0
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
