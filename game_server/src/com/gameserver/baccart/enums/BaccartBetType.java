package com.gameserver.baccart.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;


/**
 * 百家乐押注类型枚举
 * @author wayne
 *
 */
public enum BaccartBetType implements IndexedEnum{
	BANKER(1,0.95f),
	PLAYER(2,1.0f),
	DRAW(3,8.0f),
	BANKER_PAIR(4,11.0f),
	PLAYER_PAIR(5,11.0f),
	;

	private int index;
	private float rate;
	private BaccartBetType(int index,float rate){
		this.index = index;
		this.rate = rate;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public float getRate(){
		return this.rate;
	}
	
	@Override
	public String toString(){
		switch(this)
		{
		case BANKER:
			return "庄家";
		case PLAYER:
			return "闲家";
		case DRAW:
			return "和";

		case BANKER_PAIR:
			return "庄对";
		case PLAYER_PAIR:
			return "闲对";	
		default:
			return "";
		}
	}
	
	private static final List<BaccartBetType> values = IndexedEnumUtil.toIndexes(BaccartBetType.values());
	
	
	public static BaccartBetType valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}

}
