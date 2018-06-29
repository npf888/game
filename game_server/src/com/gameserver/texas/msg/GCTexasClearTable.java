package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州清理桌面
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasClearTable extends GCMessage{
	

	public GCTexasClearTable (){
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
		return MessageType.GC_TEXAS_CLEAR_TABLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_CLEAR_TABLE";
	}
}