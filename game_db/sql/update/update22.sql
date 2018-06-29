CREATE TABLE `t_human_treasury` (
  `id` bigint(20) NOT NULL,
  `chartId` bigint(20) NOT NULL,
  `type` int(1) DEFAULT NULL COMMENT '共6种类型：0，1，2，3，4，5 -每种的初始金币不同',
  `gold` bigint(20) DEFAULT NULL COMMENT '此时此刻的金币',
  `factorTreasury` int(11) DEFAULT NULL COMMENT '金币存储系数（万分比）',
  `vipPointTreasury` int(11) DEFAULT NULL COMMENT 'VIP点数奖励',
  `maxTreasury` bigint(20) DEFAULT NULL COMMENT '存储上限',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

