package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 万圣节老虎机   触发 bonus小游戏 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType38BonusTrigger extends GCMessage{
	

	public GCSlotType38BonusTrigger (){
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
		return MessageType.GC_SLOT_TYPE38_BONUS_TRIGGER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE38_BONUS_TRIGGER";
	}
}