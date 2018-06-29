package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家现在使用版本
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClientVersion extends GCMessage{
	

	public GCClientVersion (){
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
		return MessageType.GC_CLIENT_VERSION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLIENT_VERSION";
	}
}