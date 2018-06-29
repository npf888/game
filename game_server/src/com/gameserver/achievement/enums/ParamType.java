package com.gameserver.achievement.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum ParamType implements IndexedEnum {
	/**  单个 **/
	ParamType1(1),
	/** 多个 **/
	ParamType2(2)
	;

	private int index;
	private ParamType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}
	
	private static final List<ParamType> values = IndexedEnumUtil.toIndexes(ParamType.values());

	public static ParamType valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}

}
