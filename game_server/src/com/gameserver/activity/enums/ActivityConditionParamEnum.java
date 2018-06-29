package com.gameserver.activity.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 活动条件
 * @author wayne
 *
 */
public enum ActivityConditionParamEnum implements IndexedEnum{
	
	TEXAS_ROOM_TYPE(1,"德州房间类型"),
	WIN_COUNT(2,"获胜局数"),
	
	LUCKY_CARD(3,"幸运牌型"),
	SMALL_BLIND(4,"小盲注"),
	
	RECHARGE_PRODUCT_ID(5,"充值档次"),

	Condition6(6,"累计登陆天数"),
	Condition7(7,"经验加成系数"),
	
	Condition8(8,"老虎机类型"),
	Condition9(9,"次数"),
	
	Condition10(10,"达到等级"),
	Condition11(11,"转动老虎机次数"),
	Condition12(12,"消耗筹码"),
	Condition13(13,"好友增加数量"),
	
	Condition14(14,"老虎机类型"),
	Condition15(15,"转动次数"),
	Condition20(20,"消耗金额"),
	
	Condition16(16,"转动次数"),
	Condition21(21,"消耗金额"),
	
	Condition17(17,"老虎机类型"),
	Condition18(18,"图标"),
	Condition19(19,"出现次数"),
	
	Condition22(22,"美元"),
	Condition23(23,"次数"),
	
	Condition24(24,"连续天数"),
	Condition25(25,"全服累计充值"),
	;

	public final int index;
	public final String desc;
	
	private static final List<ActivityConditionParamEnum> values = IndexedEnumUtil.toIndexes(ActivityConditionParamEnum.values());
	
	private ActivityConditionParamEnum(int index,String desc)
	{
		this.index = index;
		this.desc = desc;
	}
	@Override
	public int getIndex() {
		return index;
	}

	public String getDesc()
	{
		return desc;
	}
	
	public static ActivityConditionParamEnum valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}
	
}
