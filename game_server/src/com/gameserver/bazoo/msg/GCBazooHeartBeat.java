package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 心跳
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooHeartBeat extends GCMessage{
	

	public GCBazooHeartBeat (){
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
		return MessageType.GC_BAZOO_HEART_BEAT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_HEART_BEAT";
	}
}