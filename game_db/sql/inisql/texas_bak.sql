/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : texas

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-05-16 15:58:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_activity`
-- ----------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `activityType` int(11) DEFAULT '0' COMMENT '»î¶¯ÀàÐÍ',
  `pageLink` int(11) DEFAULT '0' COMMENT '»î¶¯Á´½Ó',
  `title` varchar(50) DEFAULT NULL COMMENT '±êÌâ',
  `title_cn` varchar(50) DEFAULT NULL COMMENT '±êÌâ',
  `title_tw` varchar(50) DEFAULT NULL COMMENT '±êÌâ',
  `activityDesc` varchar(5000) DEFAULT NULL COMMENT 'ÃèÊö',
  `activityDesc_cn` varchar(5000) DEFAULT NULL COMMENT 'ÃèÊö',
  `activityDesc_tw` varchar(5000) DEFAULT NULL COMMENT 'ÃèÊö',
  `pic` varchar(100) DEFAULT NULL COMMENT '»î¶¯Í¼Æ¬',
  `pic_cn` varchar(100) DEFAULT NULL COMMENT '»î¶¯Í¼Æ¬',
  `pic_tw` varchar(100) DEFAULT NULL COMMENT '»î¶¯Í¼Æ¬',
  `rulePack` varchar(5000) DEFAULT NULL COMMENT '»î¶¯',
  `daily` int(11) DEFAULT '0' COMMENT 'Ã¿ÌìË¢ÐÂ',
  `startTime` bigint(20) DEFAULT '0' COMMENT '¿ªÊ¼Ê±¼ä',
  `endTime` bigint(20) DEFAULT '0' COMMENT '½áÊøÊ±¼ä',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_activity
-- ----------------------------

-- ----------------------------
-- Table structure for `t_baccart_room_model`
-- ----------------------------
DROP TABLE IF EXISTS `t_baccart_room_model`;
CREATE TABLE `t_baccart_room_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roomId` int(11) NOT NULL COMMENT '房间号',
  `stock` bigint(20) NOT NULL COMMENT '库存',
  `jackpot` bigint(20) NOT NULL COMMENT 'jackpot',
  `closed` int(11) NOT NULL COMMENT 'closed',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_baccart_room_model
-- ----------------------------

-- ----------------------------
-- Table structure for `t_client_resource_version`
-- ----------------------------
DROP TABLE IF EXISTS `t_client_resource_version`;
CREATE TABLE `t_client_resource_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源版本',
  `ver` int(11) NOT NULL COMMENT '版本号',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ver` (`ver`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_client_resource_version
-- ----------------------------
INSERT INTO `t_client_resource_version` VALUES ('1', '1', '0');

-- ----------------------------
-- Table structure for `t_client_version`
-- ----------------------------
DROP TABLE IF EXISTS `t_client_version`;
CREATE TABLE `t_client_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源版本',
  `version` varchar(50) NOT NULL COMMENT '版本号',
  `minVersion` varchar(50) NOT NULL COMMENT '最小版本号',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_client_version
-- ----------------------------
INSERT INTO `t_client_version` VALUES ('1', '1.0.0', '1.0.0', '0', '0');

-- ----------------------------
-- Table structure for `t_compensation`
-- ----------------------------
DROP TABLE IF EXISTS `t_compensation`;
CREATE TABLE `t_compensation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` varchar(1000) NOT NULL COMMENT '内容',
  `itemPack` varchar(2000) DEFAULT NULL COMMENT '补偿列表',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_compensation
-- ----------------------------

-- ----------------------------
-- Table structure for `t_db_version`
-- ----------------------------
DROP TABLE IF EXISTS `t_db_version`;
CREATE TABLE `t_db_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `updateTime` datetime NOT NULL,
  `version` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `version` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_db_version
-- ----------------------------

-- ----------------------------
-- Table structure for `t_device`
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deviceType` varchar(4096) DEFAULT NULL COMMENT '设备类型',
  `serverid` varchar(255) DEFAULT NULL COMMENT '服务器id',
  `userid` bigint(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `deviceId` varchar(255) DEFAULT NULL,
  `deviceMode` varchar(255) DEFAULT NULL,
  `osVersion` varchar(255) DEFAULT NULL,
  `channelType` int(11) DEFAULT '0',
  `clientVersion` varchar(255) DEFAULT NULL,
  `resourceVersion` int(11) DEFAULT '0',
  `updatetime` bigint(20) DEFAULT '0',
  `createtime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_device
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_activity`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_activity`;
CREATE TABLE `t_human_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) DEFAULT '0' COMMENT '½ÇÉ«id',
  `activityId` bigint(20) DEFAULT '0' COMMENT '»î¶¯id',
  `activityDataPack` varchar(5000) DEFAULT NULL COMMENT '»î¶¯Êý¾Ý',
  `rewardActivityDataPack` varchar(5000) DEFAULT NULL COMMENT '½±ÀøÊý¾Ý',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_activity
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_baccart`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_baccart`;
CREATE TABLE `t_human_baccart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `bankerNum` bigint(20) NOT NULL COMMENT '上庄数',
  `gameNum` bigint(20) NOT NULL COMMENT '游戏数',
  `winNum` bigint(20) NOT NULL COMMENT '赢次数',
  `beaconNum` bigint(20) NOT NULL COMMENT '明灯数',
  `lostNum` bigint(20) NOT NULL COMMENT '输次数',
  `isAuto` int(11) NOT NULL COMMENT '自动',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_baccart
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_compensation`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_compensation`;
CREATE TABLE `t_human_compensation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `compensationId` bigint(20) DEFAULT NULL COMMENT '补偿id',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_compensation
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_friend`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_friend`;
CREATE TABLE `t_human_friend` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `friendId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ºÃÓÑid',
  `giftTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '礼物时间',
  `facebook` int(11) NOT NULL DEFAULT '0' COMMENT 'facebook',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_friend
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_friend_gift`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_friend_gift`;
CREATE TABLE `t_human_friend_gift` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `friendId` bigint(20) NOT NULL DEFAULT '0' COMMENT '好友id',
  `getTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '领取时间',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_friend_gift
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_friend_request`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_friend_request`;
CREATE TABLE `t_human_friend_request` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `sendId` bigint(20) NOT NULL DEFAULT '0' COMMENT '·¢ËÍÕßid',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_friend_request
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_info`;
CREATE TABLE `t_human_info` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `passportId` bigint(20) DEFAULT NULL COMMENT 'passportId',
  `img` varchar(100) DEFAULT NULL COMMENT '图像',
  `name` varchar(36) DEFAULT NULL COMMENT '名称',
  `girlFlag` int(11) DEFAULT '0' COMMENT '女性标志',
  `level` bigint(20) NOT NULL DEFAULT '1' COMMENT '等级',
  `diamond` bigint(20) NOT NULL DEFAULT '0' COMMENT '平台币',
  `gold` bigint(20) NOT NULL DEFAULT '0' COMMENT '½ð±Ò',
  `coupon` bigint(20) NOT NULL DEFAULT '0' COMMENT 'µãÈ¯',
  `charm` bigint(20) NOT NULL DEFAULT '0' COMMENT '÷ÈÁ¦Öµ',
  `curExp` bigint(20) NOT NULL DEFAULT '0' COMMENT 'µ±Ç°¾­Ñé',
  `sceneId` int(11) NOT NULL DEFAULT '0' COMMENT '³¡¾°Id',
  `lastLoginIp` varchar(50) DEFAULT NULL COMMENT 'ÉÏ´ÎµÇÂ½IP',
  `lastLoginTime` bigint(20) DEFAULT NULL COMMENT 'ÉÏ´ÎµÇÂ½Ê±¼ä',
  `lastLogoutTime` bigint(20) DEFAULT NULL COMMENT 'ÉÏ´ÎµÇ³öÊ±¼ä',
  `totalMinute` int(11) NOT NULL DEFAULT '0' COMMENT 'ÀÛ¼ÆÔÚÏßÊ±³¤(·ÖÖÓ)',
  `onlineStatus` int(11) NOT NULL DEFAULT '0' COMMENT 'ÔÚÏß×´Ì¬',
  `idleTime` int(11) NOT NULL DEFAULT '0' COMMENT '¿ÕÏÐÊ±¼ä',
  `createTime` bigint(20) DEFAULT NULL COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `passportId` (`passportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_info
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_item`;
CREATE TABLE `t_human_item` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT '角色id',
  `templateId` int(11) NOT NULL COMMENT '模板id',
  `overlap` int(11) DEFAULT '0' COMMENT '叠加数',
  `beginTime` bigint(20) DEFAULT '0' COMMENT '开始时间',
  `duration` bigint(20) DEFAULT '0' COMMENT '持续时间',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_item
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_lucky_match`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_lucky_match`;
CREATE TABLE `t_human_lucky_match` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `lastMatchTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '上次免费时间',
  `poolPack` varchar(100) DEFAULT NULL COMMENT '次数',
  `freeTimes` int(11) NOT NULL DEFAULT '0' COMMENT '次数',
  `times` int(11) NOT NULL DEFAULT '0' COMMENT '次数',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_lucky_match
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_lucky_spin`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_lucky_spin`;
CREATE TABLE `t_human_lucky_spin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `lastSpinTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '上次免费时间',
  `freeTimes` int(11) NOT NULL DEFAULT '0' COMMENT '次数',
  `times` int(11) NOT NULL DEFAULT '0' COMMENT '次数',
  `poolPack` varchar(100) DEFAULT NULL COMMENT '次数',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_lucky_spin
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_misc`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_misc`;
CREATE TABLE `t_human_misc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) DEFAULT '0' COMMENT '½ÇÉ«id',
  `onlineTime` bigint(20) DEFAULT '0' COMMENT 'ÔÚÏßÊ±¼ä',
  `lastGetTime` bigint(20) DEFAULT '0' COMMENT 'ÉÏ´ÎÁìÈ¡Ê±¼ä',
  `currentOnlineRewardId` int(11) DEFAULT '0' COMMENT 'ÔÚÏß½±Àø',
  `firstRechargeTime` bigint(20) DEFAULT '0' COMMENT '首冲时间',
  `renameTimes` int(11) DEFAULT '0' COMMENT '改名次数',
  `adRewards` int(11) DEFAULT '0' COMMENT '广告奖励',
  `newUser` int(11) DEFAULT '0' COMMENT '新玩家',
  `fbInvitePack` varchar(5000) DEFAULT NULL,
  `fbInviteRewardPack` varchar(200) DEFAULT NULL,
  `fbReward` int(200) DEFAULT '0',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_misc
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_month_card`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_month_card`;
CREATE TABLE `t_human_month_card` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `beginTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¿ªÊ¼Ê±¼ä',
  `getTime` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ÁìÈ¡Ê±¼ä',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '³ÖÐøÊ±¼ä',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_month_card
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_recharge_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_recharge_order`;
CREATE TABLE `t_human_recharge_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) DEFAULT '0' COMMENT '½ÇÉ«id',
  `orderStatus` bigint(20) DEFAULT '0' COMMENT '×´Ì¬',
  `channelId` int(11) DEFAULT '0' COMMENT '渠道id',
  `productId` int(11) DEFAULT '0' COMMENT '²úÆ·id',
  `channelOrderId` varchar(100) DEFAULT '' COMMENT '渠道订单id',
  `cost` int(11) DEFAULT '0' COMMENT '金钱数',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_recharge_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_refund`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_refund`;
CREATE TABLE `t_human_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` varchar(1000) NOT NULL COMMENT '内容',
  `itemPack` varchar(2000) DEFAULT NULL COMMENT '补偿列表',
  `refundStatus` int(11) DEFAULT NULL COMMENT '状态',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_refund
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_slot`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_slot`;
CREATE TABLE `t_human_slot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `slotId` bigint(20) NOT NULL COMMENT '老虎机id',
  `totalBet` bigint(20) NOT NULL COMMENT '总共下注',
  `totalRefund` bigint(20) NOT NULL COMMENT '总共返还',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_slot
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_task`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_task`;
CREATE TABLE `t_human_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) DEFAULT '0' COMMENT '½ÇÉ«id',
  `dailyTaskPack` varchar(5000) DEFAULT NULL COMMENT 'ÈÕ³£ÈÎÎñ',
  `taskPack` varchar(5000) DEFAULT NULL COMMENT 'ÈÎÎñ',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_task
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_texas`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_texas`;
CREATE TABLE `t_human_texas` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `isAuto` int(11) NOT NULL DEFAULT '0' COMMENT '×Ô¶¯²¹³ä',
  `people` int(11) NOT NULL DEFAULT '0' COMMENT 'ÈËÊý',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '×Ü¾ÖÊý',
  `winCount` int(20) NOT NULL DEFAULT '0' COMMENT 'Ê¤Àû¾ÖÊý',
  `biggestHandCard` varchar(50) DEFAULT NULL COMMENT '×î´óÅÆÐÍ',
  `weekWinCoins` int(11) NOT NULL DEFAULT '0' COMMENT 'ÖÜÓ¯Àû',
  `dayBiggestWinCoins` int(11) NOT NULL DEFAULT '0' COMMENT 'ÈÕµ¥¾Ö×î¸ßÓ¯Àû',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_texas
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_texas_sng`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_texas_sng`;
CREATE TABLE `t_human_texas_sng` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `joinTimes` int(11) NOT NULL DEFAULT '0' COMMENT '参加次数',
  `goldNum` int(11) DEFAULT NULL COMMENT '金杯数',
  `silverNum` int(11) NOT NULL DEFAULT '0' COMMENT '银杯数',
  `copperNum` int(11) NOT NULL DEFAULT '0' COMMENT '铜杯数',
  `weekScore` int(20) NOT NULL DEFAULT '0' COMMENT '周分数',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_texas_sng
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_vip`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_vip`;
CREATE TABLE `t_human_vip` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) DEFAULT '0' COMMENT '½ÇÉ«id',
  `level` int(11) DEFAULT '0' COMMENT 'ÔÚÏßÊ±¼ä',
  `getTime` bigint(20) DEFAULT '0' COMMENT 'ÉÏ´ÎÁìÈ¡Ê±¼ä',
  `buyTime` bigint(20) DEFAULT '0' COMMENT 'ÔÚÏß½±Àø',
  `days` int(11) DEFAULT '0' COMMENT 'ÌìÊý',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_vip
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_week_card`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_week_card`;
CREATE TABLE `t_human_week_card` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `beginTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¿ªÊ¼Ê±¼ä',
  `getTime` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ÁìÈ¡Ê±¼ä',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '³ÖÐøÊ±¼ä',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_week_card
-- ----------------------------

-- ----------------------------
-- Table structure for `t_human_week_sign_in`
-- ----------------------------
DROP TABLE IF EXISTS `t_human_week_sign_in`;
CREATE TABLE `t_human_week_sign_in` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL COMMENT '½ÇÉ«id',
  `signInPack` varchar(50) DEFAULT NULL COMMENT 'Ç©µ½ÐÅÏ¢',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_human_week_sign_in
-- ----------------------------

-- ----------------------------
-- Table structure for `t_index_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_index_notice`;
CREATE TABLE `t_index_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cn` varchar(5000) NOT NULL COMMENT 'cn',
  `en` varchar(5000) NOT NULL COMMENT 'en',
  `tw` varchar(5000) NOT NULL COMMENT 'tw',
  `open` int(11) NOT NULL COMMENT '开启',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_index_notice
-- ----------------------------
INSERT INTO `t_index_notice` VALUES ('1', '', '', '', '0', '0', '0');

-- ----------------------------
-- Table structure for `t_mail_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_mail_info`;
CREATE TABLE `t_mail_info` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `sendId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ºÃÓÑid',
  `sendName` varchar(50) NOT NULL COMMENT '·¢ËÍÕßÐÕÃû',
  `recId` bigint(20) NOT NULL DEFAULT '0' COMMENT '½ÓÊÕÕßid',
  `recName` varchar(50) NOT NULL COMMENT '½ÓÊÕÕßÐÕÃû',
  `title` varchar(50) NOT NULL COMMENT '±êÌâ',
  `content` varchar(1000) NOT NULL COMMENT 'ÄÚÈÝ',
  `mailType` int(11) NOT NULL DEFAULT '0' COMMENT 'ÓÊ¼þÀàÐÍ',
  `mailStatus` int(11) NOT NULL DEFAULT '0' COMMENT 'ÓÊ¼þ×´Ì¬',
  `hasAttachment` int(11) NOT NULL DEFAULT '0' COMMENT '¸½¼þ',
  `attachmentPack` varchar(2000) DEFAULT NULL COMMENT '¸½¼þÄÚÈÝ',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mail_info
-- ----------------------------

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `startTime` bigint(20) DEFAULT '0' COMMENT '¿ªÊ¼Ê±¼ä',
  `endTime` bigint(20) DEFAULT '0' COMMENT '½áÊøÊ±¼ä',
  `dailyStartTime` int(11) DEFAULT '0' COMMENT 'Ã¿Ìì¿ªÊ¼Ê±¼ä',
  `dailyEndTime` int(11) DEFAULT '0' COMMENT 'Ã¿Ìì½áÊøÊ±¼ä',
  `intervalTime` int(11) DEFAULT '0' COMMENT '¼ä¸ôÊ±¼ä',
  `content` varchar(255) DEFAULT NULL COMMENT 'ÄÚÈÝ',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------

-- ----------------------------
-- Table structure for `t_robot_complement`
-- ----------------------------
DROP TABLE IF EXISTS `t_robot_complement`;
CREATE TABLE `t_robot_complement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源版本',
  `robotId` bigint(20) NOT NULL COMMENT '版本号',
  `complement` bigint(20) NOT NULL COMMENT '补签数',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_robot_complement
-- ----------------------------

-- ----------------------------
-- Table structure for `t_slot`
-- ----------------------------
DROP TABLE IF EXISTS `t_slot`;
CREATE TABLE `t_slot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `slotType` int(11) NOT NULL DEFAULT '0' COMMENT '老虎机类型',
  `jackpot` bigint(20) NOT NULL DEFAULT '0' COMMENT '彩金',
  `stock` bigint(20) NOT NULL DEFAULT '0' COMMENT '库存',
  `spinTimes` bigint(20) NOT NULL DEFAULT '0' COMMENT '次数',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_slot
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '登陆标识',
  `name` varchar(100) NOT NULL COMMENT '用户名',
  `accountId` varchar(100) DEFAULT NULL COMMENT '账号id',
  `facebookId` varchar(100) DEFAULT NULL COMMENT 'facebookid',
  `img` varchar(100) DEFAULT '' COMMENT '图像url',
  `joinTime` bigint(20) DEFAULT NULL COMMENT '帐号创建时间',
  `lastLoginTime` bigint(20) DEFAULT NULL COMMENT '上次登陆时间',
  `role` int(11) NOT NULL DEFAULT '0' COMMENT '权限',
  `lockStatus` int(11) NOT NULL DEFAULT '0' COMMENT '锁定状态',
  `muteTime` int(11) NOT NULL DEFAULT '0' COMMENT '锁定时间',
  `muteReason` varchar(256) DEFAULT NULL COMMENT '锁定原因',
  `deviceMac` varchar(50) DEFAULT NULL COMMENT '设备MAC地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `deviceMac` (`deviceMac`),
  KEY `accountId` (`accountId`),
  KEY `facebookId` (`facebookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_info
-- ----------------------------
