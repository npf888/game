alter table t_human_info add column doubleExpEndTime datetime comment '双倍经验加成结束时间';


CREATE TABLE `t_human_month_week` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `userId` bigint(20) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL COMMENT '周月特惠礼包开启时间',
  `continueTime` bigint(20) DEFAULT NULL COMMENT '持续时间',
  `mwType` tinyint(4) DEFAULT NULL COMMENT '0表示周礼包，1表示月礼包',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table t_human_month_week add column isBuy tinyint(1) DEFAULT 0 COMMENT '当周 或者 当月 是否购买（一周 或者一个月只限购一次）0:没有购买，1：购买';
