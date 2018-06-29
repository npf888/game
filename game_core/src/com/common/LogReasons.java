package com.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 日志系统的日志原因定义
 * @author Thinker
 * 
 */
public interface LogReasons
{

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target( { ElementType.FIELD, ElementType.TYPE })
	public @interface ReasonDesc
	{
		/**
		 * 原因的文字描述
		 * 
		 * @return
		 */
		String value();
	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target( { ElementType.FIELD, ElementType.TYPE })
	public @interface LogDesc
	{
		/**
		 * 日志描述
		 * 
		 * @return
		 */
		String desc();
	}

	/**
	 * LogReason的通用接口
	 */
	public static interface ILogReason
	{
		/**
		 * 取得原因的序号
		 * 
		 * @return
		 */
		public int getReason();

		/**
		 * 获取原因的文本
		 * 
		 * @return
		 */
		public String getReasonText();
	}


	


	/**
	 * 经验的原因接口
	 * 
	 * @param <E>
	 *            枚举类型
	 */
	public static interface ILevelLogReason<E extends Enum<E>> extends
			ILogReason {
		public E getReasonEnum();
	}

	/**
	 * 建筑的级别变化相关
	 */
	@LogDesc(desc = "角色经验级别变化")
	public enum LevelLogReason implements ILevelLogReason<LevelLogReason> {
		/** 正常升级 */
		@ReasonDesc("正常升级")
		REASON_LEVEL_UP_NORMAL(1, "角色获得的经验值={0}"),
		@ReasonDesc("QA修改经验")
		QA_MODIFY_LEVEL(2,"增加经验值={0}"),
;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private LevelLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public LevelLogReason getReasonEnum() {
			return this;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 武将级别变化相关
	 */
	@LogDesc(desc = "武将级别变化")
	public enum PetLevelLogReason implements ILevelLogReason<PetLevelLogReason> {
	 
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private PetLevelLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public PetLevelLogReason getReasonEnum() {
			return this;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 任务日志产生原因
	 */
	@LogDesc(desc = "任务")
	public enum TaskLogReason implements ILogReason 
	{
		/** 领取任务 */
		@ReasonDesc("领取任务")
		REASON_TASK_ACCEPT(0, "任务Id={0,number,#}|任务名称={1}|难度={2}|最低接受等级={3}"),
		/** 放弃任务 */
		@ReasonDesc("放弃任务")
		REASON_TASK_GIVEUP(1, "任务Id={0,number,#}|任务名称={1}|难度={2}|最低接受等级={3}"),
		/** 完成任务 */
		@ReasonDesc("完成任务")
		REASON_TASK_FINISH(2, "任务Id={0,number,#}|任务名称={1}|难度={2}|最低接受等级={3}"),
		/** 删除已完成任务 */
		@ReasonDesc("删除已完成任务")
		REASON_TASK_FINISH_DELETE(3, ""),
		/** 删除正执行任务 */
		@ReasonDesc("删除正执行任务")
		REASON_TASK_DOING_DELETE(4, "");
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private TaskLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 任务引导日志产生原因
	 */
	@LogDesc(desc = "任务引导")
	public enum TaskGuideLogReason implements ILogReason {
		 ;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private TaskGuideLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	

	/**
	 * 快照日志的产生原因
	 */
	@LogDesc(desc = "快照")
	public enum SnapLogReason implements ILogReason {
		/** 登入时的用户状态快照 */
		@ReasonDesc("登入时的用户状态快照")
		REASON_SNAP_LOGIN_STATUS(0, ""),
		/** 退出时的用户状态快照 */
		@ReasonDesc("退出时的用户状态快照")
		REASON_SNAP_LOGOUT_STATUS(1, ""),
		/** 断线时的用户状态快照 */
		@ReasonDesc("被踢掉时的用户状态快照")
		REASON_SNAP_KICK_STATUS(2, ""),
		 
		/** 用户退出时身上物品的状态 */
		@ReasonDesc("用户退出时身上物品的状态")
		REASON_SNAP_LOGOUT_ITEM(4, ""),
		/** 异常掉线时的保存 */
		@ReasonDesc("被踢掉时身上物品的状态")
		REASON_SNAP_KICK_ITEM(5, ""),
	 
		/** 用户退出时武将的状态 */
		@ReasonDesc("用户退出时武将的状态")
		REASON_SNAP_LOGOUT_PET(7, ""),
		/** 异常掉线时的宠物状态 */
		@ReasonDesc("被踢掉时的武将状态")
		REASON_SNAP_KICK_PET(8, ""), ;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private SnapLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}



	/**
	 * Gm命令日志原因
	 * 
	 */
	@LogDesc(desc = "使用GM命令")
	public enum GmCommandLogReason implements ILogReason {
		 
		/** 合法使用GM命令 */
		@ReasonDesc("合法使用GM命令")
		REASON_VALID_USE_GMCMD(1, ""), 
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private GmCommandLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 在线日志原因
	 * 
	 */
	@LogDesc(desc = "玩家在线时长")
	public enum OnlineTimeLogReason implements ILogReason {
		/** 无意义 */
		@ReasonDesc("无意义")
		DEFAULT(0, "正常退出"),
		@ReasonDesc("home键暂时退出")
		TEMPORARYEXIT(1, "home键暂时退出"),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private OnlineTimeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 充值日志产生原因
	 * 
	 */
	@LogDesc(desc = "充值")
	public enum ChargeLogReason implements ILogReason {
		@ReasonDesc("用户充值")
		USER_CHARGE(1, "用户充值"),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ChargeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	
	@LogDesc(desc = "邮件系统")
	public enum MailLogReason implements ILogReason {
		@ReasonDesc("邮件发送")
		MAIL_SEND(0, "邮件发送"),
		@ReasonDesc("发送好友申请邮件")
		MAIL_SYSAPPLY_FRIEND(1, "发送好友申请邮件"),
		@ReasonDesc("发送充值返还邮件")
		MAIL_CHARGE_REFUND_SEND(2, "发送充值返还邮件"),
		@ReasonDesc("发送等级返还邮件")
		MAIL_LEVEL_REFUND_SEND(3, "发送等级返还邮件"),
		@ReasonDesc("发送竞技场奖励邮件")
		MAIL_SYSAREN_AREWARD(4, "发送竞技场奖励邮件"),
		@ReasonDesc("发送过关斩将奖励邮件")
		MAIL_SYSCUTPET_REWARD(5, "发送过关斩将奖励邮件"),
		@ReasonDesc("发送新手引导任务邮件")
		MAIL_SYS_GUIDE(6, "发送新手引导任务邮件"),
		@ReasonDesc("发送用户补偿邮件")
		MAIL_USERPRIZE(7, "发送用户补偿邮件"),
		@ReasonDesc("发世界boss参与奖励邮件")
		MAIL_WORLDBOSS_PARTAKEREWARD(8, "发世界boss参与奖励邮件"),
		@ReasonDesc("发世界boss排行奖励邮件")
		MAIL_WORLDBOSS_RANKREWARD(9, "发世界boss排行奖励邮件"),
		@ReasonDesc("发世界boss击杀奖励邮件")
		MAIL_WORLDBOSS_KILLREWARD(10, "发世界boss击杀奖励邮件"),
		@ReasonDesc("发送补偿邮件")
		MAIL_COMPENSATION(11, "发送补偿邮件"),
		@ReasonDesc("发送活动奖励邮件")
		MAIL_ACTIVITY(12, "发送活动奖励邮件"),
		@ReasonDesc("读取邮件")
		MAIL_READ(13, "读取邮件"),
		@ReasonDesc("发送馈赠友情点邮件")
		MAIL_OFFLINETEAM_REWARD(14, "发送馈赠友情点邮件"),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private MailLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	 
	
	/**
	 * 系统错误（Exception）日志产生原因<p>
	 * iTermiantor 2011-10-02
	 */
	@LogDesc(desc = "Exception")
	public enum ExceptionLogReason implements ILogReason {
 
		@ReasonDesc("系统忽略错误")
		DEFAULT_EXCEPTION(1, ""),
		@ReasonDesc("系统不可忽略错误")
		DEFAULT_ERROR(2, ""),
		@ReasonDesc("系统IO超时")
		IO_TIMEOUT_ERROR(3, "");

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ExceptionLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	
	 
	
	 

	/**
	 * 快客绑定日志
	 * 
	 * iTermiantor 2011-10-02
	 */
	@LogDesc(desc = "QuickerBindLog")
	public enum QuickerBindLogReason implements ILogReason {
 ;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private QuickerBindLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
   
	@LogDesc(desc = "改名日志")
	public enum ChangeNameLogReason implements ILogReason {

		@ReasonDesc("改名日志")
		CHANGE_NAME(0, "改名日志");

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ChangeNameLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	
	 
	 
	@LogDesc(desc = "同意加好友")
	public enum FriendApproveLogReason implements ILogReason {

		@ReasonDesc("好友接受")
		FRIEND_APPROVE(0,"申请者={0}({1})|申请描述内容={2}|同意申请者={3}");


		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private FriendApproveLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "拒绝加好友")
	public enum FriendRejectLogReason implements ILogReason {
		@ReasonDesc("好友拒绝")
		FRIEND_REJECT(0,"申请者={0}({1})|申请描述内容={2}|拒绝申请者={3}");

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private FriendRejectLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	

	 
	 
	
	/**
	 * 
	 * vip产生日志产生日志的原因
	 * 
	 */
	@LogDesc(desc = "vip操作")
	public enum VipLogReason implements ILogReason {

		@ReasonDesc("购买vip卡")
		BUY_VIP(1, "vip级别从{0}变为{1}"),
	
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private VipLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}

	@LogDesc(desc = "道具日志")
	public enum ItemLogReason implements ILogReason 
	{
		/** 道具*/
		@ReasonDesc("道具使用")
		ITEM_USE(1, "道具使用,道具Id={0}"),
		@ReasonDesc("道具购买")
		ITEM_BUY(2, "道具购买,商品Id={0}"),
		@ReasonDesc("sng门票使用")
		SNG_TICKET_USE(3, "sng门票使用"),
		@ReasonDesc("sng门票返还")
		SNG_TICKET_REFUND(4, "sng门票返还"),
		@ReasonDesc("活动赠送")
		ACTIVITY_GIVEN(5, "活动赠送"),
		@ReasonDesc("签到")
		WEEK_SIGN_IN(6, "签到"),
		@ReasonDesc("邮件奖励")
		MAIL_REWARD(7, "邮件奖励"),
		@ReasonDesc("周卡初始奖励")
		WEEK_CARD_INIT(8, "周卡初始奖励"),
		@ReasonDesc("周卡每天奖励")
		WEEK_CARD_DAILY(9, "周卡每天奖励"),
		@ReasonDesc("月卡初始奖励")
		MONTH_CARD_INIT(10, "月卡初始奖励"),
		@ReasonDesc("月卡每天奖励")
		MONTH_CARD_DAILY(11, "月卡每天奖励"),
		@ReasonDesc("每天任务奖励")
		DAILY_TASK(12, "每天任务奖励"),
		@ReasonDesc("vip初始奖励")
		VIP_INIT(13, "vip初始奖励"),
		@ReasonDesc("vip剩余奖励")
		VIP_REMAIN(14, "vip剩余奖励"),
		@ReasonDesc("vip每日奖励")
		VIP_DAILY(15, "vip每日奖励"),
		@ReasonDesc("在线奖励")
		ONLINE_REWARD(16, "在线奖励"),
		@ReasonDesc("好友礼物")
		FRIEND_GIFT(17, "好友礼物"),
		@ReasonDesc("幸运轮盘")
		LUCKY_SPIN(18, "幸运轮盘"),
		@ReasonDesc("幸运匹配")
		LUCKY_MATCH_GET(19, "幸运匹配"),
		@ReasonDesc("fb邀请奖励")
		FB_INVITE(20, "fb邀请奖励"),
		@ReasonDesc("fb奖励")
		FB_REWARD(21, "fb奖励"),
		@ReasonDesc("兑换筹码奖励")
		CONVERSION(22, "兑换筹码奖励"),
		@ReasonDesc("观看视频获得")
		WATCHVIDEO(23, "观看视频获得"),
		;
		
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ItemLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "金钱改变")
	public enum GoldLogReason implements ILogReason , IndexedEnum
	{
		/** 德州房间换取筹码*/
		@ReasonDesc("德州房间换取筹码")
		ENTER_TEXAS_ROOM(1, "德州房间换取筹码,房间类型Id={0}|携带量={1}"),
		@ReasonDesc("德州退出房间")
		EXIT_TEXAS_ROOM(2, "德州房间退出房间拿回筹码,房间类型Id={0}|拿回={1}"),
		@ReasonDesc("购买商品")
		BUY_ITEM(3, "购买商品,商品Id={0}|数量={1}"),
		@ReasonDesc("发送互动道具")
		BUY_INTERACTIVE_ITEM(4, "发送互动道具,互动道具Id={0}|数量={1}"),
		@ReasonDesc("每周签到")
		WEEK_SIGN_IN(5, "每周签到"),
		@ReasonDesc("邮件奖励")
		MAIL_REWARD(6, "邮件奖励"),
		@ReasonDesc("月卡初始奖励")
		MONTH_CARD_INIT(7, "月卡初始奖励"),
		@ReasonDesc("月卡每天奖励")
		MONTH_CARD_DAILY(8, "月卡每天奖励"),
		@ReasonDesc("周卡初始奖励")
		WEEK_CARD_INIT(9, "周卡初始奖励"),
		@ReasonDesc("周卡每天奖励")
		WEEK_CARD_DAILY(10, "周卡每天奖励"),
		@ReasonDesc("vip初始奖励")
		VIP_INIT(11, "vip初始奖励"),
		@ReasonDesc("vip每天奖励")
		VIP_DAILY(12, "vip每天奖励"),
		@ReasonDesc("vip剩余奖励")
		VIP_REMAIN(13, "vip剩余"),
		@ReasonDesc("德州房间补充筹码")
		TEXAS_ROOM_COMPLEMENT(14, "德州房间补充筹码,房间类型Id={0}|补充量={1}"),
		@ReasonDesc("活动赠送筹码")
		ACTIVITY(15, "活动赠送筹码,活动Id={0},活动档次id={1}"),
		@ReasonDesc("德州小费")
		TEXAS_TIPS(16, "德州小费,房间Id={0},"),
		@ReasonDesc("购买筹码")
		BUY_COINS(17, "购买筹码"),
		@ReasonDesc("每日任务领取筹码")
		DAILY_TASK(18, "每日任务"),
		@ReasonDesc("在线奖励领取筹码")
		ONLINE_REWARD(19, "在线奖励"),
		@ReasonDesc("sng费用")
		SNG_FEE(20, "sng费用,sng类型{0}|sng费用{1}"),
		@ReasonDesc("sng银杯奖励")
		SNG_SILVER_REWARD(21, "sng银杯奖励"),
		@ReasonDesc("sng金杯奖励")
		SNG_GOLD_REWARD(22, "sng金杯奖励"),
		@ReasonDesc("sng 退票")
		SNG_FEE_REFUND(23, "sng退票,sng类型{0}|sng费用{1}"),
		@ReasonDesc("新手引导奖励")
		NEW_USER_REWARD(24, "新手引导奖励{0}"),
		@ReasonDesc("好友礼物")
		FRIEND_GIFT(25, "好友礼物"),
		@ReasonDesc("机器人补充筹码{0}")
		ROBOT_GIVE(26, "机器人补充筹码"),
		@ReasonDesc("百家乐进入兑换筹码")
		BACCARAT_ENTER(27, "百家乐进入兑换筹码{0}"),
		@ReasonDesc("百家乐退出返回筹码")
		BACCARAT_EXIT(28, "百家乐退出返回筹码{0}"),
		@ReasonDesc("百家乐补充筹码")
		BACCARAT_ROOM_COMPLEMENT(29, "百家乐补充筹码{0}"),
		@ReasonDesc("转账")
		TRANSFER(30, "转账给玩家{0}，筹码{1}"),
		@ReasonDesc("幸运转盘")
		LUCKYSPIN(31, "幸运转盘"),
		@ReasonDesc("幸运匹配")
		LUCKY_MATCH_GET(32, "幸运匹配"),
		@ReasonDesc("幸运匹配花费")
		LUCKY_MATCH_COST(33, "幸运匹配花费"),
		@ReasonDesc("老虎机")
		SLOT_COST(34, "老虎机花费"),
		@ReasonDesc("老虎机获得")
		SLOT_GET(35, "老虎机{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得")
		SLOT_SCATTER(36, "老虎机小游戏{0},下注{1}"),
		@ReasonDesc("fb邀请奖励")
		FB_INVITE(37, "fb邀请奖励"),
		@ReasonDesc("fb奖励")
		FB_REWARD(38, "fb奖励"),
		@ReasonDesc("德州获得彩金")
		TEXAS_JACKPOT(39, "德州扑克{0},获得彩金{1}"),
		
		@ReasonDesc("充值")
		RECHARGE(40, "充值档次={0},获得筹码={1}"),
		@ReasonDesc("兑换码获得")
		CONVERSION(41, "兑换码={0},获得筹码={1}"),
		
		@ReasonDesc("老虎机花费1")
		SLOT_COST1(51,"老虎机花费1"),
		@ReasonDesc("老虎机花费2")
		SLOT_COST2(52,"老虎机花费2"),
		@ReasonDesc("老虎机花费3")
		SLOT_COST3(53,"老虎机花费3"),
		@ReasonDesc("老虎机花费4")
		SLOT_COST4(54,"老虎机花费4"),
		@ReasonDesc("老虎机花费5")
		SLOT_COST5(55,"老虎机花费5"),
		@ReasonDesc("老虎机花费6")
		SLOT_COST6(56,"老虎机花费6"),
		@ReasonDesc("老虎机花费7")
		SLOT_COST7(57,"老虎机花费7"),
		@ReasonDesc("老虎机花费8")
		SLOT_COST8(58,"老虎机花费8"),
		@ReasonDesc("老虎机花费9")
		SLOT_COST9(59,"老虎机花费9"),
		@ReasonDesc("老虎机花费10")
		SLOT_COST10(60,"老虎机花费10"),
		@ReasonDesc("老虎机花费11")
		SLOT_COST11(61,"老虎机花费11"),
		@ReasonDesc("老虎机花费12")
		SLOT_COST12(62,"老虎机花费12"),
		@ReasonDesc("老虎机花费13")
		SLOT_COST13(63,"老虎机花费13"),
		@ReasonDesc("老虎机花费14")
		SLOT_COST14(64,"老虎机花费14"),
		@ReasonDesc("老虎机花费15")
		SLOT_COST15(65,"老虎机花费15"),
		@ReasonDesc("老虎机花费16")
		SLOT_COST16(66,"老虎机花费16"),
		@ReasonDesc("老虎机花费17")
		SLOT_COST17(67,"老虎机花费17"),
		@ReasonDesc("老虎机花费18")
		SLOT_COST18(68,"老虎机花费18"),
		@ReasonDesc("老虎机花费19")
		SLOT_COST19(69,"老虎机花费19"),
		@ReasonDesc("老虎机花费20")
		SLOT_COST20(70,"老虎机花费20"),
		@ReasonDesc("老虎机花费21")
		SLOT_COST21(71,"老虎机花费21"),
		@ReasonDesc("老虎机花费22")
		SLOT_COST22(72,"老虎机花费22"),
		@ReasonDesc("老虎机花费23")
		SLOT_COST23(73,"老虎机花费23"),
		@ReasonDesc("老虎机花费24")
		SLOT_COST24(74,"老虎机花费24"),
		@ReasonDesc("老虎机花费25")
		SLOT_COST25(75,"老虎机花费25"),
		@ReasonDesc("老虎机花费26")
		SLOT_COST26(76,"老虎机花费26"),
		@ReasonDesc("老虎机花费27")
		SLOT_COST27(77,"老虎机花费27"),
		@ReasonDesc("老虎机花费28")
		SLOT_COST28(78,"老虎机花费28"),
		@ReasonDesc("老虎机花费29")
		SLOT_COST29(79,"老虎机花费29"),
		@ReasonDesc("老虎机花费30")
		SLOT_COST30(80,"老虎机花费30"),
		@ReasonDesc("老虎机花费31")
		SLOT_COST31(81,"老虎机花费31"),
		@ReasonDesc("老虎机花费32")
		SLOT_COST32(82,"老虎机花费32"),
		@ReasonDesc("老虎机花费33")
		SLOT_COST33(83,"老虎机花费33"),
		@ReasonDesc("老虎机花费34")
		SLOT_COST34(84,"老虎机花费34"),
		@ReasonDesc("老虎机花费35")
		SLOT_COST35(85,"老虎机花费35"),
		@ReasonDesc("老虎机花费36")
		SLOT_COST36(86,"老虎机花费36"),
		@ReasonDesc("老虎机花费37")
		SLOT_COST37(87,"老虎机花费37"),
		@ReasonDesc("老虎机花费38")
		SLOT_COST38(88,"老虎机花费37"),
		@ReasonDesc("老虎机花费39")
		SLOT_COST39(89,"老虎机花费39"),
		@ReasonDesc("老虎机花费40")
		SLOT_COST40(90,"老虎机花费40"),
		@ReasonDesc("老虎机花费41")
		SLOT_COST41(91,"老虎机花费41"),
		@ReasonDesc("老虎机花费42")
		SLOT_COST42(92,"老虎机花费42"),
		@ReasonDesc("老虎机花费43")
		SLOT_COST43(93,"老虎机花费43"),
		@ReasonDesc("老虎机花费44")
		SLOT_COST44(94,"老虎机花费44"),
		@ReasonDesc("老虎机花费45")
		SLOT_COST45(95,"老虎机花费45"),
		@ReasonDesc("老虎机花费46")
		SLOT_COST46(96,"老虎机花费46"),
		@ReasonDesc("老虎机花费47")
		SLOT_COST47(97,"老虎机花费47"),
		@ReasonDesc("老虎机花费48")
		SLOT_COST48(98,"老虎机花费48"),
		@ReasonDesc("老虎机花费49")
		SLOT_COST49(99,"老虎机花费49"),
		@ReasonDesc("老虎机花费50")
		SLOT_COST50(100,"老虎机花费50"),
		@ReasonDesc("老虎机花费51")
		SLOT_COST51(101,"老虎机花费51"),
		@ReasonDesc("老虎机花费52")
		SLOT_COST52(102,"老虎机花费52"),
		@ReasonDesc("老虎机花费53")
		SLOT_COST53(103,"老虎机花费53"),
		@ReasonDesc("老虎机花费54")
		SLOT_COST54(104,"老虎机花费54"),
		@ReasonDesc("老虎机花费55")
		SLOT_COST55(105,"老虎机花费55"),
		@ReasonDesc("老虎机花费56")
		SLOT_COST56(106,"老虎机花费56"),
		@ReasonDesc("老虎机花费57")
		SLOT_COST57(107,"老虎机花费57"),
		@ReasonDesc("老虎机花费58")
		SLOT_COST58(108,"老虎机花费58"),
		@ReasonDesc("老虎机花费59")
		SLOT_COST59(109,"老虎机花费59"),
		@ReasonDesc("老虎机花费60")
		SLOT_COST60(110,"老虎机花费60"),
		
		
		
		
		@ReasonDesc("老虎机获得1")
		SLOT_GET1(116,"1老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得2")
		SLOT_GET2(117,"2老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得3")
		SLOT_GET3(118,"3老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得4")
		SLOT_GET4(119,"4老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得5")
		SLOT_GET5(120,"5老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得6")
		SLOT_GET6(121,"6老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得7")
		SLOT_GET7(122,"7老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得8")
		SLOT_GET8(123,"8老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得9")
		SLOT_GET9(124,"9老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得10")
		SLOT_GET10(125,"10老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得11")
		SLOT_GET11(126,"11老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得12")
		SLOT_GET12(127,"12老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得13")
		SLOT_GET13(128,"13老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得14")
		SLOT_GET14(129,"14老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得15")
		SLOT_GET15(130,"15老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得16")
		SLOT_GET16(131,"16老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得17")
		SLOT_GET17(132,"17老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得18")
		SLOT_GET18(133,"18老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得19")
		SLOT_GET19(134,"19老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得20")
		SLOT_GET20(135,"20老虎机{0},下注{1}"),
		
		@ReasonDesc("老虎机获得21")
		SLOT_GET21(136,"21老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得22")
		SLOT_GET22(137,"22老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得23")
		SLOT_GET23(138,"23老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得24")
		SLOT_GET24(139,"24老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得25")
		SLOT_GET25(140,"25老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得26")
		SLOT_GET26(141,"26老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得27")
		SLOT_GET27(142,"27老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得28")
		SLOT_GET28(143,"28老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得29")
		SLOT_GET29(144,"29老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得30")
		SLOT_GET30(145,"30老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得31")
		SLOT_GET31(146,"31老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得32")
		SLOT_GET32(147,"32老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得33")
		SLOT_GET33(148,"33老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得34")
		SLOT_GET34(149,"34老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得35")
		SLOT_GET35(150,"35老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得36")
		SLOT_GET36(151,"36老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得37")
		SLOT_GET37(152,"37老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得38")
		SLOT_GET38(153,"38老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得39")
		SLOT_GET39(154,"39老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得40")
		SLOT_GET40(155,"40老虎机{0},下注{1}"),
		
		
		@ReasonDesc("老虎机小游戏获得1")
		SLOT_SCATTER1(181,"1老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得2")
		SLOT_SCATTER2(182,"2老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得3")
		SLOT_SCATTER3(183,"3老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得4")
		SLOT_SCATTER4(184,"4老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得5")
		SLOT_SCATTER5(185,"5老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得6")
		SLOT_SCATTER6(186,"6老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得7")
		SLOT_SCATTER7(187,"7老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得8")
		SLOT_SCATTER8(188,"8老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得9")
		SLOT_SCATTER9(189,"9老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得10")
		SLOT_SCATTER10(190,"10老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得11")
		SLOT_SCATTER11(191,"11老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得12")
		SLOT_SCATTER12(192,"12老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得13")
		SLOT_SCATTER13(193,"13老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得14")
		SLOT_SCATTER14(194,"14老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得15")
		SLOT_SCATTER15(195,"15老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得16")
		SLOT_SCATTER16(196,"16老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得17")
		SLOT_SCATTER17(197,"17老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得18")
		SLOT_SCATTER18(198,"18老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得19")
		SLOT_SCATTER19(199,"19老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得20")
		SLOT_SCATTER20(200,"20老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得21")
		SLOT_SCATTER21(201,"21老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得22")
		SLOT_SCATTER22(202,"22老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得23")
		SLOT_SCATTER23(203,"23老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得24")
		SLOT_SCATTER24(204,"24老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得25")
		SLOT_SCATTER25(205,"25老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得26")
		SLOT_SCATTER26(206,"26老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得27")
		SLOT_SCATTER27(207,"27老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得28")
		SLOT_SCATTER28(208,"28老虎机小游戏{0},下注{1}"),
		
		@ReasonDesc("老虎机小游戏获得29")
		SLOT_SCATTER29(209,"29老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得30")
		SLOT_SCATTER30(210,"30老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得31")
		SLOT_SCATTER31(211,"31老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得32")
		SLOT_SCATTER32(212,"32老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得33")
		SLOT_SCATTER33(213,"33老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得34")
		SLOT_SCATTER34(214,"34老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得35")
		SLOT_SCATTER35(215,"35老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得36")
		SLOT_SCATTER36(216,"36老虎机小游戏{0},下注{1}"),
		
		@ReasonDesc("老虎机小游戏获得37")
		SLOT_SCATTER37(217,"37老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得38")
		SLOT_SCATTER38(218,"38老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得39")
		SLOT_SCATTER39(219,"39老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得40")
		SLOT_SCATTER40(220,"40老虎机小游戏{0},下注{1}"),
		
		
		
		
		
		@ReasonDesc("百家乐明灯复活")
		BACCART_REVIVE(500, "百家乐复活,房间id{0},当前连胜{1}，最小赢分量{2}"),
		@ReasonDesc("玩家创建角色初始筹码")
		INITIALCHIPS(501, "玩家创建角色初始筹码"),
		@ReasonDesc("玩家获得百家乐彩金")
		BACCARTMOSAICGOLD(502,"玩家获得百家乐彩金"),
		@ReasonDesc("玩家获得老虎机彩金")
		SLOTMOSAICGOLD(503,"玩家获得老虎机彩金"),
		@ReasonDesc("老虎机竞赛获得")
		SLOTSNGBONUS(504,"老虎机竞赛获得"),
		@ReasonDesc("获得成就")
		ACHIEVEMENT(505,"获得成就"),
		@ReasonDesc("转盘老虎机额外获得")
		ROTARY_TABLE(506,"转盘老虎机额外获得"),
		@ReasonDesc("观看视频获得")
		WATCHVIDEO(507,"观看视频获得"),
		
		
		
		
		
		@ReasonDesc("无双吹牛 经典模式 单独摇色子 扣除的钱")
		BAZOO_CLASSICAL_SINGLE_COST(508, "无双吹牛 经典模式 单独摇色子 扣除的钱,当前房间{0},当前扣除{1}"),
		@ReasonDesc("无双吹牛 经典模式 一个房间里别人摇色子给的钱")
		BAZOO_CLASSICAL_OTHER_SINGLE_GIVE(509, "无双吹牛 经典模式 一个房间里别人摇色子给的钱,当前房间{0},当前给{1}"),
		
		
		@ReasonDesc("无双吹牛 经典模式最终赢得的钱")
		BAZOO_CLASSICAL_WIN(510, "无双吹牛 经典模式最终赢得的钱,当前房间{0},当前赢得{1}"),
		@ReasonDesc("无双吹牛 经典模式最终 输的钱")
		BAZOO_CLASSICAL_COST(511, "无双吹牛 经典模式最终 输的钱,当前房间{0},当前输{1}"),
		
		@ReasonDesc("无双吹牛 经典模式 扣掉流水")
		BAZOO_CLASSICAL_LIUSHUI_COST(512, "无双吹牛 经典模式最终 输的钱,当前房间{0},扣掉流水{1}"),
		
		@ReasonDesc("无双吹牛 牛牛模式最终赢得的钱")
		BAZOO_COW_WIN(513, "无双吹牛 牛牛模式最终赢得的钱,当前房间{0},当前赢得{1}"),
		@ReasonDesc("无双吹牛 牛牛模式最终 输的钱")
		BAZOO_COW_COST(514, "无双吹牛 牛牛模式最终 输的钱,当前房间{0},当前输{1}"),
		
		
		
		@ReasonDesc("无双吹牛 梭哈模式最终赢得的钱")
		BAZOO_SHOWHAND_SINGLE_WIN(515, "无双吹牛 梭哈模式 单独 摇色子 赢得的钱,当前房间{0},当前赢得{1}"),
		@ReasonDesc("无双吹牛 经典模式最终 输的钱")
		BAZOO_SHOWHAND_SINGLE_COST(516, "无双吹牛  梭哈模式 单独 摇色子 输的钱,当前房间{0},当前输{1}"),
		
		
		@ReasonDesc("无双吹牛 签到 获得 的钱")
		BAZOO_BAZOO_SIGNIN(517, "无双吹牛  签到 获得 的钱"),
		
		
		@ReasonDesc("无双吹牛 签到 获得 的钱")
		BAZOO_BAZOO_TASK(518, "无双吹牛  签到 获得 的钱,任务ID{0}"),
		
		@ReasonDesc("无双吹牛 说话扣 的钱")
		BAZOO_BAZOO_COST_SPEAK(519, "无双吹牛  说话扣 的钱,用户ID{0}"),
		
		
		
		@ReasonDesc("无双吹牛 代理扣除的金币 操作ID_{0}")
		BAZOO_AGENT_COST_GOLD(520, "无双吹牛 代理扣除的金币 操作ID_{0}"),
		@ReasonDesc("无双吹牛 代理给予的金币 操作ID_{0}")
		BAZOO_AGENT_GIVE_GOLD(521, "无双吹牛 代理给予的金币 操作ID_{0}"),
		
		@ReasonDesc("机器人自动增加金币 操作ID_{0}")
		ROBOT_GIVE_GOLD(522, "机器人自动增加金币 操作ID_{0}"),
		@ReasonDesc("手动增加金币 操作ID_{0}")
		HAND_GIVE_GOLD(523, "手动增加金币 操作ID_{0}"),
		
		@ReasonDesc("发送礼物 扣掉金币")
		BAZOO_SEND_GIFT_COST_GOLD(524, "发送礼物 扣掉金币"),
		
		@ReasonDesc("领取红包 获得金币")
		BAZOO_RED_PACKAGE_GET_GOLD(525, "领取红包 获得金币"),
		@ReasonDesc("发送红包 扣掉金币")
		BAZOO_RED_PACKAGE_COST_GOLD(526, "发送红包 扣掉金币"),
		
		@ReasonDesc("无双吹牛 红黑单双模式 扣除金币")
		BAZOO_BLACK_WHITE_COST_GOLD(527, "无双吹牛 红黑单双模式 扣除金币"),
		@ReasonDesc("无双吹牛 红黑单双模式 给予金币")
		BAZOO_BLACK_WHITE_GIVE_GOLD(528, "无双吹牛 红黑单双模式 给予金币"),
		
		@ReasonDesc("无双吹牛 博趣平台 充值金币")
		BAZOO_BOQU_GIVE_GOLD(529, "无双吹牛 博趣平台 充值金币"),
		
		@ReasonDesc("无双吹牛 facebook 看广告 加金币")
		BAZOO_FACEBOOK_GIVE_GOLD(530, "无双吹牛 facebook 看广告 加金币"),
		
		
		
		
		
		
		
		
		
		
		
		
		@ReasonDesc("老虎机花费61")
		SLOT_COST61(5000,"老虎机花费61"),
		@ReasonDesc("老虎机花费62")
		SLOT_COST62(5001,"老虎机花费62"),
		@ReasonDesc("老虎机花费63")
		SLOT_COST63(5002,"老虎机花费63"),
		@ReasonDesc("老虎机花费64")
		SLOT_COST64(5003,"老虎机花费64"),
		@ReasonDesc("老虎机花费65")
		SLOT_COST65(5004,"老虎机花费65"),
		@ReasonDesc("老虎机花费66")
		SLOT_COST66(5005,"老虎机花费66"),
		@ReasonDesc("老虎机花费67")
		SLOT_COST67(5006,"老虎机花费67"),
		@ReasonDesc("老虎机花费68")
		SLOT_COST68(5007,"老虎机花费68"),
		@ReasonDesc("老虎机花费69")
		SLOT_COST69(5008,"老虎机花费69"),
		@ReasonDesc("老虎机花费70")
		SLOT_COST70(5009,"老虎机花费70"),
		@ReasonDesc("老虎机花费71")
		SLOT_COST71(5010,"老虎机花费71"),
		@ReasonDesc("老虎机花费72")
		SLOT_COST72(5011,"老虎机花费72"),
		@ReasonDesc("老虎机花费73")
		SLOT_COST73(5012,"老虎机花费73"),
		@ReasonDesc("老虎机花费74")
		SLOT_COST74(5013,"老虎机花费74"),
		@ReasonDesc("老虎机花费75")
		SLOT_COST75(5014,"老虎机花费75"),
		@ReasonDesc("老虎机花费76")
		SLOT_COST76(5015,"老虎机花费76"),
		@ReasonDesc("老虎机花费77")
		SLOT_COST77(5016,"老虎机花费77"),
		@ReasonDesc("老虎机花费78")
		SLOT_COST78(5017,"老虎机花费78"),
		@ReasonDesc("老虎机花费79")
		SLOT_COST79(5018,"老虎机花费79"),
		@ReasonDesc("老虎机花费80")
		SLOT_COST80(5019,"老虎机花费80"),
		@ReasonDesc("老虎机花费81")
		SLOT_COST81(5020,"老虎机花费81"),
		@ReasonDesc("老虎机花费82")
		SLOT_COST82(5021,"老虎机花费82"),
		@ReasonDesc("老虎机花费83")
		SLOT_COST83(5022,"老虎机花费83"),
		@ReasonDesc("老虎机花费84")
		SLOT_COST84(5023,"老虎机花费84"),
		@ReasonDesc("老虎机花费85")
		SLOT_COST85(5024,"老虎机花费85"),
		@ReasonDesc("老虎机花费86")
		SLOT_COST86(5025,"老虎机花费86"),
		@ReasonDesc("老虎机花费87")
		SLOT_COST87(5026,"老虎机花费87"),
		@ReasonDesc("老虎机花费88")
		SLOT_COST88(5027,"老虎机花费88"),
		@ReasonDesc("老虎机花费89")
		SLOT_COST89(5028,"老虎机花费89"),
		@ReasonDesc("老虎机花费90")
		SLOT_COST90(5029,"老虎机花费90"),
		@ReasonDesc("老虎机花费91")
		SLOT_COST91(5030,"老虎机花费91"),
		@ReasonDesc("老虎机花费92")
		SLOT_COST92(5031,"老虎机花费92"),
		@ReasonDesc("老虎机花费93")
		SLOT_COST93(5032,"老虎机花费93"),
		@ReasonDesc("老虎机花费94")
		SLOT_COST94(5033,"老虎机花费94"),
		@ReasonDesc("老虎机花费95")
		SLOT_COST95(5034,"老虎机花费95"),
		@ReasonDesc("老虎机花费96")
		SLOT_COST96(5035,"老虎机花费96"),
		@ReasonDesc("老虎机花费97")
		SLOT_COST97(5036,"老虎机花费97"),
		@ReasonDesc("老虎机花费98")
		SLOT_COST98(5037,"老虎机花费98"),
		@ReasonDesc("老虎机花费99")
		SLOT_COST99(5038,"老虎机花费99"),
		@ReasonDesc("老虎机花费100")
		SLOT_COST100(5039,"老虎机花费100"),
		@ReasonDesc("老虎机花费101")
		SLOT_COST101(5040,"老虎机花费101"),
		@ReasonDesc("老虎机花费102")
		SLOT_COST102(5041,"老虎机花费102"),
		@ReasonDesc("老虎机花费103")
		SLOT_COST103(5042,"老虎机花费103"),
		@ReasonDesc("老虎机花费104")
		SLOT_COST104(5043,"老虎机花费104"),
		@ReasonDesc("老虎机花费105")
		SLOT_COST105(5044,"老虎机花费105"),
		@ReasonDesc("老虎机花费106")
		SLOT_COST106(5045,"老虎机花费106"),
		@ReasonDesc("老虎机花费107")
		SLOT_COST107(5046,"老虎机花费107"),
		@ReasonDesc("老虎机花费108")
		SLOT_COST108(5047,"老虎机花费108"),
		@ReasonDesc("老虎机花费109")
		SLOT_COST109(5048,"老虎机花费109"),
		@ReasonDesc("老虎机花费110")
		SLOT_COST110(5049,"老虎机花费110"),
		@ReasonDesc("老虎机花费111")
		SLOT_COST111(5050,"老虎机花费111"),
		@ReasonDesc("老虎机花费112")
		SLOT_COST112(5051,"老虎机花费112"),
		@ReasonDesc("老虎机花费113")
		SLOT_COST113(5052,"老虎机花费113"),
		@ReasonDesc("老虎机花费114")
		SLOT_COST114(5053,"老虎机花费114"),
		@ReasonDesc("老虎机花费115")
		SLOT_COST115(5054,"老虎机花费115"),
		@ReasonDesc("老虎机花费116")
		SLOT_COST116(5055,"老虎机花费116"),
		@ReasonDesc("老虎机花费117")
		SLOT_COST117(5056,"老虎机花费117"),
		@ReasonDesc("老虎机花费118")
		SLOT_COST118(5057,"老虎机花费118"),
		@ReasonDesc("老虎机花费119")
		SLOT_COST119(5058,"老虎机花费119"),
		@ReasonDesc("老虎机花费120")
		SLOT_COST120(5059,"老虎机花费120"),
		@ReasonDesc("老虎机花费121")
		SLOT_COST121(5060,"老虎机花费121"),
		@ReasonDesc("老虎机花费122")
		SLOT_COST122(5061,"老虎机花费122"),
		@ReasonDesc("老虎机花费123")
		SLOT_COST123(5062,"老虎机花费123"),
		@ReasonDesc("老虎机花费124")
		SLOT_COST124(5063,"老虎机花费124"),
		@ReasonDesc("老虎机花费125")
		SLOT_COST125(5064,"老虎机花费125"),
		@ReasonDesc("老虎机花费126")
		SLOT_COST126(5065,"老虎机花费126"),
		@ReasonDesc("老虎机花费127")
		SLOT_COST127(5066,"老虎机花费127"),
		@ReasonDesc("老虎机花费128")
		SLOT_COST128(5067,"老虎机花费128"),
		
		
		@ReasonDesc("老虎机获得41")
		SLOT_GET41(5501,"41老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得42")
		SLOT_GET42(5502,"42老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得43")
		SLOT_GET43(5503,"43老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得44")
		SLOT_GET44(5504,"44老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得45")
		SLOT_GET45(5505,"45老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得46")
		SLOT_GET46(5506,"46老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得47")
		SLOT_GET47(5507,"47老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得48")
		SLOT_GET48(5508,"48老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得49")
		SLOT_GET49(5509,"49老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得50")
		SLOT_GET50(5510,"50老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得51")
		SLOT_GET51(5511,"51老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得52")
		SLOT_GET52(5512,"52老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得53")
		SLOT_GET53(5513,"53老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得54")
		SLOT_GET54(5514,"54老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得55")
		SLOT_GET55(5515,"55老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得56")
		SLOT_GET56(5516,"56老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得57")
		SLOT_GET57(5517,"57老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得58")
		SLOT_GET58(5518,"58老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得59")
		SLOT_GET59(5519,"59老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得60")
		SLOT_GET60(5520,"60老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得61")
		SLOT_GET61(5521,"61老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得62")
		SLOT_GET62(5522,"62老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得63")
		SLOT_GET63(5523,"63老虎机{0},下注{1}"),
		@ReasonDesc("64老虎机获得")
		SLOT_GET64(5524,"64老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得65")
		SLOT_GET65(5525,"65老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得66")
		SLOT_GET66(5526,"66老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得67")
		SLOT_GET67(5527,"67老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得68")
		SLOT_GET68(5528,"68老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得69")
		SLOT_GET69(5529,"69老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得70")
		SLOT_GET70(5530,"70老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得71")
		SLOT_GET71(5531,"71老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得72")
		SLOT_GET72(5532,"72老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得73")
		SLOT_GET73(5533,"73老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得74")
		SLOT_GET74(5534,"74老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得75")
		SLOT_GET75(5535,"75老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得76")
		SLOT_GET76(5536,"76老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得77")
		SLOT_GET77(5537,"77老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得78")
		SLOT_GET78(5538,"78老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得79")
		SLOT_GET79(5539,"79老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得80")
		SLOT_GET80(5540,"80老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得81")
		SLOT_GET81(5541,"81老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得82")
		SLOT_GET82(5542,"82老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得83")
		SLOT_GET83(5543,"83老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得84")
		SLOT_GET84(5544,"84老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得85")
		SLOT_GET85(5545,"85老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得86")
		SLOT_GET86(5546,"86老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得87")
		SLOT_GET87(5547,"87老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得88")
		SLOT_GET88(5548,"88老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得89")
		SLOT_GET89(5549,"89老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得90")
		SLOT_GET90(5550,"90老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得91")
		SLOT_GET91(5551,"91老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得92")
		SLOT_GET92(5552,"92老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得93")
		SLOT_GET93(5553,"93老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得94")
		SLOT_GET94(5554,"94老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得95")
		SLOT_GET95(5555,"95老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得96")
		SLOT_GET96(5556,"96老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得97")
		SLOT_GET97(5557,"97老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得98")
		SLOT_GET98(5558,"98老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得99")
		SLOT_GET99(5559,"99老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得100")
		SLOT_GET100(5560,"100老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得101")
		SLOT_GET101(5561,"101老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得102")
		SLOT_GET102(5562,"102老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得103")
		SLOT_GET103(5563,"103老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得104")
		SLOT_GET104(5564,"104老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得105")
		SLOT_GET105(5565,"105老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得106")
		SLOT_GET106(5566,"106老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得107")
		SLOT_GET107(5567,"107老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得108")
		SLOT_GET108(5568,"108老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得109")
		SLOT_GET109(5569,"109老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得110")
		SLOT_GET110(5570,"110老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得111")
		SLOT_GET111(5571,"111老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得112")
		SLOT_GET112(5572,"112老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得113")
		SLOT_GET113(5573,"113老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得114")
		SLOT_GET114(5574,"114老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得115")
		SLOT_GET115(5575,"115老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得116")
		SLOT_GET116(5576,"116老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得117")
		SLOT_GET117(5577,"117老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得118")
		SLOT_GET118(5578,"118老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得119")
		SLOT_GET119(5579,"119老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得120")
		SLOT_GET120(5580,"120老虎机{0},下注{1}"),
		
		@ReasonDesc("老虎机获得121")
		SLOT_GET121(5581,"121老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得122")
		SLOT_GET122(5582,"122老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得123")
		SLOT_GET123(5583,"123老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得124")
		SLOT_GET124(5584,"124老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得125")
		SLOT_GET125(5585,"125老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得126")
		SLOT_GET126(5586,"126老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得127")
		SLOT_GET127(5587,"127老虎机{0},下注{1}"),
		@ReasonDesc("老虎机获得128")
		SLOT_GET128(5588,"128老虎机{0},下注{1}"),
		
		
		
		@ReasonDesc("老虎机小游戏获得41")
		SLOT_SCATTER41(6001,"41老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得42")
		SLOT_SCATTER42(6002,"42老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得43")
		SLOT_SCATTER43(6003,"43老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得44")
		SLOT_SCATTER44(6004,"44老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得45")
		SLOT_SCATTER45(6005,"45老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得46")
		SLOT_SCATTER46(6006,"46老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得47")
		SLOT_SCATTER47(6007,"47老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得48")
		SLOT_SCATTER48(6008,"48老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得49")
		SLOT_SCATTER49(6009,"49老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得50")
		SLOT_SCATTER50(6010,"50老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得51")
		SLOT_SCATTER51(6011,"51老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得52")
		SLOT_SCATTER52(6012,"52老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得53")
		SLOT_SCATTER53(6013,"53老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得54")
		SLOT_SCATTER54(6014,"54老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得55")
		SLOT_SCATTER55(6015,"55老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得56")
		SLOT_SCATTER56(6016,"56老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得57")
		SLOT_SCATTER57(6017,"57老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得58")
		SLOT_SCATTER58(6018,"58老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得59")
		SLOT_SCATTER59(6019,"59老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得60")
		SLOT_SCATTER60(6020,"60老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得61")
		SLOT_SCATTER61(6021,"61老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得62")
		SLOT_SCATTER62(6022,"62老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得63")
		SLOT_SCATTER63(6023,"63老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得64")
		SLOT_SCATTER64(6024,"64老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得65")
		SLOT_SCATTER65(6025,"65老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得66")
		SLOT_SCATTER66(6026,"66老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得67")
		SLOT_SCATTER67(6027,"67老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得68")
		SLOT_SCATTER68(6028,"68老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得69")
		SLOT_SCATTER69(6029,"69老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得70")
		SLOT_SCATTER70(6030,"70老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得71")
		SLOT_SCATTER71(6031,"71老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得72")
		SLOT_SCATTER72(6032,"72老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得73")
		SLOT_SCATTER73(6033,"73老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得74")
		SLOT_SCATTER74(6034,"74老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得75")
		SLOT_SCATTER75(6035,"75老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得76")
		SLOT_SCATTER76(6036,"76老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得78")
		SLOT_SCATTER77(6037,"77老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得78")
		SLOT_SCATTER78(6038,"78老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得79")
		SLOT_SCATTER79(6039,"79老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得80")
		SLOT_SCATTER80(6040,"80老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得18")
		SLOT_SCATTER81(6041,"81老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得82")
		SLOT_SCATTER82(6042,"82老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得83")
		SLOT_SCATTER83(6043,"83老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得84")
		SLOT_SCATTER84(6044,"84老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得85")
		SLOT_SCATTER85(6045,"85老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得86")
		SLOT_SCATTER86(6046,"86老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得87")
		SLOT_SCATTER87(6047,"87老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得88")
		SLOT_SCATTER88(6048,"88老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得89")
		SLOT_SCATTER89(6049,"89老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得90")
		SLOT_SCATTER90(6050,"90老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得91")
		SLOT_SCATTER91(6051,"91老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得92")
		SLOT_SCATTER92(6052,"92老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得93")
		SLOT_SCATTER93(6053,"93老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得94")
		SLOT_SCATTER94(6054,"94老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得95")
		SLOT_SCATTER95(6055,"95老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得96")
		SLOT_SCATTER96(6056,"96老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得97")
		SLOT_SCATTER97(6057,"97老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得98")
		SLOT_SCATTER98(6058,"98老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得99")
		SLOT_SCATTER99(6059,"99老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得100")
		SLOT_SCATTER100(6060,"100老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得101")
		SLOT_SCATTER101(6061,"101老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得102")
		SLOT_SCATTER102(6062,"102老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得103")
		SLOT_SCATTER103(6063,"103老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得104")
		SLOT_SCATTER104(6064,"104老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得105")
		SLOT_SCATTER105(6065,"105老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得106")
		SLOT_SCATTER106(6066,"106老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得107")
		SLOT_SCATTER107(6067,"107老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得108")
		SLOT_SCATTER108(6068,"108老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得109")
		SLOT_SCATTER109(6069,"109老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得110")
		SLOT_SCATTER110(6070,"110老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得111")
		SLOT_SCATTER111(6071,"111老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得112")
		SLOT_SCATTER112(6072,"112老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得113")
		SLOT_SCATTER113(6073,"113老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得114")
		SLOT_SCATTER114(6074,"114老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得115")
		SLOT_SCATTER115(6075,"115老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得116")
		SLOT_SCATTER116(6076,"116老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得117")
		SLOT_SCATTER117(6077,"117老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得118")
		SLOT_SCATTER118(6078,"118老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得119")
		SLOT_SCATTER119(6079,"119老虎机小游戏{0},下注{1}"),
		@ReasonDesc("老虎机小游戏获得120")
		SLOT_SCATTER120(6080,"120老虎机小游戏{0},下注{1}"),
		
		
		@ReasonDesc("收集系统抽奖获得")
		COLLECT(10000,"收集系统抽奖获得"),
		

		@ReasonDesc("存钱罐")
		Treasury_type1(6081,"用户购买的存钱罐的钱"),
		@ReasonDesc("创建俱乐部")
		CLUB_CREATE(6082,"创建俱乐部"),
		@ReasonDesc("俱乐部捐献")
		CLUB_DONATE(6083,"俱乐部捐献"),
		@ReasonDesc("俱乐部礼包")
		CLUB_GIFT(6084,"俱乐部礼包"),
		;
		
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private GoldLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
		
		private static final List<GoldLogReason> indexes = IndexedEnumUtil.toIndexes(GoldLogReason.values());
		
		public static GoldLogReason indexOf(final int index)
		{
			return EnumUtil.valueOf(indexes, index);
		}

		@Override
		public int getIndex() {
			return reason;
		}

	}
	
	@LogDesc(desc = "数据总览")
	public enum DataOverviewLogReason implements ILogReason{
		@ReasonDesc("每日登录的用户")
		DataOverview_type1(1,"每日登录的用户"),
		@ReasonDesc("每日新注册用户")
		DataOverview_type2(2,"每日新注册用户"),
		
		@ReasonDesc("当天收入美金数")
		DataOverview_type3(3,"当天收入美金数"),
		@ReasonDesc("当天的付费人数没有去重")
		DataOverview_type4(4,"当天的付费人数没有去重"),
		@ReasonDesc("成功的订单数量")
		DataOverview_type5(5,"成功的订单数量"),
		@ReasonDesc("新用户首付")
		DataOverview_type6(6,"新用户首付"),
		@ReasonDesc("老用户首付")
		DataOverview_type7(7,"老用户首付"),
		
		@ReasonDesc("老虎机spin的次数")
		DataOverview_type8(8,"老虎机spin的次数"),
		@ReasonDesc("老虎机中奖的次数")
		DataOverview_type9(9,"老虎机中奖的次数"),
		@ReasonDesc("老虎机中小箱子的次数")
		DataOverview_type10(10,"老虎机中小箱子的次数"),
		@ReasonDesc("连线中奖")
		DataOverview_type11(11,"连线中奖"),
		@ReasonDesc("投入")
		DataOverview_type12(12,"投入"),
		
		
		
		;
		
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DataOverviewLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
		
	}
	
	@LogDesc(desc = "充值日志")
	public enum NewRechargeLogReason implements ILogReason{
		
		@ReasonDesc("老用户首付")
		RECHARGE1(1,"老用户首付"),
		
		@ReasonDesc("新人首付")
		RECHARGE2(2,"新人首付"),
		
		@ReasonDesc("都不是")
		RECHARGE3(3,"都不是"),
		;
          
		/** 原因序号  1 老用户首付 2 新人首付  3 都不是*/
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
	
		
		private NewRechargeLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
		
		
	}
	
	@LogDesc(desc = "每日登录")
	public enum PlayerLoginLogReason implements ILogReason{
		@ReasonDesc("每日登录的用户")
		PLAYERLOGINLOG1(1,"每日登录的用户"),
		
		@ReasonDesc("每日新注册用户")
		PLAYERLOGINLOG2(2,"每日新注册用户")
		;

		/** 原因序号  1 老用户首付 2 新人首付  3 都不是*/
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		
		
		private PlayerLoginLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
		
	}
	
	@LogDesc(desc = "角色在线统计")
	public enum PlayerOnleLogReason implements ILogReason {
		@ReasonDesc("每小时统计人数")
		PLAYERONLEHOURS(1,"每小时统计人数"),
		;

		/** 原因序号  1 老用户首付 2 新人首付  3 都不是*/
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private PlayerOnleLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
		
	}
	
	@LogDesc(desc = "游戏留存统计")
	public enum PlayerKeepLogReason implements ILogReason {
		
		@ReasonDesc("新用户注册")
		PlayerKeep1(1,"新用户注册"),
		
		@ReasonDesc("老用户登陆")
		PlayerKeep2(2,"老用户登陆")
		;

		/** 原因序号  1 新用户注册  2 老用户登陆*/
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private PlayerKeepLogReason(int reason,String reasonText){
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
		
	}
	
	
	
	@LogDesc(desc = "平台币改变")
	public enum DiamondLogReason implements ILogReason 
	{
		/** 购买vip*/
		@ReasonDesc("购买vip")
		BUY_VIP(1, "购买vip,消耗钻石={0}"),
		@ReasonDesc("充值")
		RECHARGE(2, "充值档次={0},获得筹码={1}"),
		@ReasonDesc("每周签到")
		WEEK_SIGN_IN(5, "每周签到"),
		@ReasonDesc("邮件奖励")
		MAIL_REWARD(6, "邮件奖励"),
		@ReasonDesc("月卡初始奖励")
		MONTH_CARD_INIT(7, "月卡初始奖励"),
		@ReasonDesc("月卡每天奖励")
		MONTH_CARD_DAILY(8, "月卡每天奖励"),
		@ReasonDesc("周卡初始奖励")
		WEEK_CARD_INIT(9, "周卡初始奖励"),
		@ReasonDesc("周卡每天奖励")
		WEEK_CARD_DAILY(10, "周卡每天奖励"),
		@ReasonDesc("vip初始奖励")
		VIP_INIT(11, "vip初始奖励"),
		@ReasonDesc("vip每天奖励")
		VIP_DAILY(12, "vip每天奖励"),
		@ReasonDesc("vip剩余奖励")
		VIP_REMAIN(13, "vip剩余"),
		@ReasonDesc("活动赠送筹码")
		ACTIVITY(15, "活动赠送钻石,活动Id={0},活动档次id={1}"),
		@ReasonDesc("购买商品")
		BUY_ITEM(16, "购买商品,商品Id={0}|数量={1}"),
		@ReasonDesc("每日任务")
		DAILY_TASK(17, "每日任务"),
		@ReasonDesc("在线奖励")
		ONLINE_REWARD(18, "在线奖励"),
		@ReasonDesc("好友礼物")
		FRIEND_GIFT(19, "好友礼物"),
		@ReasonDesc("百家乐复活")
		BACCART_REVIVE(20, "百家乐复活,房间id{0},当前连胜{1}，最小赢分量{2}"),
		@ReasonDesc("幸运轮盘抽取")
		LUCKY_SPIN_COST(21, "幸运轮盘抽取"),
		@ReasonDesc("幸运轮盘获得")
		LUCKY_SPIN_GET(22, "幸运轮盘获得"),
		@ReasonDesc("幸运匹配花费")
		LUCKY_MATCH_COST(23, "幸运匹配花费"),
		@ReasonDesc("幸运匹配获得")
		LUCKY_MATCH_GET(24, "幸运匹配获得"),
		@ReasonDesc("韩国sb推广")
		KOREAN_SB(25, "韩国sb推广"),
		@ReasonDesc("fb邀请奖励")
		FB_INVITE(26, "fb邀请奖励"),
		@ReasonDesc("fb奖励")
		FB_REWARD(27, "fb奖励"),
		@ReasonDesc("兑换筹码奖励")
		CONVERSION(28, "兑换筹码奖励"),
		@ReasonDesc("观看视频获得")
		WATCHVIDEO(29, "观看视频获得"),
		;
		
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private DiamondLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	
	@LogDesc(desc = "魅力值改变")
	public enum CharmLogReason implements ILogReason 
	{
		/** 接收赠送道具*/
		@ReasonDesc("接收赠送道具")
		ACCEPT_INTERACTIVE_ITEM(1, "赠送道具id={0}"),
		@ReasonDesc("每周签到")
		WEEK_SIGN_IN(5, "每周签到"),
		@ReasonDesc("邮件奖励")
		MAIL_REWARD(6, "邮件奖励"),
		@ReasonDesc("月卡初始奖励")
		MONTH_CARD_INIT(7, "月卡初始奖励"),
		@ReasonDesc("月卡每天奖励")
		MONTH_CARD_DAILY(8, "月卡每天奖励"),
		@ReasonDesc("周卡初始奖励")
		WEEK_CARD_INIT(9, "周卡初始奖励"),
		@ReasonDesc("周卡每天奖励")
		WEEK_CARD_DAILY(10, "周卡每天奖励"),
		@ReasonDesc("vip初始奖励")
		VIP_INIT(11, "vip初始奖励"),
		@ReasonDesc("vip每天奖励")
		VIP_DAILY(12, "vip每天奖励"),
		@ReasonDesc("vip剩余奖励")
		VIP_REMAIN(13, "vip剩余"),
		@ReasonDesc("活动赠送筹码")
		ACTIVITY(15, "活动赠送魅力,活动Id={0},活动档次id={1}"),
		@ReasonDesc("每日任务")
		DAILY_TASK(16, "每日任务"),
		@ReasonDesc("在线奖励")
		ONLINE_REWARD(17, "在线奖励"),
		@ReasonDesc("好友礼物")
		FRIEND_GIFT(28, "好友礼物"),
		@ReasonDesc("幸运轮盘")
		LUCKYSPIN(29, "幸运轮盘"),
		@ReasonDesc("幸运匹配获得")
		LUCKY_MATCH_GET(30, "幸运匹配获得"),
		@ReasonDesc("fb邀请奖励")
		FB_INVITE(31, "fb邀请奖励"),
		@ReasonDesc("fb奖励")
		FB_REWARD(32, "fb奖励"),
		@ReasonDesc("兑换筹码奖励")
		CONVERSION(33, "兑换筹码奖励"),
		@ReasonDesc("观看视频获得")
		WATCHVIDEO(34, "观看视频获得"),
		@ReasonDesc("魅力值兑换物品")
		COLLECT(35, "魅力值兑换物品"),
		;
		
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private CharmLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	
	/**
	 * 聊天日志产生原因
	 */
	@LogDesc(desc = "聊天")
	public enum ChatLogReason implements ILogReason {
		/** 包含不良信息 */
		@ReasonDesc("包含不良信息")
		REASON_CHAT_DIRTY_WORD(0, ""),
		/** 普通聊天信息 */
		@ReasonDesc("普通聊天信息")
		REASON_CHAT_COMMON(1, ""), ;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ChatLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 周卡日志
	 */
	@LogDesc(desc = "周卡")
	public enum WeekcardLogReason implements ILogReason {
		/** 周卡领取*/
		@ReasonDesc("周卡领取")
		WEEK_CARD_GET(0, "周卡领取"),
		@ReasonDesc("周卡购买")
		WEEK_CARD_BUY(1, "周卡购买"),;


		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private WeekcardLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 月卡日志
	 */
	@LogDesc(desc = "月卡")
	public enum MonthcardLogReason implements ILogReason {
		/** 月卡领取*/
		@ReasonDesc("月卡领取")
		MONTH_CARD_GET(0, "月卡领取"),
		@ReasonDesc("月卡购买")
		MONTH_CARD_BUY(1, "月卡购买"),;


		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private MonthcardLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 月卡日志
	 */
	@LogDesc(desc = "签到")
	public enum SignInLogReason implements ILogReason {
		/**签到*/
		@ReasonDesc("签到领取")
		SIGN_IN (0, "签到领取"),
	;


		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private SignInLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 充值
	 */
	@LogDesc(desc = "充值日志")
	public enum RechargeLogReason implements ILogReason {
		/**获取订单号*/
		@ReasonDesc("获取订单号")
		REQUEST_ORDER (0, "获取订单号"),
		@ReasonDesc("订单号")
		VALIDATE_ORDER (1, "验证订单号")
	;


		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private RechargeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 在线奖励日志
	 */
	@LogDesc(desc = "在线奖励日志")
	public enum OnlineTimeRewardLogReason implements ILogReason {
		/**获取订单号*/
		@ReasonDesc("领取在线奖励")
		GET_ONLINE_REWARD (0, "领取在线奖励"),
		
	;


		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private OnlineTimeRewardLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 玩家基础日志原因
	 */
	@LogDesc(desc = "玩家基础日志")
	public enum BasicPlayerLogReason implements ILogReason {
		/** 正常登录 */
		@ReasonDesc("正常登录")
		REASON_NORMAL_LOGIN(0, "IMEI={0}|设备机型={1}|操作系统版本号={2}|客户端版本={3}"),
		@ReasonDesc("正常退出")
		REASON_NORMAL_LOGOUT(2, ""),
		@ReasonDesc("home键退出")
		TEMPORARY_EXIT_LOGOUT(3, ""),
		@ReasonDesc("服务器处理出错退出")
		SERVER_ERROR(4, ""),
		@ReasonDesc("服务器人数已满退出")
		SERVER_IS_FULL(5, ""),
		@ReasonDesc("登陆认证失败退出")
		PLAYER_AUTH_LOGIN_INVALID(6, ""),
		@ReasonDesc("断网退出")
		OFF_NETWORK(7, ""),
		@ReasonDesc("主动退出")
		INITIATIVE(8, ""),
		@ReasonDesc("10分钟后踢掉玩家")
		LASTNETTIMEOUT(9, ""),
		/** 服务器停机,保存玩家数据 */
		@ReasonDesc("服务器停机,保存玩家数据")
		REASON_LOGOUT_SERVER_SHUTDOWN(10, ""),
		/** GM踢人 */
		@ReasonDesc("GM踢人")
		GM_KICK(13,""),
		/** 自己踢掉自己 */
		@ReasonDesc("自己踢掉自己")
		MULTI_LOGIN(15,""),
		@ReasonDesc("未知退出")
		LOGOUT(16, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private BasicPlayerLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	
	
	/**
	 * 每日任务日志
	 */
	@LogDesc(desc = "每日任务日志")
	public enum DailyTaskLogReason implements ILogReason {
		
		@ReasonDesc("完成进度")
		FINISH_DAILY_TASK(0, "完成进度"),
		@ReasonDesc("领取奖励")
		GET_DAILY_TASK_REWARD(1, "领取奖励"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private DailyTaskLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 好友日志
	 */
	@LogDesc(desc = "好友日志")
	public enum FriendLogReason implements ILogReason {
		
		@ReasonDesc("发送好友请求")
		FRIEND_REQUEST(1, "发送好友请求"),
		@ReasonDesc("验证好友")
		FRIEND_REQUEST_ACCEPT(2, "验证好友"),
		@ReasonDesc("拒绝好友")
		FRIEND_REQUEST_REJECT(3, "拒绝好友"),
		@ReasonDesc("删除好友")
		FRIEND_REQUEST_DELETE(4, "删除好友"),
		@ReasonDesc("发送好友礼物")
		FRIEND_GIFT(5, "好友礼物"),
		@ReasonDesc("好友礼物领取")
		FRIEND_GIFT_GET(6, "好友礼物领取"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private FriendLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 重命名日志
	 */
	@LogDesc(desc = "重命名日志")
	public enum RenameLogReason implements ILogReason {
		
		@ReasonDesc("重命名")
		RENAME(1, "发送好友请求"),
		
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private RenameLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 德州房间日志
	 */
	@LogDesc(desc = "德州房间日志")
	public enum TexasRoomLogReason implements ILogReason {
		
		@ReasonDesc("创建房间")
		CREATE(1, "创建房间"),
		@ReasonDesc("摧毁房间")
		DESTROY(2,"摧毁房间,玩家信息{0}"),
		@ReasonDesc("开始游戏")
		BEGIN(3,"开始游戏,游戏局号{0},玩家信息{1}"),
		@ReasonDesc("结束游戏")
		OVER(3,"结束游戏,游戏局号{0}"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private TexasRoomLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 百家乐房间日志
	 */
	@LogDesc(desc = "百家乐房间日志")
	public enum BaccaratRoomLogReason implements ILogReason {
		
		@ReasonDesc("百家乐房间快照")
		SNAP(1, "百家乐房间快照{0}"),
	
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private BaccaratRoomLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	/**
	 * 百家乐房间日志
	 */
	@LogDesc(desc = "百家乐房间日志")
	public enum SlotRoomLogReason implements ILogReason {
		
		@ReasonDesc("老虎机快照")
		SLOTROOMKUAIZHAO(1, "老虎机快照{0}"),
		
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private SlotRoomLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 幸运转盘
	 */
	@LogDesc(desc = "幸运转盘日志")
	public enum LuckySpinLogReason implements ILogReason {
		
		@ReasonDesc("幸运转盘")
		SPIN(1, "幸运转盘"),
		@ReasonDesc("幸运匹配")
		MATCH(2, "幸运匹配"),
		
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private LuckySpinLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 老虎机日志
	 */
	@LogDesc(desc = "老虎机日志")
	public enum SlotLogReason implements ILogReason {
		
		@ReasonDesc("拉霸")
		SLOT(1, "拉霸"),
	
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private SlotLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 老虎机 进入退出时间日志
	 */
	@LogDesc(desc = "进入退出时间日志")
	public enum InOutTimeLogReason implements ILogReason {
		
		@ReasonDesc("进入退出时间")
		INOUTTIME(1, "进入退出时间"),
		
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private InOutTimeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	/**
	 * 老虎机 进入退出时间日志
	 */
	@LogDesc(desc = "进入退出时间日志")
	public enum JackpotLogReason implements ILogReason {
		
		@ReasonDesc("彩金")
		JACK(1, "彩金"),
		
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private JackpotLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	/**
	 * 老虎机 竞赛
	 */
	@LogDesc(desc = "老虎机竞赛信息")
	public enum TournamentLogReason implements ILogReason {
		
		@ReasonDesc("竞赛")
		TOURNAMENT(1, "竞赛"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private TournamentLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 世界boss
	 */
	@LogDesc(desc = "世界boss")
	public enum WorldBossLogReason implements ILogReason {
		
		@ReasonDesc("世界boss")
		WorldBoss(1, "世界boss"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private WorldBossLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 老虎机 主转中 细节的时间统计
	 */
	@LogDesc(desc = "老虎机 主转中 细节的时间统计")
	public enum StatisticsTimeLogReason implements ILogReason {
		
		@ReasonDesc("老虎机 主转中 细节的时间统计")
		StatisticsTime(1, "老虎机 主转中 细节的时间统计"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private StatisticsTimeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 
	 * 无双吹牛
	 * *******************************************************************************************************************
	 * 无双吹牛
	 * 
	 */
	
	/**
	 * 无双吹牛 房间信息
	 */
	@LogDesc(desc = "无双吹牛 房间信息 创建-进入-退出")
	public enum DiceClassicalRoomLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛 进入房间")
		create(0, "创建房间"),
		in(1, "进入房间"),
		out(2, "退出房间"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceClassicalRoomLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	
	
	/**
	 * 无双吹牛 竞猜信息
	 */
	@LogDesc(desc = "无双吹牛 竞猜")
	public enum DiceClassicalGuessLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛 竞猜")
		guess(0, "竞猜"),
		rob(1, "抢开"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceClassicalGuessLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	
	/**
	 * 无双吹牛 叫号
	 */
	@LogDesc(desc = "无双吹牛 叫号")
	public enum DiceClassicalCallNumLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛 叫号")
		call(0, "叫号"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceClassicalCallNumLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 无双吹牛 金币走向
	 */
	@LogDesc(desc = "无双吹牛 金币走向")
	public enum DiceClassicalBazooGoldLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛 金币走向")
		addGoldChange(0, "增加金币"),
		subtractGoldChange(1, "减少金币"),
		pumpGoldChange(2, "抽水"),
		againSwingGoldChange(3, "重摇"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceClassicalBazooGoldLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	/**
	 * 无双吹牛 牛牛模式 色子值
	 */
	@LogDesc(desc = "无双吹牛 牛牛模式 色子值")
	public enum DiceCowLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛 牛牛模式 色子值")
		curDiceValues(0, "当前色子值"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceCowLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	/**
	 * 无双吹牛 梭哈模式 色子值
	 */
	@LogDesc(desc = "无双吹牛 梭哈模式 色子值")
	public enum DiceShowHandLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛 梭哈模式 色子值")
		curDiceValues(0, "当前色子值"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceShowHandLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	/**
	 * 无双吹牛 签到  色子值
	 */
	@LogDesc(desc = "无双吹牛 梭哈模式 色子值")
	public enum DiceSignInLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛签到 色子值")
		curDiceValues(0, "当前色子值"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceSignInLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	/**
	 * 无双吹牛  红黑单双
	 */
	@LogDesc(desc = "无双吹牛 红黑单双 色子值")
	public enum DiceBlackWhiteLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛 红黑单双 色子值")
		curDiceValues(0, "当前色子值"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceBlackWhiteLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 无双吹牛  统计每一把输赢（所有人包括机器人）
	 */
	@LogDesc(desc = "无双吹牛 统计每一把输赢")
	public enum DiceStatisticsWinLostLogReason implements ILogReason {
		
		@ReasonDesc("无双吹牛 统计每一把赢")
		win(1, "赢"),
		@ReasonDesc("无双吹牛 统计每一把输")
		lost(0, "输"),
		@ReasonDesc("无双吹牛 统计每一把输赢")
		BAZOO_WIN_LOST_GOLD(2, "无双吹牛 统计每一把输赢"),
		;
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;
		
		private DiceStatisticsWinLostLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}
		
		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	
	
	
	
}