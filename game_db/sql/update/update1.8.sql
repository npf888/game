set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';


USE `texas`;


-- 幸运转盘
DROP TABLE IF EXISTS `t_human_lucky_spin`;
CREATE TABLE `t_human_lucky_spin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `lastSpinTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '上次免费时间',
  `freeTimes` int(11) NOT NULL DEFAULT 0 COMMENT '次数',
  `times` int(11) NOT NULL DEFAULT 0 COMMENT '次数',
  `poolPack` varchar(100) DEFAULT NULL COMMENT '次数',
  `updateTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 幸运匹配
DROP TABLE IF EXISTS `t_human_lucky_match`;
CREATE TABLE `t_human_lucky_match` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `charId` bigint(20) NOT NULL COMMENT 'charId',
  `lastMatchTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '上次免费时间',
   `poolPack` varchar(100) DEFAULT NULL COMMENT '次数',
    `freeTimes` int(11) NOT NULL DEFAULT 0 COMMENT '次数',
  `times` int(11) NOT NULL DEFAULT 0 COMMENT '次数',
  `updateTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '更新时间',
  `createTime` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;