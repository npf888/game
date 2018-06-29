package com.common.constants;

import com.core.annotation.SysI18nString;

/**
 * 语言相关的常量定义
 * 
 * @author Thinker
 * 
 */
public class LangConstants {

	@SysI18nString(content = "")
	public static final Integer NULL = 0;

	/** 公用常量 1 ~ 1000 */
	public static Integer COMMON_BASE = 0;
	@SysI18nString(content = "输入字符为NULL")
	public static final Integer GAME_INPUT_NULL = ++COMMON_BASE;
	@SysI18nString(content = "字符串小于最小长度")
	public static final Integer GAME_INPUT_TOO_SHORT = ++COMMON_BASE;
	@SysI18nString(content = "字符串长度大于最大允许长度")
	public static final Integer GAME_INPUT_TOO_LONG = ++COMMON_BASE;
	@SysI18nString(content = "字符有异常字符")
	public static final Integer GAME_INPUT_ERROR1 = ++COMMON_BASE;
	@SysI18nString(content = "字符有非法关键字")
	public static final Integer GAME_INPUT_ERROR2 = ++COMMON_BASE;
	@SysI18nString(content = "检查非法字符")
	public static final Integer GAME_INPUT_ERROR3 = ++COMMON_BASE;
	@SysI18nString(content = "世界聊天太频繁")
	public static final Integer CHAT_TOO_OFTEN = ++COMMON_BASE;
	@SysI18nString(content = "加载角色列表错误")
	public static final Integer LOAD_PLAYER_ROLES = ++COMMON_BASE;
	@SysI18nString(content = "加载角色信息错误")
	public static final Integer LOAD_PLAYER_SELECTED_ROLE = ++COMMON_BASE;
	@SysI18nString(content = "您的账号存在异常")
	public static final Integer GM_KICK = ++COMMON_BASE;
	@SysI18nString(content = "您的钻石不足,请充值后再进行此操作!")
	public static final Integer DIAMOND_NOT_ENOUGH = ++COMMON_BASE;
	@SysI18nString(content = "您的筹码不足")
	public static final Integer GOLD_NOT_ENOUGH = ++COMMON_BASE;
	@SysI18nString(content = "您的等级不够")
	public static final Integer ROLE_LEVEL_NO = ++COMMON_BASE;

	
	
	/** 道具、背包相关的常量2000 ~ 2999 */
	public static Integer ITEM_BASE = 20000;
	@SysI18nString(content = "道具不足")
	public static final Integer NO_ITEM = 20001;
	@SysI18nString(content = "喇叭不足")
	public static final Integer NO_ITEM_SPEAKER = 20002;
	
	
	/** 玩家登录退出切换场景相关常量 3000 ~ 3999 */
	public static Integer PLAYER_BASE = 3000;
	@SysI18nString(content = "未知错误")
	public static final Integer LOGIN_UNKOWN_ERROR = ++PLAYER_BASE;
	@SysI18nString(content = "您的账号已在其他设备上登录，请点击“确定”重新登录游戏")
	public static final Integer LOGIN_ONLINE_ERROR = ++PLAYER_BASE;

	@SysI18nString(content = "您的账号已经锁定，暂时无法登录.")
	public static final Integer LOGIN_ERROR_ACCOUNT_LOCKED = ++PLAYER_BASE;
	@SysI18nString(content = "您的账号状态异常，暂时无法登录")
	public static final Integer LOGIN_ERROR_ACCOUNT_STATE = ++PLAYER_BASE;
	@SysI18nString(content = "当前服务器繁忙，请稍后再试")
	public static final Integer LOGIN_ERROR_SERVER_FULL = ++PLAYER_BASE;

	// 快客相关
	@SysI18nString(content = "登录操作过于频繁，请 1 分钟之后再登录")
	public static final Integer PLAYER_MEET_LOGIN_INTERVAL = ++PLAYER_BASE;
	
	/** Local平台的描述 4000 ~ 4999 */
	private static Integer LOCAL_BASE = 4000;
	@SysI18nString(content = "登录失败，如果您已经修改登录账号，请使用新账号登陆")
	public static final Integer LOCAL_LOGIN_NAME_ALREADY_CHANGED = ++LOCAL_BASE;
	@SysI18nString(content = "登录失败，保存用户信息失败")
	public static final Integer LOCAL_SAVE_USER_INFO_TO_DB_ERROR = ++LOCAL_BASE;


	/** 商城相关的常量5000 ~ 5999 */
	public static Integer MALL_BASE = 5000;

	/** 邮件相关 6000-6999 */
	public static Integer MAIL_BASE = 6000;
	@SysI18nString(content = "系统发送")
	public static final Integer MAIL_SYSTEM_NAME = ++MAIL_BASE;
	@SysI18nString(content = "该邮件不存在")
	public static final Integer MAIL_ISNOTEXIST = ++MAIL_BASE;
	@SysI18nString(content = "该邮件不存在奖励")
	public static final Integer MAIL_REWARD_ISNOTEXIST = ++MAIL_BASE;
	@SysI18nString(content = "您的邮件内有物品不能删除")
	public static final Integer MAIL_DELETE_ERR = ++MAIL_BASE;

	@SysI18nString(content = "玩家邮件")
	public static final Integer HUMAN_MAIL_TILTE = ++MAIL_BASE;
	@SysI18nString(content = "邮件类型错误")
	public static final Integer MAIL_KIDN_ERROR = ++MAIL_BASE;
	@SysI18nString(content = "邮件发送玩家不存在")
	public static final Integer MAIL_RECEIVER_NO_EXIST = ++MAIL_BASE;
	
	@SysI18nString(content = "积分榜奖励")
	public static final Integer ranknewTitle = ++MAIL_BASE;
	@SysI18nString(content = "恭喜你在积分榜中获得第{0}名，赶快领取奖励")
	public static final Integer ranknewcontent = ++MAIL_BASE;
	
	
	@SysI18nString(content = "恭喜你在昨天總得分排行榜中獲得第{0}名，趕快領取獎勵吧！")
	public static final Integer ranknewTotalcontent = ++MAIL_BASE;
	
	@SysI18nString(content = "月卡奖励")
	public static final Integer cardTitle = ++MAIL_BASE;
	@SysI18nString(content = "尊敬的月卡用户，恭喜获得月卡特权奖励的{0}筹码，您的月卡还有{1}天到期")
	public static final Integer cardcontent = ++MAIL_BASE;

	
	
	
	@SysI18nString(content = "禮物")
	public static final Integer GiftTitle = ++MAIL_BASE;
	@SysI18nString(content = "送您一份禮物")
	public static final Integer Giftcontent = ++MAIL_BASE;
	
	@SysI18nString(content = "段位奖励")
	public static final Integer ranknewTitle1 = ++MAIL_BASE;
	@SysI18nString(content = "恭喜您达到{0}段位获得段位奖励，请领取奖励！")
	public static final Integer ranknewcontent2 = ++MAIL_BASE;

	
	@SysI18nString(content = "周卡奖励")
	public static final Integer weekcardTitle = ++MAIL_BASE;
	@SysI18nString(content = "尊敬的周卡用户，恭喜获得周卡特权奖励的{0}筹码，您的月卡还有{1}天到期")
	public static final Integer weekcardcontent = ++MAIL_BASE;
	
	@SysI18nString(content = "买一赠一奖励")
	public static final Integer sendOneToOne = ++MAIL_BASE;
	@SysI18nString(content = "买一赠一奖励")
	public static final Integer sendOneToOneContent = ++MAIL_BASE;

	
	/** 活动相关7000-7999 */
	public static Integer ACTIVE_BASE = 7000;
	@SysI18nString(content = "活动已经不存在")
	public static final Integer ACTIVE_NO_EXIST = ++ACTIVE_BASE;


	/** 货币相关 8000-8999 */
	public static Integer CURRENCY_BASE = 8000;
	@SysI18nString(content = "钻石")
	public static final Integer CURRENCY_NAME_DIAMOND = ++CURRENCY_BASE;
	@SysI18nString(content = "筹码")
	public static final Integer CURRENCY_NAME_GOLD = ++CURRENCY_BASE;
	@SysI18nString(content = "点券")
	public static final Integer CURRENCY_NAME_COUPON = ++CURRENCY_BASE;
	@SysI18nString(content = "经验")
	public static final Integer CURRENCY_NAME_EXP = ++CURRENCY_BASE;
	@SysI18nString(content = "魅力值")
	public static final Integer CURRENCY_NAME_CHARM = ++CURRENCY_BASE;
	

	
	/** 德州相关 9000-9999 */
	public static Integer TEXAS_BASE = 9000;
	@SysI18nString(content = "小于最小携带量")
	public static final Integer TEXAS_LESS_MIN_CARRY = ++TEXAS_BASE;	
	@SysI18nString(content = "房间已满")
	public static final Integer TEXAS_ROOM_FULL  = ++TEXAS_BASE;
	@SysI18nString(content = "sng房间已满")
	public static final Integer SNG_TEXAS_ROOM_FULL  = ++TEXAS_BASE;
	
	
	
	/**商品相关10000-10999*/
	public static Integer SHOP_BASE = 10000;
	@SysI18nString(content = "筹码不足")
	public static final Integer NO_ENOUGH_MONEY = ++SHOP_BASE;
	@SysI18nString(content = "购买失败")
	public static final Integer COST_MONEY_FAILED = ++SHOP_BASE;
	@SysI18nString(content = "超过背包容量")
	public static final Integer EXCEED_BAG_CAPACITY = ++SHOP_BASE;
	
	/**月卡周卡相关11000-11999*/
	public static Integer CARD_BASE = 11000;
	@SysI18nString(content = "月卡过期")
	public static final Integer MONTH_CARD_EXPIRED = ++CARD_BASE;
	@SysI18nString(content = "周卡过期")
	public static final Integer WEEK_CARD_EXPIRED = ++CARD_BASE;
	@SysI18nString(content = "月卡今天领取过了")
	public static final Integer MONTH_CARD_ALREADY_GET = ++CARD_BASE;
	@SysI18nString(content = "周卡今天领取过了")
	public static final Integer WEEK_CARD_ALREADY_GET = ++CARD_BASE;
	
	/**排行版相关12000-12999*/
	public static Integer RANK_BASE = 12000;
	@SysI18nString(content = "排行版类型错误")
	public static final Integer RANK_TYPE_ERROR = ++RANK_BASE;
	
	
	/** 好友相关 13000-13999 */
	public static Integer FRIEND_BASE = 13000;
	@SysI18nString(content = "您的好友以达到上限，不能添加")
	public static final Integer FRIEND_ISMAX = ++FRIEND_BASE;
	@SysI18nString(content = "对方的好友以达到上限，不能添加")
	public static final Integer OFRIEND_ISMAX = ++FRIEND_BASE;
	@SysI18nString(content = "对方以是您的好友了")
	public static final Integer FRIEND_ISEXIST = ++FRIEND_BASE;
	@SysI18nString(content = "对方不是您的好友")
	public static final Integer FRIEND_ISNOTEXIST = ++FRIEND_BASE;
	@SysI18nString(content = "同意好友申请")
	public static final Integer FRIEND_APPROVE = ++FRIEND_BASE;
	@SysI18nString(content = "拒绝好友申请")
	public static final Integer FRIEND_REJECT = ++FRIEND_BASE;
	@SysI18nString(content = "不能添加自己")
	public static final Integer FRIEND_REQUEST_NOT_SELF = ++FRIEND_BASE;
	@SysI18nString(content = "好友请求已发出")
	public static final Integer FRIEND_REQUEST_SEND = ++FRIEND_BASE;
	@SysI18nString(content = "好友请求不存在")
	public static final Integer FRIEND_REQUEST_NOT_EXIST = ++FRIEND_BASE;
	@SysI18nString(content = "好友不存在")
	public static final Integer FRIEND_NOT_EXIST = ++FRIEND_BASE;
	@SysI18nString(content = "礼物不存在")
	public static final Integer FRIEND_GIFT_NOT_EXIST = ++FRIEND_BASE;
	@SysI18nString(content = "礼物已经领取过")
	public static final Integer FRIEND_GIFT_ALREADY_GET = ++FRIEND_BASE;
	@SysI18nString(content = "不能给自己发礼物")
	public static final Integer FRIEND_GIFT_NOT_SELF = ++FRIEND_BASE;
	@SysI18nString(content = "已经发送了好友礼物")
	public static final Integer FRIEND_GIFT_ALREADY_SEND = ++FRIEND_BASE;
	@SysI18nString(content = "礼物发送成功")
	public static final Integer GIFTS_TO_SEND_SUCCESS = ++FRIEND_BASE;
	@SysI18nString(content = "你已经邀请过了该玩家")
	public static final Integer SLOT_ROOM_REQ1 = ++FRIEND_BASE;
	@SysI18nString(content = "发送成功")
	public static final Integer SLOT_ROOM_REQ2 = ++FRIEND_BASE;
	@SysI18nString(content = "该老虎机房间已满")
	public static final Integer SLOT_ROOM_REQ3 = ++FRIEND_BASE;
	
	
	
	/** 用户相关 14000-14999 */
	public static Integer USER_BASE = 14000;
	@SysI18nString(content = "用户不存在")
	public static final Integer USER_NO_EXIST = ++USER_BASE;
	@SysI18nString(content = "名称修改以成功")
	public static final Integer CHANGE_NAME_SUCCESS = ++USER_BASE;
	
	@SysI18nString(content = "领取成功")
	public static final Integer Conversion1 = ++USER_BASE;
	@SysI18nString(content = "兑换码已经过期或者作废")
	public static final Integer Conversion2 = ++USER_BASE;
	@SysI18nString(content = "已经领取过了")
	public static final Integer Conversion3 = ++USER_BASE;
	@SysI18nString(content = "兑换码不存在")
	public static final Integer Conversion4 = ++USER_BASE;
	@SysI18nString(content = "用户重复")
	public static final Integer USER_DOUBLE = ++USER_BASE;
	
	
	
	/** 签到相关 15000-15999 */
	public static Integer SIGN_IN_BASE = 15000;
	@SysI18nString(content = "已经签到过")
	public static final Integer ALREADY_SIGN_IN = ++SIGN_IN_BASE;
	
	/** MISC相关 16000-16999 */
	public static Integer MISC_BASE = 16000;
	@SysI18nString(content = "已经领完所有在线奖励")
	public static final Integer ONLINE_REWAR_ALREADY_TAKE = ++MISC_BASE;
	@SysI18nString(content = "时间不够")
	public static final Integer TIME_NO_ENOUGH = ++MISC_BASE;
	
	
	/** vip相关 17000-17999 */
	public static Integer VIP_BASE = 17000;
	@SysI18nString(content = "不存在的vip")
	public static final Integer VIP_NO_EXIST = ++VIP_BASE;
	@SysI18nString(content = "购买小于当前vip")
	public static final Integer VIP_BUY_LESS = ++VIP_BASE;
	@SysI18nString(content = "玩家不是vip")
	public static final Integer PLAYER_NOT_VIP = ++VIP_BASE;
	@SysI18nString(content = "玩家已经领取vip")
	public static final Integer PLAYER_ALREADY_GET_VIP_REWARD = ++VIP_BASE;
	

	/** 充值相关 18000-18999 */
	public static Integer RECHARGE_BASE = 18000;
	@SysI18nString(content = "不存在的订单")
	public static final Integer ORDER_NO_EXIST = ++RECHARGE_BASE;
	@SysI18nString(content = "订单已处理了")
	public static final Integer ORDER_ALEADY_HANDLE = ++RECHARGE_BASE;
	@SysI18nString(content = "产品不存在")
	public static final Integer PRODUCT_NO_EXIST = ++RECHARGE_BASE;
	
	

	/** 俱乐部相关 19000-19999 */
	public static Integer CLUB_BASE = 19000;
	@SysI18nString(content = "没有加入俱乐部")
	public static final Integer CLUB_NO_CLUB = 19001;
	@SysI18nString(content = "创建俱乐部名字非法")
	public static final Integer CLUB_NAME_ERROR = 19002;
	@SysI18nString(content = "创建俱乐部名字太长")
	public static final Integer CLUB_NAME_TOO_LONG = 19003;
	@SysI18nString(content = "创建俱乐部描述非法")
	public static final Integer CLUB_DESC_ERROR = 19004;
	@SysI18nString(content = "俱乐部公告非法")
	public static final Integer CLUB_NOTICE_ERROR = 19005;
	@SysI18nString(content = "创建俱乐部类型错误")
	public static final Integer CLUB_TYPE_ERROR = 19006;
	@SysI18nString(content = "创建俱乐部消耗不足")
	public static final Integer CLUB_CLUB_COST_NOT_ENOUGH = 19007;
	@SysI18nString(content = "俱乐部捐献不符合规定")
	public static final Integer CLUB_DONATE_COST_NOT_ALLOWED = 19008;
	@SysI18nString(content = "俱乐部签到刷新时间未到")
	public static final Integer CLUB_SIGN_EARLY = 19009;
	@SysI18nString(content = "俱乐部捐献刷新时间未到")
	public static final Integer CLUB_DONATE_EARLY = 19010;
	@SysI18nString(content = "俱乐部搜索名字不合法")
	public static final Integer CLUB_SEARCH_NAME_ERROR = 19011;
	@SysI18nString(content = "俱乐部留言不合法")
	public static final Integer CLUB_BOARD_NOTE_ERROR = 19012;
	@SysI18nString(content = "俱乐部留言过长")
	public static final Integer CLUB_BOARD_TOO_LONG = 19013;
	@SysI18nString(content = "俱乐部申请次数过多")
	public static final Integer CLUB_APPLY_TOO_MANY = 19014;
	@SysI18nString(content = "已在俱乐部")
	public static final Integer CLUB_ALREADY_IN = 19015;
	@SysI18nString(content = "俱乐部不存在")
	public static final Integer CLUB_NOT_EXIST = 19016;
	@SysI18nString(content = "俱乐部操作权限不足")
	public static final Integer CLUB_OPE_NO_RIGHT = 19017;
	@SysI18nString(content = "俱乐部不允许加入")
	public static final Integer CLUB_NONE_JOIN = 19018;
	@SysI18nString(content = "已申请过俱乐部")
	public static final Integer CLUB_APPLY_ALREADY = 19019;
	@SysI18nString(content = "俱乐部已满")
	public static final Integer CLUB_FULL = 19020;
	@SysI18nString(content = "俱乐部段位未达到")
	public static final Integer CLUB_LIMIT_NOT_MATCH = 19021;
	@SysI18nString(content = "俱乐部弹劾不合法")
	public static final Integer CLUB_TANHE_INVALID = 19022;
	@SysI18nString(content = "俱乐部已有人发起弹劾")
	public static final Integer CLUB_TANHE_ALREADY = 19023;
	@SysI18nString(content = "俱乐部不可弹劾投票")
	public static final Integer CLUB_TANHE_CANNOT_VOTE = 19024;
	@SysI18nString(content = "俱乐部已投票")
	public static final Integer CLUB_TANHE_VOTE_ALREADY = 19025;
	@SysI18nString(content = "俱乐部弹劾已结束")
	public static final Integer CLUB_TANHE_EXPIRE = 19026;
	@SysI18nString(content = "俱乐部礼物过期")
	public static final Integer CLUB_GIFT_EXPIRED = 19027;
	@SysI18nString(content = "俱乐部礼物已领过")
	public static final Integer CLUB_GIFTED = 19028;
	@SysI18nString(content = "不可直接踢副会长")
	public static final Integer CLUB_CANNOT_KICK_FU_HUI_ZHANG = 19029;
	@SysI18nString(content = "俱乐部成员不存在")
	public static final Integer CLUB_MEMBER_NOT_EXISTED = 19030;
	@SysI18nString(content = "俱乐部副主席太多")
	public static final Integer CLUB_FU_ZHU_XI_TOO_MANY = 19031;
	@SysI18nString(content = "已经邀请过")
	public static final Integer CLUB_INVITE_ALREADY = 19032;
	@SysI18nString(content = "俱乐部赛季活跃榜奖励")
	public static final Integer CLUB_SEASON_HUO_YUE_REWARD = 19033;
	@SysI18nString(content = "俱乐部赛季活跃榜奖励内容")
	public static final Integer CLUB_SEASON_HUO_YUE_REWARD_CONTENT = 19034;
	@SysI18nString(content = "俱乐部赛季贡献榜奖励")
	public static final Integer CLUB_SEASON_GONG_XIAN_REWARD = 19035;
	@SysI18nString(content = "俱乐部赛季贡献榜奖励内容")
	public static final Integer CLUB_SEASON_GONG_XIAN_REWARD_CONTENT = 19036;
	@SysI18nString(content = "俱乐部道具不足")
	public static final Integer CLUB_NO_ITEMS = 19037;
	@SysI18nString(content = "俱乐部每日申请次数过多")
	public static final Integer CLUB_DAILY_APPLYING_TIMES_LIMIT = 19038;
	@SysI18nString(content = "俱乐部被申请次数过多")
	public static final Integer CLUB_APPLIED_TIMES_LIMIT = 19039;
	@SysI18nString(content = "俱乐部邀请title")
	public static final Integer CLUB_INVITE_MAIL_TITLE = 19040;
	@SysI18nString(content = "俱乐部邀请content")
	public static final Integer CLUB_INVITE_MAIL_CONTENT = 19041;
	@SysI18nString(content = "俱乐部拒绝title")
	public static final Integer CLUB_REFUSE_MAIL_TITLE = 19042;
	@SysI18nString(content = "俱乐部拒绝content")
	public static final Integer CLUB_REFUSE_MAIL_CONTENT = 19043;
	
	/** 广播 9990001-9990001 */
	public static Integer NOTICE_BASE = 9990000;
	@SysI18nString(content = "{0}在获得{1}彩金")
	public static final Integer BACCART_JACKPOT_BROADCAST = ++NOTICE_BASE;
	@SysI18nString(content = "游戏类型 {0} 玩家名称 {1} 玩家头像 {2}玩家获得彩金数量{3}")
	public static final Integer JACKPOT_BROADCAST = ++NOTICE_BASE;
	
	@SysI18nString(content = "世界boss发送奖励")
	public static final Integer WORLD_BOSS_REWARD = ++CLUB_BASE;
	@SysI18nString(content = "恭喜你在世界boss中获得第{0}名，赶快领取奖励")
	public static final Integer WORLD_BOSS_REWARD_CONTENT = ++MAIL_BASE;
	
	@SysI18nString(content = "世界boss游戏开始 敬请关注")
	public static final Integer WORLD_BOSS_BROADCAST = ++NOTICE_BASE;
	
	/** 无双吹牛 99999001-  */
	public static Integer DICE_BASE = 99999000;
	@SysI18nString(content = "您当前正处于{0}房间之中")
	public static final Integer DICE_USER_NOT_OUT_ROOM = ++DICE_BASE;
	@SysI18nString(content = "金币不足{0}")
	public static final Integer DICE_USER_GOLD_NOT_ENOUGH = ++DICE_BASE;
	@SysI18nString(content = "{0}房间不存在")
	public static final Integer DICE_USER_ROOM_NOT_EXIST = ++DICE_BASE;
	@SysI18nString(content = "bet{0}不对应")
	public static final Integer DICE_USER_BET_NOT_RIGHT = ++DICE_BASE;
	@SysI18nString(content = "房间密码错误")
	public static final Integer DICE_USER_PRI_PASSWORD_WRONG = ++DICE_BASE;
	@SysI18nString(content = "{0}房间已满")
	public static final Integer DICE_USER_ROOM_FULL = ++DICE_BASE;
	@SysI18nString(content = "您没有红包")
	public static final Integer DICE_NO_RED_PACKAGE = ++DICE_BASE;
	@SysI18nString(content = "您的红包已经领完")
	public static final Integer DICE_HAS_TAKE_PACKAGE = ++DICE_BASE;
	
	@SysI18nString(content = "用户ID_{0}_给您发的红包，请在道具商城领取")
	public static final Integer DICE_RED_PACKAGE_GET = ++DICE_BASE;
	
	
	

	
}