CREATE TABLE `t_boss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nameId` int(11) DEFAULT NULL COMMENT '多语言 名称的ID',
  `descrip` int(11) DEFAULT NULL,
  `bossId` bigint(20) DEFAULT NULL COMMENT 'bossId',
  `type` tinyint(1) DEFAULT NULL COMMENT '1.（airtime）秒内，所有wild连线伤害降低百分之（） 2.恢复自身最大血量的百分之（） 3.（airtime）秒内，收到伤害降低百分之（） 4.（airtime）秒内，有（）概率免疫伤害。',
  `airtime` int(11) DEFAULT NULL COMMENT '技能开始后持续时间（秒） ',
  `wildreduce` int(11) DEFAULT NULL COMMENT 'wild中奖连线伤害降低百分数。（除以100）',
  `recover1` int(11) DEFAULT NULL COMMENT '恢复最大血量百分数（除以100）',
  `damagereduce` int(11) DEFAULT NULL COMMENT '伤害减免百分数 ',
  `avoid` int(11) DEFAULT NULL COMMENT ' 免伤概率，百分数（除以100）',
  `blood` int(11) DEFAULT NULL COMMENT '基础血量',
  `rewardNum1` int(11) DEFAULT NULL COMMENT '击杀成功后：奖励金币总数，按照伤害占比瓜分奖励百分比',
  `rewardNum2` int(11) DEFAULT NULL COMMENT '未击杀成功：奖励金币总数，按照伤害占比瓜分奖励百分比 ',
  `changingBlood` bigint(20) DEFAULT NULL COMMENT '变化中的血',
  `increaseBlood` bigint(20) DEFAULT NULL COMMENT '增长的血（每次增长的血 的 ，全部是增长的）',
  `beKilled` tinyint(1) DEFAULT NULL COMMENT '是否 被击杀 1：被击杀- 0：没有被击杀',
  `attackRank` text COMMENT '伤害排行榜：依照当前玩家对boss造成的伤害值及百分比排行，显示前十名及自己的排名',
  `startTime` datetime DEFAULT NULL,
  `continueTime` bigint(20) DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `nextBossId` int(11) DEFAULT NULL COMMENT '下一个boss的ID ,在上一个结束时 生成',
  `lastBossId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `bossTemplateId` int(11) DEFAULT NULL COMMENT '这个boss 开的是哪个时间段的bossTemplate 的ID',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6305588552463451139 DEFAULT CHARSET=utf8;








CREATE TABLE `t_human_attack_boss` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户的ID',
  `bossId` bigint(20) DEFAULT NULL COMMENT 'boss的 ID',
  `attackBlood` bigint(20) DEFAULT NULL COMMENT '每次攻击 boss的血量',
  `attackTotalBlood` bigint(20) DEFAULT NULL COMMENT '总的攻击血量',
  `attackAllTotalBlood` bigint(20) DEFAULT NULL COMMENT '累计攻击boss 的钱',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




