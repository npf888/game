package com.gameserver.activity.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
import com.gameserver.activity.HumanActivityData;



/**
 * 活动类型
 * @author wayne
 *
 */
public enum ActivityTypeEnum implements IndexedEnum{
	/**大玩家 **/
	BIG_PLAYER(1,HumanActivityData.class),
	/**幸运牌型 **/
	LUCKY_CARD(2,HumanActivityData.class),
	/** 充值 **/
	RECHARGE(3,HumanActivityData.class),

	/** 累积 充值**/
	CUMULATIVE_RECHARGE(4,HumanActivityData.class),
	
	
	/** 每日登录**/
	ActivityType5(5,HumanActivityData.class),
	/** 经验活动**/
	ActivityType6(6,HumanActivityData.class),
	/** 幸运老虎机**/
	ActivityType7(7,HumanActivityData.class),
	/** 等级获活动 **/
	ActivityType8(8,HumanActivityData.class),
	
	/** 新增 ：：：**/
	/** 连续游戏送大礼 **/
	ActivityType9(9,HumanActivityData.class),
	/** 消耗返利 **/
	ActivityType10(10,HumanActivityData.class),
	/** 广交好友 **/
	ActivityType11(11,HumanActivityData.class),
	/** 情有独钟**/
	ActivityType12(12,HumanActivityData.class),
	/** 我是大赢家  **/
	ActivityType13(13,HumanActivityData.class),
	/** 极限追求 **/
	ActivityType14(14,HumanActivityData.class),
	
	/** 累计充值（美元） **/
	ActivityType15(15,HumanActivityData.class),
	/** 连续充值送礼（必须连续） **/
	ActivityType16(16,HumanActivityData.class),
	/** 全服累计充值 **/
	ActivityType17(17,HumanActivityData.class),
	/** 累计充值（次数) **/
	ActivityType18(18,HumanActivityData.class)
	
	
	
	;

	private final int index;
	private final Class<? extends HumanActivityData> clazz;
	private ActivityTypeEnum(int index,Class<? extends HumanActivityData> clazz){
		this.index= index;
		this.clazz = clazz;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	public Class<? extends HumanActivityData> getClazz() {
		return clazz;
	}


	private static final List<ActivityTypeEnum> values = IndexedEnumUtil.toIndexes(ActivityTypeEnum.values());

	public static ActivityTypeEnum valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
