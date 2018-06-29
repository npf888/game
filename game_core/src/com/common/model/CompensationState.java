package com.common.model;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum CompensationState implements IndexedEnum  {
	WAIT(1),
	RELEASE(2),
	REPEAL(3)
	;

	private final int index;
	/** 按索引顺序存放的枚举数组 */
	private static final List<CompensationState> values = IndexedEnumUtil.toIndexes(CompensationState.values());
	
	/**
	 * 
	 * @param index
	 * 枚举的索引,从0开始
	 */
	private CompensationState(int index)
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
	public static CompensationState valueOf(final int index)
	{
		return EnumUtil.valueOf(values, index);
	}
}
