package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐清理桌面
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartClearTable extends GCMessage{
	

	public GCBaccartClearTable (){
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
		return MessageType.GC_BACCART_CLEAR_TABLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_CLEAR_TABLE";
	}
}