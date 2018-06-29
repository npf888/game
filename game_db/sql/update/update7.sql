CREATE TABLE `t_robot_statis_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `slotName` varchar(64) DEFAULT NULL COMMENT '老虎机名称',
  `slotId` bigint(20) DEFAULT NULL,
  `slotType` bigint(20) DEFAULT NULL COMMENT '老虎机类型',
  `chartId` bigint(20) DEFAULT NULL,
  `rewardCount` bigint(20) DEFAULT NULL COMMENT '赢取次数：在转动中赢得筹码的次数',
  `rewardUsed` bigint(20) DEFAULT NULL COMMENT '消耗筹码数：所有转动结束后所消耗的筹码',
  `payCount` bigint(20) DEFAULT NULL COMMENT '支付线筹码数：支付线中奖获得的筹码数量',
  `scatterNum` bigint(20) DEFAULT NULL COMMENT 'Scatter筹码数：中scatter游戏获得的筹码数量；',
  `bonusNum` bigint(20) DEFAULT NULL COMMENT 'Bonus筹码数：中bonus游戏获得的筹码数量；',
  `scatterTriggerCount` bigint(20) DEFAULT NULL COMMENT '付费 --- scatter的次数：scatter游戏触发的次数；',
  `scatterTriggerFreeCount` bigint(20) DEFAULT NULL COMMENT '免费  -- scatter的次数：scatter游戏触发的次数；',
  `bonusTriggerCount` bigint(20) DEFAULT NULL COMMENT 'bonus的次数：bonus游戏触发的次数',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;







CREATE TABLE `t_human_task_template` (
  `id` bigint(20) NOT NULL,
  `conditions` bigint(20) DEFAULT NULL COMMENT '条件(判断是否完成的条件，大于等于此条件）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

