package com.gameserver.common.msg;

/**
 * 消息类型
 * 
 * @author Thinker
 * 
 */

// /////////////
// 注意
// 假如新创建一个模块 必须加在最后
// 假如在已有的模块加消息 必须加在那个模块的最后
// ////////////

public final class MessageType {
	/* === 系统内部消息类型定义结束 === */

	// /////////////
	// 服务器内部状态
	// ////////////
	public static short STAUS_BEGIN = 400;

	private static short BASE_NUMBER = 500;
	/** 每个大系统分配的消息个数 */
	public static final short NUMBER_PER_SYS = 350;
	/** 每个子系统分配的消息个数 */
	public static final short NUMBER_PER_SUB_SYS = 50;

	public static final short CG_CHAT_MSG = 20001;
	public static final short GC_CHAT_MSG = 20002;

	// /////////////
	// 各模块通用的消息
	// ////////////
	public static short COMMON_BEGIN = BASE_NUMBER;
	public static final short CG_ADMIN_COMMAND = ++COMMON_BEGIN;
	public static final short GC_SYSTEM_MESSAGE = ++COMMON_BEGIN;
	public static final short GC_PING = ++COMMON_BEGIN;
	public static final short CG_PING = ++COMMON_BEGIN;
	public static final short GC_SYSTEM_NOTICE = ++COMMON_BEGIN;
	public static final short CG_HANDSHAKE = ++COMMON_BEGIN;
	public static final short GC_HANDSHAKE = ++COMMON_BEGIN;

	// /////////////
	// 玩家登录退出模块
	// ////////////
	public static short PLAYER_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_CHECK_PLAYER_LOGIN = ++PLAYER_BEGIN;
	public static final short GC_CHECK_PLAYER_LOGIN = ++PLAYER_BEGIN;
	public static final short GC_NOTIFY_EXCEPTION = ++PLAYER_BEGIN;
	public static final short CG_PLAYER_ENTER = ++PLAYER_BEGIN;
	public static final short CG_ENTER_SCENE = ++PLAYER_BEGIN;
	public static final short GC_ENTER_SCENE = ++PLAYER_BEGIN;
	public static final short CG_QUERY_PLAYER_INFO = ++PLAYER_BEGIN;
	public static final short GC_QUERY_PLAYER_INFO = ++PLAYER_BEGIN;
	public static final short CG_QUERY_PLAYER_INFO_NAME = ++PLAYER_BEGIN;
	public static final short GC_QUERY_PLAYER_INFO_NAME = ++PLAYER_BEGIN;
	public static final short CG_CLIENT_VERSION = ++PLAYER_BEGIN;
	public static final short GC_CLIENT_VERSION = ++PLAYER_BEGIN;
	// /////////////
	// 德州模块
	// ////////////
	public static short TEXAS_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);

	public static final short CG_TEXAS_LIST = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_LIST = ++TEXAS_BEGIN;
	public static final short CG_JOIN_TEXAS = ++TEXAS_BEGIN;
	public static final short GC_JOIN_TEXAS = ++TEXAS_BEGIN;
	public static final short GC_SYNC_JOIN_TEXAS = ++TEXAS_BEGIN;
	public static final short CG_LEAVE_TEXAS = ++TEXAS_BEGIN;
	public static final short GC_LEAVE_TEXAS = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_BANKER_POS = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_BUTTOM_DEAL = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_PLAYER_TURN = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_FLOP = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_TURN = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_RIVER = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_LOOK = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_LOOK = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_FOLLOW = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_FOLLOW = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_ADD_BET = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_ADD_BET = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_ALL_IN = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_ALL_IN = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_GIVE_UP = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_GIVE_UP = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_SIDE_POOL = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_ROOM_INFO = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_SETTLE = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_SETTLE_GIVEUP = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_COINS_SYNC = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_SEAT = ++TEXAS_BEGIN;
	public static final short GC_HUMAN_TEXAS_INFO_DATA = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_AUTO = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_AUTO = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_PEOPLE_SETTING = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_PEOPLE_SETTING = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_QUICK_START = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_SEAT = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_EXIT = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_EXIT = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_COMPLEMENT = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_COMPLEMENT = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_PREPARE_RESTART = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_TIPS = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_TIPS = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_HAND_CARD = ++TEXAS_BEGIN;

	public static final short CG_TEXAS_SNG_LIST = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_SNG_LIST = ++TEXAS_BEGIN;

	public static final short CG_JOIN_TEXAS_SNG = ++TEXAS_BEGIN;
	public static final short GC_SNG_RANK = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_BIG_BLIND = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_SMALL_BLIND = ++TEXAS_BEGIN;
	public static final short GC_HUMAN_TEXAS_SNG_INFO_DATA = ++TEXAS_BEGIN;
	public static final short CG_TEXAS_VIP_LIST = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_VIP_LIST = ++TEXAS_BEGIN;
	public static final short CG_JOIN_TEXAS_VIP = ++TEXAS_BEGIN;
	public static final short GC_JOIN_TEXAS_VIP_FAILED = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_CLEAR_TABLE = ++TEXAS_BEGIN;
	public static final short GC_TEXAS_COMPLEMENT_NUM = ++TEXAS_BEGIN;
	public static final short CG_JOIN_TEXAS_ROOM_ID = ++TEXAS_BEGIN;
	public static final short CG_HUMAN_TEXAS_INFO_DATA_SEARCH = ++TEXAS_BEGIN;
	public static final short GC_HUMAN_TEXAS_INFO_DATA_SEARCH = ++TEXAS_BEGIN;
	public static final short GC_HUMAN_TEXAS_EXP = ++TEXAS_BEGIN;

	// /////////////
	// 人物模块
	// ////////////
	public static short HUMAN_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_HUMAN_DETAIL_INFO = ++HUMAN_BEGIN;
	public static final short GC_ROLE_SYMBOL_CHANGED_LONG = ++HUMAN_BEGIN;
	public static final short CG_HUMAN_CHANGE_NAME = ++HUMAN_BEGIN;
	public static final short GC_HUMAN_CHANGE_NAME = ++HUMAN_BEGIN;
	public static final short CG_HUMAN_CHANGE_SEX = ++HUMAN_BEGIN;
	public static final short GC_HUMAN_CHANGE_SEX = ++HUMAN_BEGIN;
	public static final short CG_HUMAN_CHANGE_IMG = ++HUMAN_BEGIN;
	public static final short GC_HUMAN_CHANGE_IMG = ++HUMAN_BEGIN;
	public static final short CG_HUMAN_NEW_GUIDE = ++HUMAN_BEGIN;
	public static final short CG_HUMAN_VIDEO_NUM = ++HUMAN_BEGIN;
	public static final short GC_HUMAN_VIDEO_NUM = ++HUMAN_BEGIN;
	public static final short GC_CHANEAGE_COUNTRIES = ++HUMAN_BEGIN;
	public static final short CG_CHANEAGE_COUNTRIES = ++HUMAN_BEGIN;
	public static final short GC_SLOT_ROOM1 = ++HUMAN_BEGIN;
	public static final short GC_SLOT_ROOM2 = ++HUMAN_BEGIN;
	public static final short GC_SLOT_ROOM3 = ++HUMAN_BEGIN;
	public static final short GC_SLOT_ROOM4 = ++HUMAN_BEGIN;
	public static final short GC_SLOT_ROOM5 = ++HUMAN_BEGIN;
	public static final short CG_ROOM_BIGWIN_GIFT = ++HUMAN_BEGIN;
	public static final short GC_FRIEND_GAME = ++HUMAN_BEGIN;
	public static final short CG_HUMAN_DETAIL_INFO_TODAY_VIEW = ++HUMAN_BEGIN;
	public static final short GC_HUMAN_CHANGE_VIP = ++HUMAN_BEGIN;
	public static final short CG_EXP_DOUBLE = ++HUMAN_BEGIN;
	public static final short GC_EXP_DOUBLE = ++HUMAN_BEGIN;

	public static final short CG_SLOT_ROOM_GIFT = ++HUMAN_BEGIN;
	public static final short CG_SLOT_ROOM_PLEASE = ++HUMAN_BEGIN;
	public static final short GC_SLOT_ROOM_GIFT = ++HUMAN_BEGIN;
	public static final short GC_SLOT_ROOM_PLEASE = ++HUMAN_BEGIN;

	// /////////////
	// 商城
	// ////////////
	public static short SHOP_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_SHOP_LIST = ++SHOP_BEGIN;
	public static final short GC_SHOP_LIST = ++SHOP_BEGIN;
	public static final short CG_BUY_ITEM = ++SHOP_BEGIN;
	public static final short GC_BUY_ITEM = ++SHOP_BEGIN;

	// /////////////
	// 周卡
	// ////////////
	public static short WEEK_CARD_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_HUMAN_WEEK_CARD_INFO_DATA = ++WEEK_CARD_BEGIN;
	public static final short CG_WEEK_CARD_GET = ++WEEK_CARD_BEGIN;
	public static final short GC_WEEK_CARD_GET = ++WEEK_CARD_BEGIN;

	// /////////////
	// 月卡
	// ////////////
	public static short MONTH_CARD_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_HUMAN_MONTH_CARD_INFO_DATA = ++MONTH_CARD_BEGIN;
	public static final short CG_MONTH_CARD_GET = ++MONTH_CARD_BEGIN;
	public static final short GC_MONTH_CARD_GET = ++MONTH_CARD_BEGIN;

	// /////////////
	// 背包
	// ////////////
	public static short ITEM_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_HUMAN_BAG_INFO_DATA = ++ITEM_BEGIN;

	// /////////////
	// 排行
	// ////////////
	public static short RANK_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_COMMON_RANK = ++RANK_BEGIN;
	public static final short GC_COMMON_RANK = ++RANK_BEGIN;

	// /////////////
	// 邮件
	// ////////////
	public static short MAIL_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_LOAD_MAIL_LIST = ++MAIL_BEGIN;
	public static final short GC_LOAD_MAIL_LIST = ++MAIL_BEGIN;
	public static final short CG_SEND_MAIL = ++MAIL_BEGIN;
	public static final short GC_SEND_MAIL = ++MAIL_BEGIN;
	public static final short CG_READ_MAIL = ++MAIL_BEGIN;
	public static final short GC_READ_MAIL = ++MAIL_BEGIN;
	public static final short CG_DELETE_MAIL = ++MAIL_BEGIN;
	public static final short GC_DELETE_MAIL = ++MAIL_BEGIN;
	public static final short CG_DEAL_WITH_REWARD = ++MAIL_BEGIN;
	public static final short GC_DEAL_WITH_REWARD = ++MAIL_BEGIN;
	public static final short GC_UPDATE_MAIL_LIST = ++MAIL_BEGIN;
	public static final short CG_RECEIVE_ALL = ++MAIL_BEGIN;
	public static final short GC_RECEIVE_ALL = ++MAIL_BEGIN;

	// /////////////
	// 好友
	// ////////////
	public static short RELATION_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_LOAD_FRIEND_LIST = ++RELATION_BASE;
	public static final short GC_LOAD_FRIEND_LIST = ++RELATION_BASE;
	public static final short CG_LOAD_FRIEND_REQUEST_LIST = ++RELATION_BASE;
	public static final short GC_LOAD_FRIEND_REQUEST_LIST = ++RELATION_BASE;
	public static final short CG_REQUEST_FRIEND = ++RELATION_BASE;
	public static final short GC_REQUEST_FRIEND = ++RELATION_BASE;
	public static final short GC_REQUEST_FRIEND_SYNC = ++RELATION_BASE;
	public static final short CG_APPLY_FRIEND = ++RELATION_BASE;
	public static final short GC_APPLY_FRIEND = ++RELATION_BASE;
	public static final short GC_ADD_FRIEND = ++RELATION_BASE;

	public static final short CG_DELETE_FRIEND = ++RELATION_BASE;
	public static final short GC_DELETE_FRIEND = ++RELATION_BASE;

	public static final short CG_LOAD_FRIEND_GIFT_LIST = ++RELATION_BASE;
	public static final short GC_LOAD_FRIEND_GIFT_LIST = ++RELATION_BASE;
	public static final short CG_FRIEND_GIFT = ++RELATION_BASE;
	public static final short GC_FRIEND_GIFT = ++RELATION_BASE;
	public static final short GC_FRIEND_GIFT_SYNC = ++RELATION_BASE;

	public static final short CG_FRIEND_GIFT_GET = ++RELATION_BASE;
	public static final short GC_FRIEND_GIFT_GET = ++RELATION_BASE;
	public static final short CG_FACEBOOK_FRIENDS_SYNC = ++RELATION_BASE;
	public static final short GC_FACEBOOK_FRIENDS_SYNC = ++RELATION_BASE;

	public static final short CG_STRANGER_LIST = ++RELATION_BASE;
	public static final short GC_STRANGER_LIST = ++RELATION_BASE;
	public static final short CG_FACEBOOK_NUM = ++RELATION_BASE;
	public static final short CG_ENTER_FRIENDS_ROOM = ++RELATION_BASE;
	public static final short GC_LEVEL_GIFT_MORE = ++RELATION_BASE;

	// /////////////
	// 签到
	// ////////////
	public static short SIGN_IN_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_SIGN_IN = ++SIGN_IN_BASE;
	public static final short GC_SIGN_IN = ++SIGN_IN_BASE;
	public static final short GC_SIGN_IN_INFO_DATA = ++SIGN_IN_BASE;

	// /////////////
	// 跑马灯
	// ////////////
	public static short NOTICE_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_NOTICE_INFO_DATA = ++NOTICE_BASE;
	public static final short GC_NOTICE_INFO_DATA_MULTI = ++NOTICE_BASE;

	// /////////////
	// 在线奖励
	// ////////////
	public static short MISC_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_ONLINE_REWARD = ++MISC_BASE;
	public static final short GC_ONLINE_REWARD = ++MISC_BASE;
	public static final short GC_MISC_INFO_DATA = ++MISC_BASE;
	public static final short CG_NEW_USER = ++MISC_BASE;
	public static final short GC_NEW_USER = ++MISC_BASE;
	public static final short CG_KOREAN_SB = ++MISC_BASE;
	public static final short GC_KOREAN_SB = ++MISC_BASE;
	public static final short CG_FB_INVITE = ++MISC_BASE;
	public static final short GC_FB_INVITE = ++MISC_BASE;
	public static final short CG_FB_INVITE_GET_REWARD = ++MISC_BASE;
	public static final short GC_FB_INVITE_GET_REWARD = ++MISC_BASE;
	public static final short GC_MISC_FB_INFO_DATA = ++MISC_BASE;
	public static final short CG_FB_GET_REWARD = ++MISC_BASE;
	public static final short GC_FB_GET_REWARD = ++MISC_BASE;
	public static final short CG_FB_THUMB_REWARD = ++MISC_BASE;
	public static final short GC_FB_THUMB_REWARD = ++MISC_BASE;

	// /////////////
	// vip
	// ////////////
	public static short VIP_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_VIP_BUY = ++VIP_BASE;
	public static final short GC_VIP_BUY = ++VIP_BASE;
	public static final short CG_VIP_GET = ++VIP_BASE;
	public static final short GC_VIP_GET = ++VIP_BASE;
	public static final short GC_HUMAN_VIP_INFO_DATA = ++VIP_BASE;
	public static final short CG_VIP_CREATE_ROOM = ++VIP_BASE;
	public static final short GC_VIP_CREATE_ROOM = ++VIP_BASE;

	// /////////////
	// 充值
	// ////////////
	public static short RECHARGE_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_REQUEST_ORDER = ++RECHARGE_BASE;
	public static final short GC_REQUEST_ORDER = ++RECHARGE_BASE;
	public static final short CG_VALIDATE_ORDER = ++RECHARGE_BASE;
	public static final short GC_VALIDATE_ORDER = ++RECHARGE_BASE;
	public static final short GC_ORDER_INFO_DATA_LIST = ++RECHARGE_BASE;
	public static final short GC_MYCARD_AUTHCODE = ++RECHARGE_BASE;
	public static final short CG_VALIDATE_ORDER_MYCARD = ++RECHARGE_BASE;
	public static final short CG_REQUEST_ORDER_MYCARD = ++RECHARGE_BASE;
	public static final short CG_ORDER_AMAZON = ++RECHARGE_BASE;
	public static final short CG_ORDER_AMAZON_DELIVERY = ++RECHARGE_BASE;
	public static final short GC_ORDER_AMAZON_DELIVERY = ++RECHARGE_BASE;
	public static final short CG_REQUEST_ORDER_MOL = ++RECHARGE_BASE;
	public static final short CG_VALIDATE_ORDER_MOL = ++RECHARGE_BASE;
	public static final short GC_MOL_ORDER = ++RECHARGE_BASE;
	public static final short GC_DOUBLE_TURN = ++RECHARGE_BASE;
	public static final short GC_OBTAIN_COUPON = ++RECHARGE_BASE;
	public static final short CG_COUPON_EXIST = ++RECHARGE_BASE;
	public static final short GC_COUPON_EXIST = ++RECHARGE_BASE;
	public static final short GC_REQUEST_ORDER_THIRD_PARTY = ++RECHARGE_BASE;

	// /////////////
	// 任务
	// ////////////
	public static short TASK_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_DAILY_TASK_GET = ++TASK_BASE;
	public static final short GC_DAILY_TASK_GET = ++TASK_BASE;
	public static final short GC_TASK_INFO_DATA = ++TASK_BASE;
	public static final short GC_TASK_INFO_DATA_CHANGE = ++TASK_BASE;
	public static final short CG_TASK_PROGRESS = ++TASK_BASE;
	public static final short GC_TASK_PROGRESS = ++TASK_BASE;

	// /////////////
	// 道具
	// ////////////
	public static short ITEM_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_SEND_INTERACTIVE_ITEM = ++ITEM_BASE;
	public static final short GC_SEND_INTERACTIVE_ITEM = ++ITEM_BASE;
	public static final short CG_GROUP_SEND_INTERACTIVE_ITEM = ++ITEM_BASE;
	public static final short GC_GROUP_SEND_INTERACTIVE_ITEM = ++ITEM_BASE;

	// /////////////
	// 活动
	// ////////////
	public static short ACTIVITY_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_ACTIVITY_LIST = ++ACTIVITY_BASE;
	public static final short GC_HUMAN_ACTIVITY_REWARD_DATA_LIST = ++ACTIVITY_BASE;
	public static final short CG_GET_ACTIVITY_REWARD = ++ACTIVITY_BASE;
	public static final short GC_GET_ACTIVITY_REWARD = ++ACTIVITY_BASE;
	public static final short GC_UPDATE_ACTIVITY = ++ACTIVITY_BASE;
	public static final short GC_UPDATE_HUMAN_ACTIVITY_REWARD = ++ACTIVITY_BASE;
	public static final short GC_HUNAMN_PROGRESS = ++ACTIVITY_BASE;
	public static final short GC_HUNAMN_PROGRESS_SINGLE = ++ACTIVITY_BASE;
	public static final short CG_ACTIVITY_PROGRESS = ++ACTIVITY_BASE;
	public static final short GC_MONTH_OR_WEEK = ++ACTIVITY_BASE;
	public static final short CG_MONTH_WEEK_LEFT_TIME = ++ACTIVITY_BASE;
	public static final short GC_MONTH_WEEK_LEFT_TIME = ++ACTIVITY_BASE;
	public static final short CG_ACTIVITY_LIST = ++ACTIVITY_BASE;
	public static final short CG_STILL_HAVE_ACTIVITY_GOLD = ++ACTIVITY_BASE;
	public static final short GC_STILL_HAVE_ACTIVITY_GOLD = ++ACTIVITY_BASE;
	public static final short CG_FUNCTION = ++ACTIVITY_BASE;
	public static final short GC_FUNCTION_LEFT_TIME = ++ACTIVITY_BASE;

	// /////////////
	// 百家乐
	// ////////////
	public static short BACCART_BASE = (BASE_NUMBER += NUMBER_PER_SYS);

	public static final short CG_BACCART_LIST = ++BACCART_BASE;
	public static final short GC_BACCART_LIST = ++BACCART_BASE;

	public static final short GC_BACCART_SHUFFLE = ++BACCART_BASE;
	public static final short GC_BACCART_START_BET = ++BACCART_BASE;
	public static final short GC_BACCART_SETTLE = ++BACCART_BASE;
	public static final short GC_BACCART_CLEAR_TABLE = ++BACCART_BASE;
	public static final short CG_BACCART_BET = ++BACCART_BASE;
	public static final short GC_BACCART_BET = ++BACCART_BASE;
	public static final short CG_BACCART_JOIN = ++BACCART_BASE;
	public static final short GC_BACCART_JOIN = ++BACCART_BASE;
	public static final short GC_BACCART_SYNC_JOIN = ++BACCART_BASE;
	public static final short CG_BACCART_STAND = ++BACCART_BASE;
	public static final short GC_BACCART_STAND = ++BACCART_BASE;
	public static final short CG_BACCART_SEAT = ++BACCART_BASE;
	public static final short GC_BACCART_SEAT = ++BACCART_BASE;
	public static final short CG_BACCART_EXIT = ++BACCART_BASE;
	public static final short GC_BACCART_EXIT = ++BACCART_BASE;

	public static final short GC_BACCART_PLAYER_JACKPOT = ++BACCART_BASE;
	public static final short GC_BACCART_JACKPOT = ++BACCART_BASE;
	public static final short GC_BACCART_LIGHT = ++BACCART_BASE;
	public static final short CG_HUMAN_BACCART = ++BACCART_BASE;
	public static final short GC_HUMAN_BACCART = ++BACCART_BASE;
	public static final short CG_BACCART_AUTO = ++BACCART_BASE;
	public static final short GC_BACCART_AUTO = ++BACCART_BASE;
	public static final short GC_HUMAN_BACCART_COINS_SYNC = ++BACCART_BASE;
	public static final short CG_BACCART_COMPLEMENT = ++BACCART_BASE;
	public static final short GC_BACCART_COMPLEMENT = ++BACCART_BASE;
	public static final short CG_BACCART_REVIVE = ++BACCART_BASE;
	public static final short GC_BACCART_REVIVE = ++BACCART_BASE;
	public static final short GC_BACCART_COMPLEMENT_TIP = ++BACCART_BASE;
	public static final short CG_BACCARAT_QUICK_START = ++BACCART_BASE;

	// /////////////
	// 幸运转盘
	// ////////////
	public static short LUCKY_SPIN_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_LUCKY_SPIN = ++LUCKY_SPIN_BASE;
	public static final short GC_LUCKY_SPIN = ++LUCKY_SPIN_BASE;
	public static final short GC_LUCKY_SPIN_INFO_DATA = ++LUCKY_SPIN_BASE;
	public static final short CG_LUCKY_MATCH = ++LUCKY_SPIN_BASE;
	public static final short GC_LUCKY_MATCH = ++LUCKY_SPIN_BASE;

	public static final short CG_BIG_ZHUANPAN = ++LUCKY_SPIN_BASE;
	public static final short CG_SPIN_ZHUANPAN = ++LUCKY_SPIN_BASE;
	public static final short GC_BIG_ZHUANPAN = ++LUCKY_SPIN_BASE;
	public static final short GC_SPIN_ZHUANPAN_FREE = ++LUCKY_SPIN_BASE;
	public static final short GC_SPIN_ZHUANPAN_NOFREE = ++LUCKY_SPIN_BASE;

	// /////////////
	// 老虎机
	// ////////////
	public static short SLOT_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_SLOT_ENTER = ++SLOT_BASE;
	public static final short CG_SLOT_OUT = ++SLOT_BASE;
	public static final short GC_SLOT_ENTER = ++SLOT_BASE;
	public static final short CG_SLOT_ENTER_ROOM = ++SLOT_BASE;
	public static final short CG_SLOT_SLOT = ++SLOT_BASE;
	public static final short GC_SLOT_SLOT = ++SLOT_BASE;
	public static final short CG_FREE_SLOT_REWARD = ++SLOT_BASE;
	public static final short GC_FREE_SLOT_REWARD = ++SLOT_BASE;
	public static final short CG_SLOT_GET_LIST = ++SLOT_BASE;
	public static final short GC_SLOT_LIST = ++SLOT_BASE;

	public static final short GC_SLOT_GET_REWARD = ++SLOT_BASE;
	public static final short CG_SLOT_GET_RANK = ++SLOT_BASE;
	public static final short GC_SLOT_GET_RANK = ++SLOT_BASE;
	public static final short GC_SLOT_GET_SNGBONUS = ++SLOT_BASE;
	public static final short GC_ROTARY_TABLE = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE10 = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE10_SCATTER = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE11 = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE12_CHOOSE = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE12_FREE = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE12 = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE12_FREE = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE12_CHOOSE = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE13_BOUNS_PRIZE = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE13_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE13_SEND_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE14 = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE14_BONUS = ++SLOT_BASE;

	public static final short CG_SLOT_TYPE15_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE15_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE15_BOUNS_START = ++SLOT_BASE;

	public static final short GC_SLOT_TYPE16 = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE17 = ++SLOT_BASE;

	public static final short GC_SLOT_TYPE18 = ++SLOT_BASE;

	public static final short CG_SLOT_TYPE19_SCATTER = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE19 = ++SLOT_BASE;

	public static final short GC_SLOT_TYPE20 = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE20_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE20_BOUNS_NEW = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE21_BOUNS_INFO = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE21_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE21_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE22 = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE22 = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE23_INIT_REWARD = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE23_INIT_REWARD = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE23_BOUNS_INFO = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE24_BOUNS_PRIZE = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE24_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE24_SEND_BOUNS = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE24_BOUNS_GAME_GO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE24_BOUNS_BAR = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE24_BOUNS_GAME_START = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE24_BOUNS_SAMBA = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE25_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE25_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE25_BOUNS_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE25_WILD_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE26_BOUNS_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE26_WILD_INFO = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE26_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE26_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE27_BOUNS_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE27_WILD_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE28_BOUNS_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE28_SCATTER_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE28_WILD_INFO = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE28_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE28_BOUNS = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE29_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE29_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE29_BOUNS_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE29_WILD_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE30_BOUNS = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE30_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE30_BOUNS_INFO = ++SLOT_BASE;
	public static final short CG_GET_SLOT_CACHEMSG = ++SLOT_BASE;
	public static final short GC_SLOT_ERROR = ++SLOT_BASE;
	public static final short GC_GET_SLOT_CACHEMSG = ++SLOT_BASE;
	public static final short CG_DEMO_TYPE = ++SLOT_BASE;

	public static final short CG_SNG_RANK_INFO = ++SLOT_BASE;
	public static final short GC_SNG_RANK_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE31_WILD_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE31_SPECIFIC_WILD_INFO = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE31_BOUNS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE31_BONUS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE31_BONUS_ONE = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE31_BONUS_TWO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE31_BONUS_THREE = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE32_SOCIAL_CONTACT = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE32_WILD_INFO = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE32_BONUS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE32_SPECIAL_LIST = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE32_BULLET_OUT = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE32_BULLET_IN = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE32_LEFT_BULLET_NUM = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE33_BONUS_LIST = ++SLOT_BASE;
	// 竞赛
	public static final short GC_SLOT_SNG_VIEW = ++SLOT_BASE;

	// 竞赛
	public static final short CG_SLOT_TOURNAMENT_START = ++SLOT_BASE;
	public static final short GC_SLOT_TOURNAMENT_START_DATA = ++SLOT_BASE;
	public static final short GC_SLOT_TOURNAMENT_NOT_OPEN = ++SLOT_BASE;
	public static final short GC_SLOT_RANK_LIST = ++SLOT_BASE;
	public static final short CG_TOURNAMENT_GET_LIST = ++SLOT_BASE;
	public static final short GC_TOURNAMENT_GET_LIST = ++SLOT_BASE;
	public static final short CG_SLOT_TOURNAMENT_CLOSE = ++SLOT_BASE;

	// 微信的类型
	public static final short CG_WEIXIN_ENTER = ++SLOT_BASE;

	public static final short GC_SLOT_TYPE3_BOUNS_START = ++SLOT_BASE;
	
	
	public static final short GC_REMOVE_SLOT_SLOT = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE14_APPLE_BONUS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE14_RUNE_BONUS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE14_PREY_BONUS = ++SLOT_BASE;
	
	//万圣节
	public static final short GC_SLOT_TYPE38_PUMPKIN = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE38_BONUS_TRIGGER = ++SLOT_BASE;
	public static final short CG_SLOT_TYPE38_BONUS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE38_BONUS = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE38_WILD = ++SLOT_BASE;
	public static final short GC_SLOT_TYPE38_JACKPOT = ++SLOT_BASE;
	//赢得 winner之后 获得的 翻倍转盘的接口
	public static final short GC_WINNER_WHEEL = ++SLOT_BASE;
	
	
	// /////////////
	// Vip新系统
	// ////////////

	public static short VIP_NEW_BASE = (BASE_NUMBER += NUMBER_PER_SYS);

	public static final short GC_VIP_NEW_DATA = ++VIP_NEW_BASE;

	// /////////////
	// 大厅消息模块
	// ////////////
	public static short LOBBY_BASE = (BASE_NUMBER += NUMBER_PER_SYS);

	public static final short CG_JACKPOT_LIST_DATA = ++LOBBY_BASE;
	public static final short GC_JACKPOT_LIST_DATA = ++LOBBY_BASE;
	public static final short GC_JACKPOT_LEVEL_DATA = ++LOBBY_BASE;
	public static final short CG_GAMETYPE_JACKPOT = ++LOBBY_BASE;
	public static final short GC_GAMETYPE_JACKPOT = ++LOBBY_BASE;
	public static final short CG_SLOT_NEW_JACKPOTS = ++LOBBY_BASE;
	public static final short GC_ALL_SLOT_NEW_JACKPOTS = ++LOBBY_BASE;
	public static final short CG_ALL_SLOT_NEW_JACKPOTS = ++LOBBY_BASE;
	//新的彩金返回值
	public static final short GC_HUMAN_JACKPOT_REWARD = ++LOBBY_BASE;

	// /////////////
	// 新排行榜消息模块
	// ////////////
	public static short RANKNEW_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_REQUEST_RANK = ++RANKNEW_BASE;
	public static final short GC_RANK_LIST = ++RANKNEW_BASE;
	public static final short CG_HUMAN_RANK = ++RANKNEW_BASE;
	public static final short GC_HUMAN_RANK = ++RANKNEW_BASE;

	// /////////////
	// 优惠礼包
	// ////////////
	public static short GIFT_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_REQUEST_GIFT = ++GIFT_BASE;
	public static final short GC_REQUEST_GIFT = ++GIFT_BASE;
	public static final short CG_REQUEST_GIFT_TIME_END = ++GIFT_BASE;
	public static final short GC_NEW_COMER_GIFT = ++GIFT_BASE;
	public static final short CG_NEW_COMER_GIFT = ++GIFT_BASE;

	// /////////////
	// 成就系统
	// ////////////
	public static short ACHIEVEMENT_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_ACH_INFO = ++ACHIEVEMENT_BASE;
	public static final short CG_RECEIVE_ACH = ++ACHIEVEMENT_BASE;
	public static final short GC_ACH_INFO = ++ACHIEVEMENT_BASE;
	public static final short GC_RECEIVE_ACH = ++ACHIEVEMENT_BASE;

	// /////////////
	// 兑换码系统
	// ////////////
	public static short CONVERSIONCODE_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_CONVERSION_CODE = ++CONVERSIONCODE_BASE;
	public static final short GC_CONVERSION_CODE = ++CONVERSIONCODE_BASE;
	// /////////////
	// 收集系统
	// ////////////
	public static short CHARM_EXCHANGE_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_CHARM_EXCHANGE = ++CHARM_EXCHANGE_BASE;
	public static final short GC_CHARM_EXCHANGE = ++CHARM_EXCHANGE_BASE;
	public static final short CG_COLLECT_INIT = ++CHARM_EXCHANGE_BASE;
	public static final short GC_COLLECT_INIT = ++CHARM_EXCHANGE_BASE;
	public static final short CG_RAFFLE = ++CHARM_EXCHANGE_BASE;
	public static final short GC_RAFFLE = ++CHARM_EXCHANGE_BASE;
	public static final short GC_GET_VOUCHERS = ++CHARM_EXCHANGE_BASE;
	public static final short CG_CARD_EXCHANGE = ++CHARM_EXCHANGE_BASE;
	public static final short GC_CARD_EXCHANGE = ++CHARM_EXCHANGE_BASE;

	// 存钱罐
	public static short GC_TREASURY_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_TREASURY = ++GC_TREASURY_BASE;
	public static final short GC_TREASURY = ++GC_TREASURY_BASE;

	

	// club
	public static short GC_CLUB_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_CLUB_LIST = ++GC_CLUB_BASE;
	public static final short CG_CLUB_PANEL = ++GC_CLUB_BASE;
	public static final short CG_CLUB_CREATE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_MEMBER_LIST = ++GC_CLUB_BASE;
	public static final short GC_CLUB_INFO = ++GC_CLUB_BASE;
	public static final short GC_CLUB_SIGN_DATA = ++GC_CLUB_BASE;
	public static final short CG_CLUB_SIGN = ++GC_CLUB_BASE;
	public static final short CG_CLUB_DONATE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_DONATE_DATA = ++GC_CLUB_BASE;
	public static final short CG_CLUB_SEARCH = ++GC_CLUB_BASE;
	public static final short CG_CLUB_NOTE_PANEL = ++GC_CLUB_BASE;
	public static final short CG_CLUB_NOTE_SEND = ++GC_CLUB_BASE;
	public static final short GC_CLUB_NOTE_LIST = ++GC_CLUB_BASE;
	public static final short CG_CLUB_INVATE = ++GC_CLUB_BASE;
	public static final short CG_CLUB_RANKING_lIST = ++GC_CLUB_BASE;
	public static final short CG_CLUB_JOIN = ++GC_CLUB_BASE;
	public static final short CG_CLUB_APPLY_LIST = ++GC_CLUB_BASE;
	public static final short GC_CLUB_APPLY_LIST = ++GC_CLUB_BASE;
	public static final short CG_CLUB_APPLY_OP = ++GC_CLUB_BASE;
	public static final short GC_CLUB_APPLY_OP = ++GC_CLUB_BASE;
	public static final short GC_CLUB_INVATE_LIST = ++GC_CLUB_BASE;
	public static final short CG_CLUB_NOT_JOIN = ++GC_CLUB_BASE;
	public static final short GC_CLUB_JOIN_RESULT = ++GC_CLUB_BASE;
	public static final short CG_CLUB_LEAVE = ++GC_CLUB_BASE;
	public static final short CG_CLUB_CHANGE_NAME = ++GC_CLUB_BASE;
	public static final short CG_CLUB_EDIT = ++GC_CLUB_BASE;
	public static final short CG_CLUB_GET_GIFT = ++GC_CLUB_BASE;
	public static final short CG_CLUB_INFO_GET = ++GC_CLUB_BASE;
	public static final short CG_CLUB_INVATE_LIST = ++GC_CLUB_BASE;
	public static final short CG_CLUB_JOIN2 = ++GC_CLUB_BASE;
	public static final short CG_CLUB_KICK = ++GC_CLUB_BASE;
	public static final short CG_CLUB_NOTE_DELETE = ++GC_CLUB_BASE;
	public static final short CG_CLUB_NOTE_SEND_GIFT = ++GC_CLUB_BASE;
	public static final short CG_CLUB_PROMATE = ++GC_CLUB_BASE;
	public static final short CG_CLUB_TANHE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_CHANGE_NAME = ++GC_CLUB_BASE;
	public static final short GC_CLUB_DONATE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_EDIT = ++GC_CLUB_BASE;
	public static final short GC_CLUB_GET_GIFT = ++GC_CLUB_BASE;
	public static final short GC_CLUB_INFO_GET = ++GC_CLUB_BASE;
	public static final short GC_CLUB_INVATE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_JOIN2 = ++GC_CLUB_BASE;
	public static final short GC_CLUB_KICK = ++GC_CLUB_BASE;
	public static final short GC_CLUB_LEAVE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_NOTE_DELETE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_PROMATE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_RANKING_LIST = ++GC_CLUB_BASE;
	public static final short GC_CLUB_SEARCH_RESULT = ++GC_CLUB_BASE;
	public static final short GC_CLUB_SIGN = ++GC_CLUB_BASE;
	public static final short GC_CLUB_TANHE_VOTE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_TANHE_STATE = ++GC_CLUB_BASE;
	public static final short GC_CLUB_TANHE = ++GC_CLUB_BASE;
	public static final short CG_CLUB_TANHE_VOTE = ++GC_CLUB_BASE;
	public static final short CG_CLUB_TANHE_STATE = ++GC_CLUB_BASE;
	
	
	public static short GC_WORLD_BOSS_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_OPEN_PANEL = ++GC_WORLD_BOSS_BASE;
	public static final short CG_GET_BOSS_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short GC_GET_BOSS_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short CG_GET_RANK_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short GC_GET_RANK_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short GC_REFRESH_BOSS_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short CG_BOSS_START_END_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short GC_BOSS_START_END_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short GC_SELF_ATTACK_BLOOD_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short CG_RETURN_BLOOD = ++GC_WORLD_BOSS_BASE;
	public static final short GC_RETURN_BLOOD = ++GC_WORLD_BOSS_BASE;
	public static final short CG_REFRESH_BOSS_INFO = ++GC_WORLD_BOSS_BASE;
	public static final short GC_BEFORE_START = ++GC_WORLD_BOSS_BASE;
	public static final short CG_BEFORE_START = ++GC_WORLD_BOSS_BASE;
	
	
	public static short GC_GIVEALIKE_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_GIVEALIKE_SAVE = ++GC_GIVEALIKE_BASE;
	public static final short GC_GET_GIVEALIKE_INFO = ++GC_GIVEALIKE_BASE;
	public static final short CG_ISNOT_GIVEALIKE = ++GC_GIVEALIKE_BASE;
	public static final short GC_ISNOT_GIVEALIKE = ++GC_GIVEALIKE_BASE;
	

	public static short GC_NEW_JACKPOT_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_NEW_JACKPOT = ++GC_NEW_JACKPOT_BASE;
	public static final short GC_NEW_JACKPOT = ++GC_NEW_JACKPOT_BASE;
	public static final short GC_SLOT_NEW_JACKPOTS = ++GC_NEW_JACKPOT_BASE;

	
	public static short GC_NEWBIE_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_GET_SAVE_POINT = ++GC_NEWBIE_BASE;
	public static final short GC_GET_SAVE_POINT = ++GC_NEWBIE_BASE;
	public static final short CG_SAVE_POINT = ++GC_NEWBIE_BASE;
	public static final short GC_SAVE_POINT = ++GC_NEWBIE_BASE;
	public static final short CG_SLOT_NEWBIE = ++GC_NEWBIE_BASE;
	public static final short GC_SLOT_NEWBIE = ++GC_NEWBIE_BASE;

	public static short GC_PAY_GUIDE_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_PAY_GUIDE = ++GC_PAY_GUIDE_BASE;
	public static final short CG_ITEM_INVOKE = ++GC_PAY_GUIDE_BASE;
	
	//无双吹牛
	public static short GC_BAZOO_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_ROOM_ENTER = ++GC_BAZOO_BASE;
	public static final short CG_ROOM_OUT = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_ENTER = ++GC_BAZOO_BASE;
	public static final short GC_DICE_UNIFY_SWING = ++GC_BAZOO_BASE;
	public static final short CG_DICE_SINGLE_SWING = ++GC_BAZOO_BASE;
	public static final short CG_TALK_BIG = ++GC_BAZOO_BASE;
	public static final short GC_TALK_BIG = ++GC_BAZOO_BASE;
	public static final short CG_ROB_OPEN = ++GC_BAZOO_BASE;
	public static final short GC_ROB_OPEN = ++GC_BAZOO_BASE;
	public static final short CG_GUESS_BIG_SMALL = ++GC_BAZOO_BASE;
	public static final short GC_GUESS_BIG_SMALL = ++GC_BAZOO_BASE;
	public static final short GC_END_COUNT = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_OUT = ++GC_BAZOO_BASE;
	public static final short CG_MODE_CHOSE = ++GC_BAZOO_BASE;
	public static final short GC_DICE_SINGLE_SWING = ++GC_BAZOO_BASE;
	public static final short GC_MODE_CHOSE = ++GC_BAZOO_BASE;
	public static final short CG_ROOM_CREATE = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_MATCHED = ++GC_BAZOO_BASE;
	public static final short CG_ROOM_PRI_SEARCH = ++GC_BAZOO_BASE;
	public static final short CG_ROOM_PRI_JOIN = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_PRI_SEARCH = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_CREATE = ++GC_BAZOO_BASE;
	public static final short GC_DICE_USER_SHOULD_SWING = ++GC_BAZOO_BASE;
	public static final short GC_GUESS_OPEN = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_STATE = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_INIT = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_ENTER = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_MATCHING = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_READY = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_ROUND_BEGIN = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_ROUND_TURN = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_CALL_DICE = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_ROUND_OPEN = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_ROUND_GUESS = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_ROUND_RESULT = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_HEART_BEAT = ++GC_BAZOO_BASE;
	public static final short GC_BAZOO_HEART_BEAT = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_ENTER_NOT_ALLOW = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_CHANGE_NAME = ++GC_BAZOO_BASE;
	public static final short GC_BAZOO_CHANGE_NAME = ++GC_BAZOO_BASE;
	public static final short CG_ROOM_PUB_JOIN = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_HALL_STATUS = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_BE_REMOVEED = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_MATCHEDING = ++GC_BAZOO_BASE;
	public static final short CG_ROOM_PRI_List = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_PRI_List = ++GC_BAZOO_BASE;
	public static final short GC_ROOM_PRI_JOIN = ++GC_BAZOO_BASE;
	public static final short GC_ROBOT_DICE_UNIFY_SWING = ++GC_BAZOO_BASE;
	public static final short GC_ROBOT_WHICH_ROOM_TO_GOIN = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_MAGIC_FACE = ++GC_BAZOO_BASE;
	public static final short GC_BAZOO_MAGIC_FACE = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_SEND_GIFT = ++GC_BAZOO_BASE;
	public static final short GC_BAZOO_SEND_GIFT = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_RED_PACKAGE = ++GC_BAZOO_BASE;
	public static final short GC_BAZOO_RED_PACKAGE = ++GC_BAZOO_BASE;
	public static final short GC_BLACK_WHITE_ALL_SWING = ++GC_BAZOO_BASE;
	public static final short GC_BLACK_WHITE_WHO_TURN = ++GC_BAZOO_BASE;
	public static final short CG_BLACK_WHITE_CALL_NUM = ++GC_BAZOO_BASE;
	public static final short GC_BLACK_WHITE_CALL_NUM = ++GC_BAZOO_BASE;
	public static final short GC_BLACK_WHITE_END_COUNT = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT = ++GC_BAZOO_BASE;
	public static final short GC_STATE_ROOM_BLACK_WHITE_END = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_BOQU = ++GC_BAZOO_BASE;
	public static final short GC_BAZOO_BOQU = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_NEWGUY_PROCESS = ++GC_BAZOO_BASE;
	public static final short CG_BAZOO_FACEBOOK_ADD_GOLD = ++GC_BAZOO_BASE;
	
	
	public static short GC_COW_BAZOO_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_COW_UNIFY_SWING = ++GC_COW_BAZOO_BASE;
	public static final short CG_COW_SINGLE_SWING = ++GC_COW_BAZOO_BASE;
	public static final short GC_COW_END_UNIFY_SWING = ++GC_COW_BAZOO_BASE;
	public static final short GC_COW_SINGLE_SWING = ++GC_COW_BAZOO_BASE;
	public static final short GC_COW_SINGLE_SWING_BEGIN = ++GC_COW_BAZOO_BASE;
	public static final short GC_COW_SINGLE_SWING_END = ++GC_COW_BAZOO_BASE;
	public static final short GC_STATE_ROOM_SINGLE_SWING_BEGIN = ++GC_COW_BAZOO_BASE;
	public static final short GC_STATE_ROOM_SINGLE_SWING_END = ++GC_COW_BAZOO_BASE;

	
	public static short GC_SHOW_HAND_BAZOO_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_SHOW_HAND_UNIFY_SWING = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_SHOW_HAND_LITTLE_SWING = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short CG_SHOW_HAND_SINGLE_SWING = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_SHOW_HAND_SINGLE_SWING = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_STATE_ROOM_SHOW_HAND_ALL_SWING = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short CG_SHOW_HAND_SINGLE_SWICH = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_SHOW_HAND_SINGLE_SWICH = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short CG_SHOW_HAND_SINGLE_SWICH_CANCEL = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_SHOW_HAND_SINGLE_SWICH_CANCEL = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_SHOW_HAND_END_COUNT = ++GC_SHOW_HAND_BAZOO_BASE;
	public static final short GC_BAZOO_HALL_STATUS = ++GC_SHOW_HAND_BAZOO_BASE;
	
	public static short GC_BAZOO_SIGNIN_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_BAZOO_SIGNIN = ++GC_BAZOO_SIGNIN_BASE;
	public static final short GC_BAZOO_SIGNIN = ++GC_BAZOO_SIGNIN_BASE;
	
	
	public static short GC_BAZOO_RANK_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_BAZOO_RANK_REQUEST = ++GC_BAZOO_RANK_BASE;
	public static final short GC_BAZOO_RANK_REQUEST = ++GC_BAZOO_RANK_BASE;
	public static final short CG_BAZOO_RANK_TOTAL_GOLD_REQUEST = ++GC_BAZOO_RANK_BASE;
	
	
	public static short GC_BAZOO_TASK_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_BAZOO_TASK = ++GC_BAZOO_TASK_BASE;
	public static final short GC_BAZOO_TASK = ++GC_BAZOO_TASK_BASE;
	public static final short CG_BAZOO_GET_REWARD = ++GC_BAZOO_TASK_BASE;
	public static final short CG_BAZOO_ACHIEVE_TASK = ++GC_BAZOO_TASK_BASE;
	public static final short GC_BAZOO_ACHIEVE_TASK = ++GC_BAZOO_TASK_BASE;
	public static final short GC_BAZOO_GET_REWARD = ++GC_BAZOO_TASK_BASE;
	
	public static short GC_BAZOO_ACHIEVE_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_BAZOO_ACHIEVE_FIRST = ++GC_BAZOO_ACHIEVE_BASE;
	public static final short CG_BAZOO_ACHIEVE = ++GC_BAZOO_ACHIEVE_BASE;
	public static final short GC_BAZOO_ACHIEVE = ++GC_BAZOO_ACHIEVE_BASE;
	
	public static short GC_BAZOO_ITEM_BASE = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_BAZOO_MALL_REQUEST = ++GC_BAZOO_ITEM_BASE;
	public static final short GC_BAZOO_MALL_REQUEST = ++GC_BAZOO_ITEM_BASE;
	public static final short CG_BAZOO_ITEM_REQUEST = ++GC_BAZOO_ITEM_BASE;
	public static final short GC_BAZOO_ITEM_REQUEST = ++GC_BAZOO_ITEM_BASE;
	public static final short CG_BAZOO_ITEM_CLOCK_CHANGE = ++GC_BAZOO_ITEM_BASE;
	public static final short GC_BAZOO_ITEM_CLOCK_CHANGE = ++GC_BAZOO_ITEM_BASE;
	public static final short CG_BAZOO_ITEM_BUY_BY_GOLD = ++GC_BAZOO_ITEM_BASE;
	public static final short GC_BAZOO_ITEM_BUY_BY_GOLD = ++GC_BAZOO_ITEM_BASE;
	public static final short GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL = ++GC_BAZOO_ITEM_BASE;
	
	public static short GC_BAZOO_NEW_GUY = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_BAZOO_NEW_GUY_PROCESS = ++GC_BAZOO_NEW_GUY;
	
	private MessageType() {
	}

}