CREATE TABLE `t_human_payguide` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户的ID',
  `treasury` tinyint(1) DEFAULT NULL COMMENT '小金猪 是否 提醒 1是  ，0 是否',
  `exp` tinyint(1) DEFAULT NULL COMMENT '经验加速卡   是否 提醒 1是  ，0 是否',
  `preference` tinyint(1) DEFAULT NULL COMMENT '特惠礼包   是否 提醒 1是  ，0 是否',
  `club` tinyint(1) DEFAULT NULL COMMENT '俱乐部   是否 提醒 1是  ，0 是否',
  `monthcard` tinyint(1) DEFAULT NULL COMMENT '月卡   是否 提醒 1是  ，0 是否',
  `coupon` tinyint(1) DEFAULT NULL COMMENT '优惠券',
  `time` date DEFAULT NULL COMMENT '本条数据创建时间，每天都会更新，一人就一条，每天更新时间，其他数据为0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

