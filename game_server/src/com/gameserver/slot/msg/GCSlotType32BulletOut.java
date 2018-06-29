package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 斯巴达老虎机攻城车出现 通知当前用户
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType32BulletOut extends GCMessage{
	

	public GCSlotType32BulletOut (){
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
		return MessageType.GC_SLOT_TYPE32_BULLET_OUT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE32_BULLET_OUT";
	}
}