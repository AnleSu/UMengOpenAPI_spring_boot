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

 Date: 30/03/2020 09:38:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for EventInfo
-- ----------------------------
DROP TABLE IF EXISTS `EventInfo`;
CREATE TABLE `EventInfo` (
  `name` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
