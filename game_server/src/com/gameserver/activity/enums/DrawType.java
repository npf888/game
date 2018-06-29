package com.gameserver.activity.enums;

import com.core.enums.IndexedEnum;

public enum DrawType implements IndexedEnum{
	/**不可以领取 **/
	NODRAW(0),
	/** 可以领取**/
	ALLOWDRAW(1),
	/**已经领取 **/
	ALREADYDRAW(2);
	
	private final int index;
	private DrawType(int index){
		this.index = index;
		
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
}
