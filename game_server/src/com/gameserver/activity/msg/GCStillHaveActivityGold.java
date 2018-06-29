package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 还有未领取的活动的金币
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStillHaveActivityGold extends GCMessage{
	

	public GCStillHaveActivityGold (){
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
		return MessageType.GC_STILL_HAVE_ACTIVITY_GOLD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STILL_HAVE_ACTIVITY_GOLD";
	}
}