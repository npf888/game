package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 猫老虎机彩金推送
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType18 extends GCMessage{
	
	/** 老虎机ID */
	private int slotId;
	/** 获得彩金 */
	private long jackpot;

	public GCSlotType18 (){
	}
	
	public GCSlotType18 (
			int slotId,
			long jackpot ){
			this.slotId = slotId;
			this.jackpot = jackpot;
	}

	@Override
	protected boolean readImpl() {
		slotId = readInteger();
		jackpot = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotId);
		writeLong(jackpot);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE18;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE18";
	}

	public int getSlotId(){
		return slotId;
	}
		
	public void setSlotId(int slotId){
		this.slotId = slotId;
	}

	public long getJackpot(){
		return jackpot;
	}
		
	public void setJackpot(long jackpot){
		this.jackpot = jackpot;
	}
}