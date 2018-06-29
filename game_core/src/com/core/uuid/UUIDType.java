package com.core.uuid;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;



/**
 * 游戏中的UUID类型
 * @author Thinker
 *
 */
public enum UUIDType implements IndexedEnum 
{
	/** 玩家角色的UUID */
	HUMAN(0),
    /** 退出原因ID */
    EXITREASONID(1),
    /** 玩家德州扑克的UUID */
	HUMANTEXASID(2),
	/** 玩家背包的UUID */
	HUMANBAGID(3),
	/** 玩家月卡的UUID */
	HUMANMONTHCARDID(4),
	/** 玩家周卡的UUID */
	HUMANWEEKCARDID(5),
	/** 玩家邮件的UUID */
	HUMANMAILID(6),
	/** 玩家好友的UUID */
	HUMANFRIENDID(7),
	/** 玩家好友请求的UUID */
	HUMANFRIENDREQUESTID(8),
	/** 玩家签到的UUID */
	HUMANWEEKSIGNINID(9),
	/** 玩家vip的UUID */
	HUMANVIPID(10),
	/** 订单的UUID */
	HUMANRECHARGEID(11),
	/** 任务的UUID */
	HUMANTASKID(12),
	/** 活动的UUID */
	HUMANACTIVITYID(13),
	/** 德州房间的UUID */
	HUMANTEXASROOMID(14),
	/** 德州局号的UUID */
	HUMANTEXASROOMGAMEID(15),
	/** 道具的UUID */
	HUMANITEMID(16),
	  /** 玩家德州sng扑克的UUID */
	HUMANTEXASSNGID(17),
	/**玩家补偿UUID*/
	HUMANCOMPENSATIONID(18),
	/**玩家miscUUID*/
	HUMANMISCID(19),
	/**玩家礼物UUID*/
	HUMANFRIENDGIFTID(20),
    /**机器人UUID*/
	ROBOTCOMPLEMENTID(21),	/**玩家百家乐UUID*/
	HUMANBACCARTID(22),
	/**百家乐房间数据号*/
	BACCARTROOMMODELID(23),
	/**幸运转盘*/
	LUCKYSPINID(24),
	/**幸运匹配*/
	LUCKYMATCHID(25),
	/**老虎机*/
	SLOT(26),
	/**老虎机个人信息*/
	HUMANSLOT(27),
	/**新排行榜*/
	RANKNEW(28),
	/**优惠礼包 */
	HUMANGIFT(29),
	/**德州房间号 **/
	TEXASROOT(30),
	/**成就系统 **/
	ACHIEVEMENT(31),
	/**兑换码系统 **/
	CONVERSIONCODE(32),
	/**收集系统 **/
	COLLECT(33),
	/**
	 * 俱乐部
	 */
	CLUB(34),
	CLUB_MEMBER(35),
	/** 玩家储钱罐的UUID */
	HUMANTREASURYID(36),
	/** 世界boss 的 boss 的ID */
	WORLDBOSSID(37),
	/** 世界boss 的 humanAttackBoss */
	HUMANATTACKBOSSID(38),
	/** 评论 */
	HUMANGIVEALIKE(39),
	/** 特惠李礼包 */
	HUMANMONTHWEEK(40),
	/** 付费引导 */
	HUMANPAYGUIDE(41),
	/** 新手礼包 新 */
	HUMANNEWCOMER(42),
	/**功能**/
	FUNCTIONUUID(43),
	
	/**
	 * 无双吹牛
	 */
	
	HUMANBAZOOSIGNIN(44),
	HUMANBAZOORANK(45),
	HUMANBAZOOPERSONAL(46),
	HUMANBAZOOTASK(47),
	HUMANBAZOOWINS(48),
	BAZOOROOMCREATE(49),
	BAZOONEWGUY(50),
;

	private final int index;
	/** 按索引顺序存放的枚举数组 */
	private static final List<UUIDType> values = IndexedEnumUtil.toIndexes(UUIDType.values());

	/**
	 * 
	 * @param index
	 *            枚举的索引,从0开始
	 */
	private UUIDType(int index)
	{
		this.index = index;
	}

	@Override
	public int getIndex() 
	{
		return this.index;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static UUIDType valueOf(final int index)
	{
		return EnumUtil.valueOf(values, index);
	}
}
