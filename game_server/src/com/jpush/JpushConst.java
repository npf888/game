package com.jpush;

public class JpushConst {
	//邮件
	public static final int NOTIFY_TYPE_MAIL = 1;
	//公告
	public static final int NOTIFY_TYPE_GONG_GAO = 2;
	
	
	//所有人
	public static final int Condition_type_all = 0;
	//在线奖励可领取
	public static final int Condition_type_online_reward = 1;
	//玩家未签到
	public static final int Condition_type_need_sign = 2;
	//收到新的好友申请
	public static final int Condition_type_friend_request = 3;
	//登录天数
	public static final int Condition_type_logon_days = 4;
	//未登录天数
	public static final int Condition_type_not_logon_days = 5;
	//充值次数
	public static final int Condition_type_recharge_times = 6;
	//点开计费点未充值的次数
	public static final int Condition_type_open_not_recharge_times = 7;
	//金币存储量百分比达到
	public static final int Condition_type_gold_percent = 9;
	//所在工会有人购买了俱乐部礼包
	public static final int Condition_type_club_gift = 10;
	//有可领奖邮件
	public static final int Condition_type_mail_has_reward =11;
	//等级
	public static final int Condition_type_level = 12;
	//版本号
	public static final int Condition_type_version = 13;
	//24小时更新过版本
	public static final int Condition_type_update_in_24hours = 14;
}
