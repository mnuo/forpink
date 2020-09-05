/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50520
 Source Host           : 127.0.0.1:3306
 Source Schema         : pink

 Target Server Type    : MySQL
 Target Server Version : 50520
 File Encoding         : 65001

 Date: 05/09/2020 11:47:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission_info
-- ----------------------------
DROP TABLE IF EXISTS `permission_info`;
CREATE TABLE `permission_info`  (
  `id` bigint(20) NOT NULL COMMENT '权限id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口路径',
  `description` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '说明',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` enum('enable','disable') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'enable' COMMENT '角色状态\'ENABLE\'开启,\'DISABLE\'关闭',
  `method` enum('GET','POST','PUT','DELETE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'GET' COMMENT '请求方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission_info
-- ----------------------------
INSERT INTO `permission_info` VALUES (2, 'get', '/test/user', NULL, NULL, NULL, '2020-09-04 15:26:30', NULL, '0000-00-00 00:00:00', 'enable', 'GET');
INSERT INTO `permission_info` VALUES (2131231, 'get', '/security/users/**', NULL, NULL, NULL, '2020-07-03 16:31:55', NULL, '2020-07-03 16:31:55', 'enable', 'GET');
INSERT INTO `permission_info` VALUES (213123123213, 'post', '/security/users/**', NULL, NULL, NULL, '2020-07-03 16:31:55', NULL, '2020-07-03 16:31:55', 'enable', 'POST');

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info`  (
  `id` bigint(20) NOT NULL COMMENT '角色id',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色说明',
  `role_level` int(2) DEFAULT NULL COMMENT '角色等级  从小到大  1大于2',
  `status` enum('enable','disable') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'enable' COMMENT '启用\'ENABLE\'      关闭\'DISABLE\'',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_info
-- ----------------------------
INSERT INTO `role_info` VALUES (2, 'TEST', 'test', 'test', 1, 'enable', NULL, '2020-09-04 15:42:59', NULL, '2020-07-03 16:31:55');
INSERT INTO `role_info` VALUES (3123123, 'ADMIN', 'admin', 'ADMIN', 1, 'enable', NULL, '2020-09-01 15:00:36', NULL, '2020-07-03 16:31:55');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint(20) NOT NULL COMMENT '角色与权限关系id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `map_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `map_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (2, 2, 2);
INSERT INTO `role_permission` VALUES (1233211, 3123123, 2131231321);

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill`  (
  `seckill_id` bigint(20) NOT NULL,
  `name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `number` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `version` int(11) NOT NULL COMMENT '商品库存id\r\n商品名称\r\n库存数量\r\n秒杀开启时间\r\n秒杀结束时间\r\n创建时间\r\n版本号\r\n',
  PRIMARY KEY (`seckill_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES (1000, '1000元秒杀iphone8', 100, '2020-05-21 18:50:11', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);
INSERT INTO `seckill` VALUES (1001, '500元秒杀ipad2', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);
INSERT INTO `seckill` VALUES (1002, '300元秒杀小米4', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);
INSERT INTO `seckill` VALUES (1003, '200元秒杀红米note', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);

-- ----------------------------
-- Table structure for seckill_bak
-- ----------------------------
DROP TABLE IF EXISTS `seckill_bak`;
CREATE TABLE `seckill_bak`  (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`seckill_id`) USING BTREE,
  INDEX `idx_start_time`(`start_time`) USING BTREE,
  INDEX `idx_end_time`(`end_time`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1004 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀库存表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of seckill_bak
-- ----------------------------
INSERT INTO `seckill_bak` VALUES (1000, '1000元秒杀iphone8', 0, '2020-05-21 18:50:11', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 100);
INSERT INTO `seckill_bak` VALUES (1001, '500元秒杀ipad2', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);
INSERT INTO `seckill_bak` VALUES (1002, '300元秒杀小米4', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);
INSERT INTO `seckill_bak` VALUES (1003, '200元秒杀红米note', 100, '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', 0);

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed`  (
  `success_kill_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `seckill_id` bigint(20) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`success_kill_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for success_killed_bak
-- ----------------------------
DROP TABLE IF EXISTS `success_killed_bak`;
CREATE TABLE `success_killed_bak`  (
  `success_kill_id` bigint(20) NOT NULL,
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `state` tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`success_kill_id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀成功明细表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of success_killed_bak
-- ----------------------------
INSERT INTO `success_killed_bak` VALUES (6669187512847564800, 1000, 1, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187512956616704, 1000, 4, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513069862912, 1000, 8, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513099223040, 1000, 12, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513157943296, 1000, 16, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513183109120, 1000, 19, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513237635072, 1000, 22, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513296355328, 1000, 25, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513350881280, 1000, 27, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513384435712, 1000, 31, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513468321792, 1000, 34, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513493487616, 1000, 39, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513522847744, 1000, 40, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513552207872, 1000, 42, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513598345216, 1000, 45, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513648676864, 1000, 48, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513673842688, 1000, 50, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513699008512, 1000, 53, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513753534464, 1000, 56, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513778700288, 1000, 57, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513820643328, 1000, 55, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513875169280, 1000, 59, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513908723712, 1000, 61, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513933889536, 1000, 63, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187513992609792, 1000, 65, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514013581312, 1000, 69, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514042941440, 1000, 71, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514076495872, 1000, 74, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514097467392, 1000, 78, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514110050304, 1000, 79, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514131021824, 1000, 81, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514147799040, 1000, 82, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514181353472, 1000, 85, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514214907904, 1000, 86, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514240073728, 1000, 90, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514290405376, 1000, 92, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514323959808, 1000, 94, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514349125632, 1000, 98, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514361708544, 1000, 100, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514386874368, 1000, 103, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514399457280, 1000, 104, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514462371840, 1000, 107, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514487537664, 1000, 109, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514529480704, 1000, 113, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514596589568, 1000, 115, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514625949696, 1000, 117, 0, '2020-05-21 18:50:10');
INSERT INTO `success_killed_bak` VALUES (6669187514638532608, 1000, 119, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187514726612992, 1000, 114, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187514760167424, 1000, 123, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187514831470592, 1000, 126, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515062157312, 1000, 128, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515167014912, 1000, 131, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515196375040, 1000, 135, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515217346560, 1000, 138, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515255095296, 1000, 141, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515280261120, 1000, 146, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515301232640, 1000, 147, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515318009856, 1000, 151, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515338981376, 1000, 153, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515364147200, 1000, 154, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515372535808, 1000, 156, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515393507328, 1000, 160, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515414478848, 1000, 163, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515435450368, 1000, 166, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515548696576, 1000, 169, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515590639616, 1000, 172, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515624194048, 1000, 177, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515645165568, 1000, 176, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515674525696, 1000, 180, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515687108608, 1000, 181, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515708080128, 1000, 184, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515745828864, 1000, 188, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515766800384, 1000, 191, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515796160512, 1000, 193, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515821326336, 1000, 196, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515850686464, 1000, 197, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515867463680, 1000, 199, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515884240896, 1000, 201, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515905212416, 1000, 204, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515921989632, 1000, 206, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515938766848, 1000, 210, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515963932672, 1000, 213, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187515997487104, 1000, 216, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516022652928, 1000, 219, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516039430144, 1000, 220, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516077178880, 1000, 223, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516144287744, 1000, 226, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516165259264, 1000, 230, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516182036480, 1000, 231, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516203008000, 1000, 234, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516223979520, 1000, 235, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516240756736, 1000, 238, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516265922560, 1000, 241, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516299476992, 1000, 243, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516387557376, 1000, 247, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516429500416, 1000, 250, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516496609280, 1000, 253, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516551135232, 1000, 256, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516639215616, 1000, 258, 0, '2020-05-21 18:50:11');
INSERT INTO `success_killed_bak` VALUES (6669187516660187136, 1000, 260, 0, '2020-05-21 18:50:11');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(20) NOT NULL COMMENT '用户与角色关系id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 3123123);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (21312312, 123, 3123123);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(20) NOT NULL COMMENT '用户主键',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `pass_word` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像地址',
  `mobile` int(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `status` enum('enable','disable') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'disable' COMMENT '是否生效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE COMMENT '唯一索引  用户名'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '123123', '', NULL, NULL, 'admin', 'enable');
INSERT INTO `users` VALUES (2, 'test', '123123', '123123', NULL, NULL, 'test', 'enable');
INSERT INTO `users` VALUES (123, 'liuheming', '$2a$10$6DS95TrR8gGshNu3mNs3MeQE6wdQKh3DrAR/VArSMVyiHHOR0fUbC', '', NULL, NULL, 'liuheming', 'enable');

SET FOREIGN_KEY_CHECKS = 1;
