package com.gameserver.baccart.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum BaccartResultEnum implements IndexedEnum{
	DRAW(0),
	PLAYER(1),
	BANKER(2),
	;
	
	private int index;
	private BaccartResultEnum(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	private static final List<BaccartResultEnum> values = IndexedEnumUtil.toIndexes(BaccartResultEnum.values());
	
	
	public static BaccartResultEnum valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}
	
	@Override
	public String toString(){
		switch(this)
		{
		case DRAW:
			return "无";
		case PLAYER:
			return "闲";
		case BANKER:
			return "庄";
	
		default:
			return "";
		}
	}
}
