#两条insert 语句 实在 texas_activity 数据库 中
insert into activity_condition(condition_id,title,description,type,create_time,activity_type_id,activity_type_name,id) 
						values(20,'消耗金额','','T','2017-05-04 13:17:37',12,'',36);
insert into activity_condition(condition_id,title,description,type,create_time,activity_type_id,activity_type_name,id) 
						values(21,'消耗金额','','T','2017-05-04 13:17:37',13,'',37);
						
#在 texas 数据库中						
alter table t_human_info add column friendId text COMMENT '好友的ID,用 ， 分割';