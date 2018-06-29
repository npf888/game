CREATE TABLE `t_human_bazoo_signin` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `passportId` bigint(20) DEFAULT NULL,
  `signInTime` datetime DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_human_bazoo_rank` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `passportId` bigint(20) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `headImg` varchar(256) DEFAULT NULL,
  `dayProfit` bigint(20) DEFAULT NULL COMMENT '每天的盈利',
  `weekProfit` bigint(20) DEFAULT NULL COMMENT '每周的盈利',
  `monthProfit` bigint(20) DEFAULT NULL COMMENT '每月的盈利',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_human_bazoo_personal` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `passportId` bigint(20) DEFAULT NULL,
  `modeType` tinyint(1) DEFAULT NULL,
  `numberOfGame` int(11) DEFAULT NULL COMMENT '局数',
  `singleTopGold` bigint(20) DEFAULT NULL COMMENT '单局最高',
  `rateOfWinning` int(11) DEFAULT NULL COMMENT '胜率',
  `aWinningStreak` int(11) DEFAULT NULL COMMENT '连胜',
  `passToKill` int(11) DEFAULT NULL COMMENT '通杀',
  `bigPatterns` varchar(64) DEFAULT NULL COMMENT '最大牌型',
  `pantherNumber` int(11) DEFAULT NULL COMMENT '豹子数',
  `dayProfit` bigint(20) DEFAULT NULL,
  `weekProfit` bigint(20) DEFAULT NULL,
  `monthProfit` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_human_bazoo_personal add column winTimes int(11);

CREATE TABLE `t_human_bazoo_task` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `passportId` bigint(20) DEFAULT NULL,
  `task` text COMMENT '所有任务  json字符串',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_human_bazoo_achieve` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `passportId` bigint(20) DEFAULT NULL,
  `achieve` text COMMENT '成就的 json字符串',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_human_bazoo_wins` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `passportId` bigint(20) DEFAULT NULL,
  `modeType` tinyint(1) DEFAULT NULL COMMENT '模式 类型',
  `winTimes` int(11) DEFAULT NULL COMMENT '连胜 次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





