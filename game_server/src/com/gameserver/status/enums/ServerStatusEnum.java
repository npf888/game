package com.gameserver.status.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;


public enum ServerStatusEnum implements IndexedEnum{
	TEST(0,"测试"),
	RUN(1,"运行"),
	STOP(2,"停止"),
	;
	
	public final int index;
	public final String desc;
	
	private ServerStatusEnum(int index,String desc)
	{
		this.index = index;
		this.desc = desc;
	}


	public String getDesc() 
	{
		return desc;
	}
	
	@Override
	public int getIndex() 
	{
		return index;
	}

	private static final List<ServerStatusEnum> values = IndexedEnumUtil.toIndexes(ServerStatusEnum.values());

	public static ServerStatusEnum valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
	
	
}
