package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 进入房间  匹配中   当前只有一个用户（用户自己）的时候 发送此消息 告诉用户 应该处于等待状态
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomMatcheding extends GCMessage{
	

	public GCRoomMatcheding (){
	}
	

	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_MATCHEDING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_MATCHEDING";
	}
}