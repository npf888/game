#在 texas 数据库中						
alter table t_human_friend add column agree tinyint default 1 COMMENT "用户 发出的申请好友同意之后，在每次登录的时候都会判断下是否有好友同意了 他的申请，如果有就把该状态改为1，否则是0";

#在 texas 数据库中
alter table t_user_info add column deviceId varchar(50);
alter table t_user_info add column macAddress varchar(50);
alter table t_user_info add column androidId varchar(50);