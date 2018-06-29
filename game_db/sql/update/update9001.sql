CREATE TABLE `t_human_givealike` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `userId` bigint(20) DEFAULT NULL,
  `slotType` int(11) DEFAULT NULL COMMENT '哪种类型的老虎机',
  `slotBet` int(11) DEFAULT NULL COMMENT '老虎机 的哪个场，每个老虎机都有四个场 ',
  `content` varchar(255) DEFAULT NULL COMMENT '评价的内容',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table t_slot add column jackpot1 bigint(20) default 0;
alter table t_slot add column jackpot2 bigint(20) default 0;
alter table t_slot add column jackpot3 bigint(20) default 0;
alter table t_slot add column jackpot4 bigint(20) default 0;
alter table t_slot add column jackpot5 bigint(20) default 0;

alter table t_slot add column cumuJackpot1 bigint(20) default 0;
alter table t_slot add column cumuJackpot2 bigint(20) default 0;
alter table t_slot add column cumuJackpot3 bigint(20) default 0;
alter table t_slot add column cumuJackpot4 bigint(20) default 0;
alter table t_slot add column cumuJackpot5 bigint(20) default 0;