set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';


USE `texas`;


-- slot
DROP TABLE IF EXISTS `t_slot`;
CREATE TABLE `t_slot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `slotType` int(11) NOT NULL DEFAULT 0 COMMENT '老虎机类型',
  `jackpot` bigint(20) NOT NULL DEFAULT 0 COMMENT '彩金',
  `stock` bigint(20) NOT NULL DEFAULT 0 COMMENT '库存',
  `spinTimes` bigint(20) NOT NULL DEFAULT 0 COMMENT '次数',
  `updateTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 人物老虎机信息
DROP TABLE IF EXISTS `t_human_slot`;
CREATE TABLE `t_human_slot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `slotId` bigint(20) NOT NULL COMMENT '老虎机id',
  `totalBet` bigint(20) NOT NULL COMMENT '总共下注',
  `totalRefund` bigint(20) NOT NULL COMMENT '总共返还',
  `updateTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;