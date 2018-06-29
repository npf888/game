set names 'utf8';
set character_set_database = 'utf8';
set character_set_server = 'utf8';

USE `texas`;

alter table t_human_task add taskProgress varchar(255) DEFAULT '' COMMENT '任务进度';