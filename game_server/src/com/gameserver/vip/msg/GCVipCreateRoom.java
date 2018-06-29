package com.gameserver.vip.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * vip开房
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCVipCreateRoom extends GCMessage{
	

	public GCVipCreateRoom (){
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
		return MessageType.GC_VIP_CREATE_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_VIP_CREATE_ROOM";
	}
}