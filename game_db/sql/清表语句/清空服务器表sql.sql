-- 数据库版本信息表
delete from t_db_version;

-- 设备信息表
delete from t_device;

-- 角色信息表
delete from t_human_info;

-- 角色信缓存息表
delete from t_character_info_cache;

-- 物品表
delete from t_user_item;


-- 武将表
delete from t_pet_info;


-- 角色武将信息缓存表
delete from t_pet_cache_info;


-- 玩家酒馆信息
delete from t_user_petpub;


-- 珍宝阁信息
delete from t_jumbo_court;

-- doing任务信息表
delete from t_doing_task;


-- 已完成任务信息表
delete from t_finished_task;


-- daily任务信息表
delete from t_daily_task;


-- VIP任务信息表
delete from t_vip_task;


-- 玩家神器信息
delete from t_user_godequip;


-- 已完成副本信息表
delete from t_human_mission;


-- 副本对应的星星奖励表
delete from t_human_mission_star_reward;


-- 通用记录信息表
delete from t_common_record;



-- 玩家记录信息表:存放各种通用记录
delete from t_user_record;



-- 用户竞技场信息
delete from t_human_self_arena;


-- 用户竞技场战斗日志
delete from t_human_arena_log;


-- 用户竞技场排行奖励表
delete from t_human_arena_rankbonus;



-- 用户武将PVP信息
delete from t_human_self_petpvp;


-- 用户武将PVP排行奖励表
delete from t_human_petpvp_rankbonus;


-- 用户过关斩将信息
delete from t_human_cutpet;


-- 已完副本挂机的数据
delete from t_human_cutpet_hook;


-- 已完副本挂机的数据
delete from t_human_mission_hook;


-- 邮件表
delete from t_mail_info;

-- 好友关系表
delete from t_human_friend;


-- 玩家友情信息表
delete from t_user_friendpoint;


-- 玩家离线组队信息表
delete from t_user_offlineteam;


-- 玩家退出原因记录表
delete from t_user_exitreason;

 -- 商城
delete from t_shopmall_info;

-- vip系统表
delete from t_vip_info;

-- 活动信息
delete from t_active_info;

-- 排行
delete from t_rank;

-- 用户体力信息
delete from t_human_Vim;

-- GM补偿
delete from t_user_prize;

-- 全服补偿信息
delete from t_compensation;

-- 全服补偿列表
delete from t_compensation_user;

-- 平台礼包表
delete from t_prize;

-- 玩家武将附身表
delete from t_pet_possessed;


-- 聚宝盆数据
delete from t_treasurebowl;

-- 活跃度数据
delete from t_liveness;

-- 摇钱树信息
delete from t_money_tree;

-- 玩家挑战世界Boss表
delete from t_human_worldboss;

-- 每周登陆奖励
delete from t_human_weeklogin;

--  神秘商店
delete from t_user_mystery_mall;

-- 每周登陆奖励
delete from t_weeklogin_awards;

-- 武将图鉴
delete from t_user_pet_book;

-- 用户活动信息
delete from t_human_activity;

-- 角色其它信息表
delete from t_human_other_info;


-- 用户充值表
delete from t_user_charge_info;


-- 用户充值明细
delete from t_user_charge_detail;


-- 用户充值表
delete from t_user_charge_info;


-- 用户消费元宝明细
delete from t_user_consume_detail;


-- 天书信息
delete from t_human_god_book;

-- 无尽副本
delete from t_human_endLess_mission;

-- 玩家返还信息
delete from t_user_charge_refund;

-- 招募积分
delete from t_human_petpub_score;

-- 武将招募活动配置
delete from t_petpub_configure;


不清空的表
-- 定时公告
-- delete from t_time_notice;

-- 活动信息
-- delete from t_activity;

-- 功能开关表
-- delete from t_offon_control;

-- 兑换码信息
-- delete from t_promocode;