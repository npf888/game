package com.gameserver.texas.enums;

import com.core.enums.IndexedEnum;

public enum JoinVipErrorEnum implements IndexedEnum{
	/**成功*/
	SUCCESS(0),
	/**密码错误*/
	PASSWORD_ERROR(1),
	/**房间不存在*/
	NO_EXIST(2),
	;
	

	
	private int index;
	
	private JoinVipErrorEnum(int index)
	{
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	;


}
