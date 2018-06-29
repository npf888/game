set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';

USE `texas`;

alter table t_human_info add slotRoomId varchar(255) DEFAULT '' COMMENT '老虎机房间ID';
alter table t_human_info add slotId varchar(255) DEFAULT '' COMMENT '老虎机ID';