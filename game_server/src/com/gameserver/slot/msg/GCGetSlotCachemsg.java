package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 请求老虎机缓存消息,服务器没有缓存消息的时候返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetSlotCachemsg extends GCMessage{
	

	public GCGetSlotCachemsg (){
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
		return MessageType.GC_GET_SLOT_CACHEMSG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_SLOT_CACHEMSG";
	}
}