set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';


USE `texas`;

-- 新VIP系统
DROP TABLE IF EXISTS `t_human_vip_new`;
CREATE TABLE `t_human_vip_new` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `humanId` bigint(20) NOT NULL COMMENT '角色ID',
  `vipLevel` int(11) DEFAULT NULL COMMENT 'vip等级',
  `curPoint` int(11) DEFAULT NULL COMMENT '当前vip点',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING HASH
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 优惠礼包
DROP TABLE IF EXISTS `t_human_gift`;
CREATE TABLE `t_human_gift` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `charId` bigint(20) NOT NULL COMMENT '角色ID',
  `giftid` int(11) DEFAULT NULL COMMENT '优惠弹出礼包',
  `refreshTime` bigint(20) DEFAULT NULL COMMENT '优惠弹出礼包弹出世间点',
  `updateTime` bigint(20) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 新排行榜
DROP TABLE IF EXISTS `t_rank`;
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 德州彩金
DROP TABLE IF EXISTS `t_textas`;
CREATE TABLE `t_textas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `texasId` int(11) DEFAULT '0' COMMENT '德州房间ID',
  `jackpot` bigint(20) DEFAULT '0' COMMENT '彩金池',
  `cumuJackpot` bigint(20) DEFAULT '0' COMMENT '累计彩金池',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '更新时间',
  `createTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

alter table t_human_slot add totalJackpot bigint(20) NOT NULL DEFAULT '0' COMMENT '总获得彩金';
alter table t_slot add cumuJackpot bigint(20) NOT NULL DEFAULT '0' COMMENT '累计彩金池';