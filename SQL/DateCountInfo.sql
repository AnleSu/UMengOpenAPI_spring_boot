/*
 Navicat Premium Data Transfer

 Source Server         : UMengOpenAPI_db
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : UMeng_api_db

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 30/03/2020 09:37:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for DateCountInfo
-- ----------------------------
DROP TABLE IF EXISTS `DateCountInfo`;
CREATE TABLE `DateCountInfo` (
  `date` varchar(255) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`date`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
