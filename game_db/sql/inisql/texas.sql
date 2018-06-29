-- MySQL dump 10.13  Distrib 5.6.25, for Win64 (x86_64)
--
-- Host: localhost    Database: texas
-- ------------------------------------------------------
-- Server version	5.6.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_activity`
--

DROP TABLE IF EXISTS `t_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `activityType` int(11) DEFAULT '0' COMMENT '活动类型',
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
  `rulePack` varchar(5000) DEFAULT NULL COMMENT '活动明细',
  `daily` int(11) DEFAULT '0' COMMENT '判断是否是隔天刷新 1是隔天刷新',
  `startTime` bigint(20) DEFAULT '0' COMMENT '¿ªÊ¼Ê±¼ä',
  `endTime` bigint(20) DEFAULT '0' COMMENT '½áÊøÊ±¼ä',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_baccart_room_model`
--

DROP TABLE IF EXISTS `t_baccart_room_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_baccart_room_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roomId` int(11) NOT NULL COMMENT '房间号',
  `stock` bigint(20) NOT NULL COMMENT '库存',
  `jackpot` bigint(20) NOT NULL COMMENT 'jackpot',
  `closed` int(11) NOT NULL COMMENT 'closed',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6196952583179504646 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_client_resource_version`
--

DROP TABLE IF EXISTS `t_client_resource_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_client_resource_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源版本',
  `ver` int(11) NOT NULL COMMENT '版本号',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ver` (`ver`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_client_version`
--

DROP TABLE IF EXISTS `t_client_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_client_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源版本',
  `version` varchar(50) NOT NULL COMMENT '版本号',
  `minVersion` varchar(50) NOT NULL COMMENT '最小版本号',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_compensation`
--

DROP TABLE IF EXISTS `t_compensation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_compensation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` varchar(1000) NOT NULL COMMENT '内容',
  `itemPack` varchar(2000) DEFAULT NULL COMMENT '补偿列表',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_conversioncode`
--

DROP TABLE IF EXISTS `t_conversioncode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_conversioncode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `conversionCode` varchar(1000) NOT NULL DEFAULT '' COMMENT '兑换码',
  `gold` bigint(20) NOT NULL DEFAULT '0' COMMENT '赠送的筹码',
  `endTime` bigint(20) NOT NULL COMMENT '结束时间',
  `isdelete` int(11) DEFAULT '0' COMMENT '是否有效 1 表示无效的',
  `updateTime` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_db_version`
--

DROP TABLE IF EXISTS `t_db_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_db_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `updateTime` datetime NOT NULL,
  `version` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `version` (`version`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_delivery`
--

DROP TABLE IF EXISTS `t_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` varchar(255) DEFAULT '' COMMENT '美元',
  `extraparam1` varchar(255) DEFAULT '' COMMENT '订单号',
  `time` varchar(255) DEFAULT '' COMMENT '请求时间',
  `sign` varchar(255) DEFAULT NULL,
  `roleid` varchar(255) DEFAULT NULL,
  `gold` varchar(255) DEFAULT NULL,
  `merchantref` varchar(255) DEFAULT NULL,
  `zoneid` varchar(255) DEFAULT NULL,
  `pay_type` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_device`
--

DROP TABLE IF EXISTS `t_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=611 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_achievement`
--

DROP TABLE IF EXISTS `t_human_achievement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_achievement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `charId` bigint(20) NOT NULL COMMENT '角色ID',
  `achValue` text COMMENT '（id:state(1 没有完成 2 已经完成但没有领取 3 已经领取)：time:时间）',
  `achDate` text COMMENT '大类型小类型累计值',
  `slotRotate` bigint(20) DEFAULT '0',
  `slotWin` bigint(20) DEFAULT '0',
  `slotSingleWin` bigint(20) DEFAULT '0',
  `updateTime` bigint(20) DEFAULT '0',
  `createTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_activity`
--

DROP TABLE IF EXISTS `t_human_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) DEFAULT '0' COMMENT '½ÇÉ«id',
  `activityId` bigint(20) DEFAULT '0' COMMENT '»î¶¯id',
  `activityDataPack` varchar(5000) DEFAULT NULL COMMENT '»î¶¯Êý¾Ý',
  `rewardActivityDataPack` varchar(5000) DEFAULT NULL COMMENT '½±ÀøÊý¾Ý',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_baccart`
--

DROP TABLE IF EXISTS `t_human_baccart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_compensation`
--

DROP TABLE IF EXISTS `t_human_compensation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_compensation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `compensationId` bigint(20) DEFAULT NULL COMMENT '补偿id',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_friend`
--

DROP TABLE IF EXISTS `t_human_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_friend` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `friendId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ºÃÓÑid',
  `giftTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '礼物时间',
  `facebook` int(11) NOT NULL DEFAULT '0' COMMENT 'facebook',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_friend_gift`
--

DROP TABLE IF EXISTS `t_human_friend_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_friend_gift` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `friendId` bigint(20) NOT NULL DEFAULT '0' COMMENT '好友id',
  `getTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '领取时间',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_friend_request`
--

DROP TABLE IF EXISTS `t_human_friend_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_friend_request` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `sendId` bigint(20) NOT NULL DEFAULT '0' COMMENT '·¢ËÍÕßid',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_gift`
--

DROP TABLE IF EXISTS `t_human_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_gift` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `charId` bigint(20) NOT NULL COMMENT '角色ID',
  `giftid` int(11) DEFAULT NULL COMMENT '优惠弹出礼包',
  `refreshTime` bigint(20) DEFAULT NULL COMMENT '优惠弹出礼包弹出世间点',
  `updateTime` bigint(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_info`
--

DROP TABLE IF EXISTS `t_human_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_info` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `passportId` bigint(20) DEFAULT NULL COMMENT 'passportId',
  `img` varchar(2000) DEFAULT NULL COMMENT '图像',
  `name` varchar(36) DEFAULT NULL COMMENT '名称',
  `girlFlag` int(11) DEFAULT '0' COMMENT '女性标志',
  `level` bigint(20) NOT NULL DEFAULT '1' COMMENT '等级',
  `diamond` bigint(20) NOT NULL DEFAULT '0' COMMENT '平台币',
  `gold` bigint(20) NOT NULL DEFAULT '0' COMMENT '金币',
  `coupon` bigint(20) NOT NULL DEFAULT '0' COMMENT '点券',
  `charm` bigint(20) NOT NULL DEFAULT '0' COMMENT '魅力值',
  `curExp` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前经验',
  `sceneId` int(11) NOT NULL DEFAULT '0' COMMENT '所在场景Id',
  `lastLoginIp` varchar(50) DEFAULT NULL COMMENT '上次登陆IP',
  `lastLoginTime` bigint(20) DEFAULT NULL COMMENT '上次登陆时间',
  `lastLogoutTime` bigint(20) DEFAULT NULL COMMENT '上次登出时间',
  `totalMinute` int(11) NOT NULL DEFAULT '0' COMMENT '累计在线时长(分钟)',
  `onlineStatus` int(11) NOT NULL DEFAULT '0' COMMENT '在线状态',
  `idleTime` int(11) NOT NULL DEFAULT '0' COMMENT '空闲时间',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `isPay` bigint(20) NOT NULL DEFAULT '0' COMMENT '首充',
  `gameview` varchar(1000) DEFAULT '' COMMENT 'gameview首充记录',
  `newguide` varchar(1000) DEFAULT '' COMMENT '新手引导',
  `addfriendIds` varchar(2000) DEFAULT '' COMMENT '添加好友ID隔天清空',
  `receivecode` varchar(2000) DEFAULT '' COMMENT '领取随机码',
  `watchNum` int(11) DEFAULT '0' COMMENT '视频观看次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `passportId` (`passportId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_item`
--

DROP TABLE IF EXISTS `t_human_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_lucky_match`
--

DROP TABLE IF EXISTS `t_human_lucky_match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_lucky_spin`
--

DROP TABLE IF EXISTS `t_human_lucky_spin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_misc`
--

DROP TABLE IF EXISTS `t_human_misc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `fbThumb` int(11) DEFAULT '0' COMMENT '点赞 1 已经领取过奖了了',
  `refreshTime` bigint(20) DEFAULT '0' COMMENT '刷新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_month_card`
--

DROP TABLE IF EXISTS `t_human_month_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_month_card` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `beginTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¿ªÊ¼Ê±¼ä',
  `getTime` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ÁìÈ¡Ê±¼ä',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '³ÖÐøÊ±¼ä',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_recharge_order`
--

DROP TABLE IF EXISTS `t_human_recharge_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `topUpType` int(11) DEFAULT '0' COMMENT '支付类型0 普通 1mycard 2mol',
  `authCode` varchar(500) DEFAULT '' COMMENT 'mycard 授权码',
  `tradeSeq` varchar(255) DEFAULT '' COMMENT 'MyCard 交易序號',
  `paymentType` varchar(255) DEFAULT '' COMMENT '付費方式',
  `myCardTradeNo` varchar(255) DEFAULT '',
  `userId` varchar(500) DEFAULT '' COMMENT '亚马逊客户账号',
  `receiptId` varchar(500) DEFAULT '' COMMENT '亚马逊收据ID',
  `currencyCode` varchar(255) DEFAULT '' COMMENT 'mol货币类型',
  `amountmol` int(11) DEFAULT '0' COMMENT '单位是分mol支付',
  `paymentIdmol` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6237941597965290499 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_refund`
--

DROP TABLE IF EXISTS `t_human_refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_slot`
--

DROP TABLE IF EXISTS `t_human_slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_slot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `slotId` bigint(20) NOT NULL COMMENT '老虎机id',
  `totalBet` bigint(20) NOT NULL COMMENT '总共下注',
  `totalRefund` bigint(20) NOT NULL COMMENT '总共返还',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `totalJackpot` bigint(20) NOT NULL COMMENT '总获得彩金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162986 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_task`
--

DROP TABLE IF EXISTS `t_human_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) DEFAULT '0' COMMENT '½ÇÉ«id',
  `dailyTaskPack` varchar(5000) DEFAULT NULL COMMENT 'ÈÕ³£ÈÎÎñ',
  `taskPack` varchar(5000) DEFAULT NULL COMMENT 'ÈÎÎñ',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_texas`
--

DROP TABLE IF EXISTS `t_human_texas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_texas` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT '玩家角色ID',
  `isAuto` int(11) NOT NULL DEFAULT '0' COMMENT '是否自动补充',
  `people` int(11) NOT NULL DEFAULT '0' COMMENT '人数',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '玩的局数',
  `winCount` int(20) NOT NULL DEFAULT '0' COMMENT '胜利局数',
  `biggestHandCard` varchar(50) DEFAULT NULL COMMENT '最大牌型',
  `weekWinCoins` int(11) NOT NULL DEFAULT '0' COMMENT '周盈利',
  `dayBiggestWinCoins` int(11) NOT NULL DEFAULT '0' COMMENT '单日最高盈利',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_texas_sng`
--

DROP TABLE IF EXISTS `t_human_texas_sng`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_vip`
--

DROP TABLE IF EXISTS `t_human_vip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_vip_new`
--

DROP TABLE IF EXISTS `t_human_vip_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_vip_new` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `humanId` bigint(20) NOT NULL COMMENT '角色ID',
  `vipLevel` int(11) DEFAULT NULL COMMENT 'vip等级',
  `curPoint` int(11) DEFAULT NULL COMMENT '当前vip点',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162948 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_week_card`
--

DROP TABLE IF EXISTS `t_human_week_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_week_card` (
  `id` bigint(20) NOT NULL COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Íæ¼Ò½ÇÉ«ID',
  `beginTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¿ªÊ¼Ê±¼ä',
  `getTime` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ÁìÈ¡Ê±¼ä',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '³ÖÐøÊ±¼ä',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_human_week_sign_in`
--

DROP TABLE IF EXISTS `t_human_week_sign_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_human_week_sign_in` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `charId` bigint(20) NOT NULL COMMENT '½ÇÉ«id',
  `signInPack` varchar(50) DEFAULT NULL COMMENT 'Ç©µ½ÐÅÏ¢',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6239275903950162947 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_index_notice`
--

DROP TABLE IF EXISTS `t_index_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_index_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cn` varchar(5000) NOT NULL COMMENT 'cn',
  `en` varchar(5000) NOT NULL COMMENT 'en',
  `tw` varchar(5000) NOT NULL COMMENT 'tw',
  `open` int(11) NOT NULL COMMENT '开启',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_mail_info`
--

DROP TABLE IF EXISTS `t_mail_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `charId` (`charId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_notice`
--

DROP TABLE IF EXISTS `t_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_rank`
--

DROP TABLE IF EXISTS `t_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `charId` bigint(20) NOT NULL COMMENT '角色ID',
  `daySingleJackpot` bigint(20) DEFAULT NULL COMMENT '今日单居最大赢取彩金',
  `dayTotalJackpot` bigint(20) DEFAULT NULL COMMENT '今日总的累计彩金',
  `singleJackpot` bigint(20) DEFAULT NULL COMMENT '注册到现在最大的彩金',
  `totalJackpot` bigint(20) DEFAULT NULL COMMENT '注册到现在累计彩金',
  `viplevel` int(11) DEFAULT NULL COMMENT 'vip等级',
  `updateTime` bigint(20) NOT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6239276083105664003 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_robot_complement`
--

DROP TABLE IF EXISTS `t_robot_complement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_robot_complement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源版本',
  `robotId` bigint(20) NOT NULL COMMENT '版本号',
  `complement` bigint(20) NOT NULL COMMENT '补签数',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6238234810773048323 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_slot`
--

DROP TABLE IF EXISTS `t_slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_slot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `slotType` int(11) NOT NULL DEFAULT '0' COMMENT '老虎机类型',
  `jackpot` bigint(20) NOT NULL DEFAULT '0' COMMENT '彩金',
  `stock` bigint(20) NOT NULL DEFAULT '0' COMMENT '库存',
  `spinTimes` bigint(20) NOT NULL DEFAULT '0' COMMENT '次数',
  `updateTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `cumuJackpot` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计彩金池',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6215058648026745862 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_textas`
--

DROP TABLE IF EXISTS `t_textas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_textas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `texasId` int(11) DEFAULT '0' COMMENT '德州房间ID',
  `jackpot` bigint(20) DEFAULT '0' COMMENT '彩金池',
  `cumuJackpot` bigint(20) DEFAULT '0' COMMENT '累计彩金池',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6196952583179504646 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_info`
--

DROP TABLE IF EXISTS `t_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '登陆标识',
  `name` varchar(100) NOT NULL COMMENT '用户名',
  `accountId` varchar(100) DEFAULT NULL COMMENT '账号id',
  `facebookId` varchar(100) DEFAULT NULL COMMENT 'facebookid',
  `img` varchar(2000) DEFAULT '' COMMENT '图像url',
  `joinTime` bigint(20) DEFAULT NULL COMMENT '帐号创建时间',
  `lastLoginTime` bigint(20) DEFAULT NULL COMMENT '上次登陆时间',
  `role` int(11) NOT NULL DEFAULT '0' COMMENT '权限',
  `lockStatus` int(11) NOT NULL DEFAULT '0' COMMENT '锁定状态',
  `muteTime` int(11) NOT NULL DEFAULT '0' COMMENT '锁定时间',
  `muteReason` varchar(256) DEFAULT NULL COMMENT '锁定原因',
  `deviceMac` varchar(50) DEFAULT NULL COMMENT '设备MAC地址',
  `twitterId` varchar(100) DEFAULT '' COMMENT 'twitterId',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `deviceMac` (`deviceMac`) USING BTREE,
  KEY `accountId` (`accountId`) USING BTREE,
  KEY `facebookId` (`facebookId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-20 11:55:56
