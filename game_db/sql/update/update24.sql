#在 texas
alter table t_human_info add column couponExtraChip bigint(20) default 0 COMMENT "优惠券额外筹码百分比 获得筹码数 = 首充翻倍、VIP翻倍后的筹码数 X （1+ 优惠券额外筹码百分比/100 )";
alter table t_human_info add column couponDurationDate datetime  COMMENT "当前优惠券的结束日期（到这个日期就结束了，不能在使用了）";



alter table t_human_activity add column conditions text  COMMENT "条件，json字符串 （可以是任意条件）";

# 所有人共同操作的活动  例如全服累计充值送礼
CREATE TABLE `t_common_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ö÷¼ü',
  `activityId` bigint(20) DEFAULT '0' COMMENT '»î¶¯id',
  `activityDataPack` varchar(5000) DEFAULT NULL COMMENT '»î¶¯Êý¾Ý',
  `rewardActivityDataPack` varchar(5000) DEFAULT NULL COMMENT '½±ÀøÊý¾Ý',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '¸üÐÂÊ±¼ä',
  `createTime` bigint(20) DEFAULT '0' COMMENT '´´½¨Ê±¼ä',
  `conditions` varchar(500) DEFAULT NULL COMMENT '条件，json字符串 （可以是任意条件）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert into texas_activity.activity_condition(condition_id,title,description,type,create_time,activity_type_id,activity_type_name,id) 
	values(22,"美元","美元","T","2017-07-19 19:31:37","15","",38);
insert into texas_activity.activity_condition(condition_id,title,description,type,create_time,activity_type_id,activity_type_name,id) 
	values(23,"次数","次数","T","2017-07-19 19:31:37","18","",39);
	
insert into texas_activity.activity_condition(condition_id,title,description,type,create_time,activity_type_id,activity_type_name,id) 
	values(24,"连续天数","连续天数","T","2017-07-19 19:31:37","16","",40);
	
insert into texas_activity.activity_condition(condition_id,title,description,type,create_time,activity_type_id,activity_type_name,id) 
	values(25,"全服累计充值","全服累计充值","T","2017-07-19 19:31:37","17","",41);




