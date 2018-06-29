set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';

USE `texas`;

alter table t_human_info add isPay bigint(20) NOT NULL DEFAULT '0' COMMENT '首充';
alter table t_human_misc add fbThumb int(11) NOT NULL DEFAULT '0' COMMENT '点赞 1已经领取过奖了';