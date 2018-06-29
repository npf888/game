package com.gameserver.player.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;


public enum PlayerRoleEnum implements IndexedEnum{
	/**普通玩家 **/
	NORMAL(0),
	/** Gm **/
	GM(1),
	/**机器人 **/
	ROBOT(2),
	/** 币商  **/
	AGENT(3),
	;
	
	private int index;
	private PlayerRoleEnum(int index){
		this.index = index;
	}
	
	private static final List<PlayerRoleEnum> indexes = IndexedEnumUtil.toIndexes(PlayerRoleEnum.values());

	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public static PlayerRoleEnum indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}

	@Override
	public String toString(){
		switch(this){
		case NORMAL:
			return "普通玩家";
		case GM:
			return "GM";
		case ROBOT:
			return "robot";
		case AGENT:
			return "币商人";
		}
		return "";
	}
	
}
