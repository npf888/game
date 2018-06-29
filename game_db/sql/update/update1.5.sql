set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';


USE `texas`;


-- 百家乐
DROP TABLE IF EXISTS `t_human_baccart`;
CREATE TABLE `t_human_baccart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `bankerNum` bigint(20) NOT NULL COMMENT '上庄数',
  `gameNum` bigint(20) NOT NULL COMMENT '游戏数',
  `winNum` bigint(20) NOT NULL COMMENT '赢次数',
  `beaconNum` bigint(20) NOT NULL COMMENT '明灯',
  `isAuto` int(11) NOT NULL COMMENT '自动',
  `lostNum` bigint(20) NOT NULL COMMENT '输次数',
  `updateTime` bigint(20) NOT NULL COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 百家乐房间
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
