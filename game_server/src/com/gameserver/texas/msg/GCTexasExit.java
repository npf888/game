package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州玩家退出
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasExit extends GCMessage{
	

	public GCTexasExit (){
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
		return MessageType.GC_TEXAS_EXIT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_EXIT";
	}
}