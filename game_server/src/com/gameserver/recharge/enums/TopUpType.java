package com.gameserver.recharge.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum TopUpType implements IndexedEnum{
	/**普通 **/
	COMMON(0),
	/**mycard **/
	MYCARD(1),
	/**MOL **/
	MOL(2)
	;

	private final int index;
	
	private TopUpType(int index){
		this.index= index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}

	private static final List<TopUpType> values = IndexedEnumUtil.toIndexes(TopUpType.values());

	public static TopUpType valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
