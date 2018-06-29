set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';

USE `texas`;

alter table t_human_info add age int(11) DEFAULT '0' COMMENT '年龄';
alter table t_human_info add slotRotate bigint(20) DEFAULT '0' COMMENT '总转次数';
alter table t_human_info add slotWin bigint(20) DEFAULT '0' COMMENT '总赢得分';
alter table t_human_info add slotSingleWin bigint(20) DEFAULT '0' COMMENT '单次赢取最大';
alter table t_human_info add slotWinNum bigint(20) DEFAULT '0' COMMENT '玩家总胜利次数';