package com.core.schedule;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 任务枚举类型
 * @author Thinker
 *
 */
public enum LLScheduleEnum implements IndexedEnum
{

	SCHEDULE_SYSTEM_PERFORMANCE(10,"系统性能检测"),

	SCHEDULE_STATISTICS_USERINFO(18,"定时检测用户和活跃功能统计"),
	SCHEDULE_STATISTICS_RETENTIONINFO(19,"定时检测留存功能统计"),
	SCHEDULE_STATISTICS_GUIDEUSERINFO(20,"定时检测新手引导功能统计"),
	SCHEDULE_ONLINEPLAYER(21,"定时检测在线用户数"),
	SCHEDULE_STATISTICS_ACTIVEUSER(22,"定时检测日，7日，30日活跃用户"),
	SCHEDULE_STATISTICS_FLOWUSER(23,"定时检测激活下载流程用户"),
	SCHEDULE_STATISTICS_ACUPCU(24,"定时检测acu和Pcu用户"),
	SCHEDULE_STATISTICS_FLOWNEWUSER(25,"定时检测新登和新增的设备和用户数"),
	SCHEDULE_STATISTICS_MAINTASK(26,"定时检测主线任务的用户访问情况"),
	SCHEDULE_STATISTICS_AllTASK(27,"定时检测全部任务的统计数据"),
	SCHEDULE_STATISTICS_SNAPINFO(28,"定时检测用户快照的统计数据"),
	SCHEDULE_OFFLINETEAM_REWARD(29,"启动离线组队奖励结算"),
	SCHEDULE_STATISTICS_CCUINFO(30,"定时检测服务器上下行数据量和次数"),
	SCHEDULE_STATISTICS_NETTIMEOUT(32,"定时检测客户端连接超时"),

	SCHEDULE_LAST_NET_TIME(9999,"定时检测网络时间"),
	SCHEDULE_HEART_BEAT_SERVER(10000,"定时ping"),
	SCHEDULE_NOTICE(10001,"跑马灯"),
	SCHEDULE_BACCART_ROOM(10002,"百家乐房间"),
	RANK_NEW(10003,"排行榜奖励结算"),
	SLOT_SNG_RANK(10004,"老虎机排名结算"),
	SLOT_SNG_RANK_START(10005,"老虎机竞赛等待时间"),
	ONLINEPLAYERLOG(10006,"在线人数统计"),
	
	
	REGULARTIME(20000,"周 月 特惠充值活动"),
	
	
	
	
	WORLD_BOSS_START(200000,"世界boss的开始"),
	WORLD_BOSS_END(200001,"世界boss的结束时间"),
	WORLD_BOSS_SKILL_START(200002,"世界boss的 boss 开启后，多长时间 开启技能"),
	WORLD_BOSS_CONTINUE(200003,"世界boss持续时间"),
	WORLD_BOSS_AIR_ROLL_END(200004,"世界boss的 boss 在 skillstart 技能开启后 多长时间  回一次血 的结束时间"),
	WORLD_BOSS_NOTICE_START(200005,"世界boss的 boss 通知用户的跑马灯 开始"),
	WORLD_BOSS_NOTICE_BEFORE(200006,"世界boss的 boss 赛前 30分钟 通知"),
	
	
	CLASSICAL_BEGIN_THE_GAME(200007,"经典模式  开始游戏"),
	CLASSICAL_END_COUNT(200008,"经典模式 去结算之前"),
	CLASSICAL_END_TO_START(200009,"经典模式 本轮结束到 下一轮 开始"),
	CLASSICAL_GUESS_BEFORE(2000010,"经典模式 竞猜之前"),
	CLASSICAL_WHO_SHOULD_SWING(200011,"经典模式  轮到谁摇色子"),
	
	COW_END_COUNT_ONCE(200012,"牛牛模式 去结算之前等待"),
	COW_END_TO_START(200013,"牛牛模式 结算之后等待"),
	
	SHOW_HAND_WHO_TURN_SWING(200014,"梭哈模式 该轮到谁摇色子了"),
	SHOW_HAND_END_TO_BEGIN(200015,"梭哈模式 结束到开始"),
	
	BAZOO_RANK(200016,"无双吹牛 排行榜"),
	
	
	BLACK_WHITE_WHO_SHOULD_SWING(200017,"黑白单双模式  轮到谁摇色子"),
	BLACK_WHITE_SWING_LEFT(200018,"黑白单双模式  摇剩余色子"),
	BLACK_WHITE_END_TO_BEGIN(200019,"黑白单双模式  进入下一局"),
	;
	
	/** 枚举的索引 */
	public final int index;
	/** 名称的key */
	private final String nameKey;
	
	/** 按索引顺序存放的枚举数组 */
	private static final List<LLScheduleEnum> indexes = IndexedEnum.IndexedEnumUtil.toIndexes(LLScheduleEnum.values());

	
	private LLScheduleEnum(int index,String nameKey)
	{
		this.index = index;
		this.nameKey = nameKey;
	}
	
	/**
	 * 获取索引
	 */
	@Override
	public int getIndex()
	{
		return index;
	}
	
	/**
	 * 取得名称key
	 * 
	 * @return
	 */
	public String getNameKey() 
	{
		return this.nameKey;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 *            枚举的索引
	 * @return
	 */
	public static LLScheduleEnum valueOf(final int index) 
	{
		return EnumUtil.valueOf(indexes, index);
	}
}
