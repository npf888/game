package com.gameserver.task.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 
 * @author 郭君伟
 *
 */
public enum RefreshType implements IndexedEnum {

	RefreshType1(1),
	RefreshType2(2),
	RefreshType3(3),
	RefreshType4(4),
	RefreshType5(5),
	RefreshType6(6),
	RefreshType7(7),
	RefreshType8(8),
	RefreshType9(9),
	
	;
	
	private int index;
	private RefreshType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}
	
	private static final List<RefreshType> values = IndexedEnumUtil.toIndexes(RefreshType.values());

	public static RefreshType valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}

}
