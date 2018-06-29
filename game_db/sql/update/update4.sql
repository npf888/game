set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';

USE `texas`;

alter table t_human_info add countries varchar(255) DEFAULT '' COMMENT '国籍';
alter table t_human_achievement add slotWinNum bigint(20) DEFAULT '0' COMMENT '玩家总胜利次数';