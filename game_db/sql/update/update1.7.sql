set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';


USE `texas`;

alter table t_human_recharge_order add channelOrderId varchar(100) DEFAULT 0;
alter table t_human_friend add facebook int(11) DEFAULT 0