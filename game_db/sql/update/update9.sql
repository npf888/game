alter table t_activity add column hall_pic varchar(100) comment '大厅游戏' after activityDesc_tw;
alter table t_activity add column hall_pic_cn varchar(100) comment '大厅图片,中文简体' after activityDesc_tw;
alter table t_activity add column hall_pic_tw varchar(100) comment '大厅图片,中文繁体' after activityDesc_tw;
alter table t_robot_statis_data add column bonusTriggerFreeCount bigint(20) comment 'bonus免费触发的次数' after bonusTriggerCount;