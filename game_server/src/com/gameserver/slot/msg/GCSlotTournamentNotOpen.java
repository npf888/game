package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 接收到此消息说明 竞赛处于关闭状态
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotTournamentNotOpen extends GCMessage{
	

	public GCSlotTournamentNotOpen (){
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
		return MessageType.GC_SLOT_TOURNAMENT_NOT_OPEN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TOURNAMENT_NOT_OPEN";
	}
}