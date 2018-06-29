package com.gameserver.chat.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;


/**
 * 聊天频道
 * @author wayne
 *
 */
public enum ChatChannelEnum implements IndexedEnum{
	//喇叭
	SPEAKER(0),
	//世界
	WORLD(1),
	//房间
	ROOM(2),
	//俱乐部
	CLUB(3),
	//私聊
	PRIVATE(4),
	//百家乐
	BACCARAT(5);
	
	private final int index;
	private ChatChannelEnum(int index){
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}
	private static final List<ChatChannelEnum> values = IndexedEnumUtil.toIndexes(ChatChannelEnum.values());

	public static ChatChannelEnum valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
	
	
}
