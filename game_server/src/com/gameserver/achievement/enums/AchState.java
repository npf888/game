package com.gameserver.achievement.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 1 没有完成 2 已经完成但没有领取 3 已经领取
 * @author 郭君伟
 *
 */
public enum AchState implements IndexedEnum {
	/** 没有完成 **/
	AchState1(1),
	/** 已经完成但没有领取 **/
	AchState2(2),
	/** 已经领取 **/
	AchState3(3)
	
	;

	private int index;
	private AchState(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}
	
	private static final List<AchState> values = IndexedEnumUtil.toIndexes(AchState.values());

	public static AchState valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
