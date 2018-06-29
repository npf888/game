CREATE TABLE `t_human_new_comer` (
  `id` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `startTime` datetime DEFAULT NULL COMMENT '每天第一次登录时间（这个不会变）',
  `perStartTime` datetime DEFAULT NULL COMMENT '每天的 第一次登录时间 （这个会变，根据剩余时间）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



alter table t_human_info add column watchTime datetime  COMMENT "观看视频的时间";