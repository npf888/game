package com.gameserver.baccart.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum BaccartPairEnum  implements IndexedEnum{
	NONE(0),
	PLAYER_PAIR(1),
	BANKER_PAIR(2),
	PLAYER_BNAKER_PAIR(3),
	;
	
	private int index;
	private BaccartPairEnum(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	private static final List<BaccartPairEnum> values = IndexedEnumUtil.toIndexes(BaccartPairEnum.values());
	
	
	public static BaccartPairEnum valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}

	@Override
	public String toString(){
		switch(this)
		{
		case NONE:
			return "无";
		case PLAYER_PAIR:
			return "闲对";
		case BANKER_PAIR:
			return "庄对";
		case PLAYER_BNAKER_PAIR:
			return "庄闲对";

		default:
			return "";
		}
	}
}
