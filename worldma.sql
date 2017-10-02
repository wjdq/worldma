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

 Date: 02/10/2017 09:36:51
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
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '图片消息上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_msg
-- ----------------------------
DROP TABLE IF EXISTS `activity_msg`;
CREATE TABLE `activity_msg`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '待发送的活动信息',
  `type` enum('1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '消息的类型（1文字或2图片）',
  `status` enum('1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '1表示要发送的活动消息，2表示以过期的活动消息',
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_msg
-- ----------------------------
INSERT INTO `activity_msg` VALUES (1, '欢迎使用“World码”--扫码群发软件，该软件面向所有商户可免费使用', '1', '1', '2017-10-01 22:03:53');

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
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '客户扫码的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for client_count
-- ----------------------------
DROP TABLE IF EXISTS `client_count`;
CREATE TABLE `client_count`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '扫码客户计数表主键ID',
  `count` smallint(6) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录该扫码者第几次扫码',
  `uin` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '扫码客户的uin号',
  `createtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '扫码时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

