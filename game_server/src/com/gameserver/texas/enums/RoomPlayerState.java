package com.gameserver.texas.enums;

import com.core.enums.IndexedEnum;

public enum RoomPlayerState implements IndexedEnum{
	/**桌上*/
	INIT(0),
	/**排队*/
	WAITING(1),
	/**游戏*/
	GAMING(2),
	/**弃牌*/
	GIVEUP(3);
	
	private int index;
	
	private RoomPlayerState(int index)
	{
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
}
