package com.gameserver.texas.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;


/**
 * 德州房间枚举
 * @author wayne
 *
 */
public enum TexasRoomEnum implements IndexedEnum{
	NORMAL(0),//普通
	SNG(1),//比赛
	VIP(2),//vip
	;
	private int index;
	private TexasRoomEnum(int index){
		this.index = index;
	} 
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	@Override
	public String toString(){
		switch(this)
		{
		case NORMAL:
			return "普通场次";
		case SNG:
			return "SNG";
		case VIP:
			return "vip";
		default:
			return "";
		}
	}
	
	private static final List<TexasRoomEnum> values = IndexedEnumUtil.toIndexes(TexasRoomEnum.values());

	public static TexasRoomEnum valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
