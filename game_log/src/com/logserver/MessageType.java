package com.logserver;

/**
 * This is an auto generated source,please don't modify it.
 */
public interface MessageType {
	/** 标准日志消息 */
	public final short LOG_RECORD = 500;				
	
	/* Log消息 */
		/** 金钱日志 */
		public final short LOG_GOLD_RECORD = 400;
		/** 聊天日志 */
		public final short LOG_CHAT_RECORD = 401;
		/** vip日志 */
		public final short LOG_VIP_RECORD = 402;
		/** 平台币日志 */
		public final short LOG_DIAMOND_RECORD = 403;
		/** 周卡日志 */
		public final short LOG_WEEKCARD_RECORD = 404;
		/** 月卡日志 */
		public final short LOG_MONTHCARD_RECORD = 405;
		/** 签到日志 */
		public final short LOG_SIGNIN_RECORD = 406;
		/** 充值日志 */
		public final short LOG_RECHARGE_RECORD = 407;
		/** 玩家基础日志 */
		public final short LOG_BASICPLAYER_RECORD = 408;
		/** 在线奖励日志 */
		public final short LOG_ONLINETIMEREWARD_RECORD = 409;
		/** 在线时间 */
		public final short LOG_ONLINETIME_RECORD = 410;
		/** 每日任务日志 */
		public final short LOG_DAILYTASK_RECORD = 411;
		/** 异常日志 */
		public final short LOG_EXCEPTION_RECORD = 412;
		/** 魅力日志 */
		public final short LOG_CHARM_RECORD = 413;
		/** 好友日志 */
		public final short LOG_FRIEND_RECORD = 414;
		/** 道具日志 */
		public final short LOG_ITEM_RECORD = 415;
		/** 改名日志 */
		public final short LOG_RENAME_RECORD = 416;
		/** 房间日志 */
		public final short LOG_TEXASROOM_RECORD = 417;
		/** 百家乐房间日志 */
		public final short LOG_BACCARATROOM_RECORD = 418;
		/** 幸运转盘日志 */
		public final short LOG_LUCKYSPIN_RECORD = 419;
		/** 老虎机日志 */
		public final short LOG_SLOT_RECORD = 420;
		/** 数据总览 */
		public final short LOG_DATAOVERVIEW_RECORD = 421;
		/** 充值新日志 */
		public final short LOG_NEWRECHARGE_RECORD = 422;
		/** 玩家登陆 */
		public final short LOG_PLAYERLOGIN_RECORD = 423;
		/** 玩家在线统计 */
		public final short LOG_PLAYERONLE_RECORD = 424;
		/** 留存统计 */
		public final short LOG_PLAYERKEEP_RECORD = 425;
		/** 老虎机房间快照 */
		public final short LOG_SLOTROOM_RECORD = 426;
		/** 用户进入退出老虎机的时间 */
		public final short LOG_INOUTTIME_RECORD = 427;
		/** 新的彩金的日志 */
		public final short LOG_JACKPOT_RECORD = 428;
		/** 竞赛日志 */
		public final short LOG_TOURNAMENT_RECORD = 429;
		/** 世界boss日志 */
		public final short LOG_WORLDBOSS_RECORD = 430;
		/** 老虎机主转的时间统计 */
		public final short LOG_STATISTICSTIME_RECORD = 431;
		/** 无双吹牛-房间信息-创建-进入-退出 */
		public final short LOG_DICECLASSICALROOM_RECORD = 432;
		/** 无双吹牛-竞猜信息 */
		public final short LOG_DICECLASSICALGUESS_RECORD = 433;
		/** 无双吹牛-叫号 */
		public final short LOG_DICECLASSICALCALLNUM_RECORD = 434;
		/** 无双吹牛-金币走向 */
		public final short LOG_DICECLASSICALBAZOOGOLD_RECORD = 435;
		/** 无双吹牛-牛牛模式-色子值 */
		public final short LOG_DICECOW_RECORD = 436;
		/** 无双吹牛-梭哈模式-色子值 */
		public final short LOG_DICESHOWHAND_RECORD = 437;
		/** 无双吹牛-签到-色子值 */
		public final short LOG_DICESIGNIN_RECORD = 438;
		/** 无双吹牛-签到-色子值 */
		public final short LOG_DICEBLACKWHITE_RECORD = 439;
		/** 无双吹牛-统计每一把输赢（所有人包括机器人） */
		public final short LOG_DICESTATISTICSWINLOST_RECORD = 440;

}