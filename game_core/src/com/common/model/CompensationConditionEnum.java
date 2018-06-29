package com.common.model;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum CompensationConditionEnum implements IndexedEnum {
	DAY(1),
	LEVEL(2),
	VIPLEVEL(3),
	CHARGE(4)
	;

	private final int index;
	/** 按索引顺序存放的枚举数组 */
	private static final List<CompensationConditionEnum> values = IndexedEnumUtil.toIndexes(CompensationConditionEnum.values());
	
	/**
	 * 
	 * @param index
	 * 枚举的索引,从0开始
	 */
	private CompensationConditionEnum(int index)
	{
		this.index = index;
	}
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static CompensationConditionEnum valueOf(final int index)
	{
		return EnumUtil.valueOf(values, index);
	}
}
