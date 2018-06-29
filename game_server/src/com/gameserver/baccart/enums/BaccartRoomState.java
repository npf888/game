package com.gameserver.baccart.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;


public enum BaccartRoomState implements IndexedEnum{
	INIT(0),
	SHUFFLE(1),
	BET(2),
	SETTLE(3),
	CLEAR(4),

	;

	private int index;
	private BaccartRoomState(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	private static final List<BaccartRoomState> values = IndexedEnumUtil.toIndexes(BaccartRoomState.values());

	public static BaccartRoomState valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
	
	@Override
	public String toString(){
		switch(this)
		{
		case INIT:
			return "初始化";
		case SHUFFLE:
			return "洗牌";
		case BET:
			return "下注";

		case SETTLE:
			return "结算";
		case CLEAR:
			return "清理桌面";
					
		default:
			return "";
		}
	}

}
