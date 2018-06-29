package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 新手引导
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCNewUser extends GCMessage{
	

	public GCNewUser (){
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
		return MessageType.GC_NEW_USER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_NEW_USER";
	}
}