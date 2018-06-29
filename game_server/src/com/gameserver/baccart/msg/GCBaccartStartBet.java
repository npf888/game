package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐开始下注
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartStartBet extends GCMessage{
	

	public GCBaccartStartBet (){
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
		return MessageType.GC_BACCART_START_BET;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_START_BET";
	}
}