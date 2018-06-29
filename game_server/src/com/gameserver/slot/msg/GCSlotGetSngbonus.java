package com.gameserver.slot.msg;

import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.msg.MessageType;

/**
 * 获取竞赛奖金池 变化的时候也会主动推给客户端
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotGetSngbonus extends GCMessage{
	
	/** 老虎机ID */
	private int slotId;
	/** 开始时间点 */
	private long startTime;
	/** 奖金总数 */
	private long allBonus;

	public GCSlotGetSngbonus (){
	}
	
	public GCSlotGetSngbonus (
			int slotId,
			long startTime,
			long allBonus ){
			this.slotId = slotId;
			this.startTime = startTime;
			this.allBonus = allBonus;
	}

	@Override
	protected boolean readImpl() {
		slotId = readInteger();
		startTime = readLong();
		allBonus = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotId);
		writeLong(startTime);
		writeLong(allBonus);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_GET_SNGBONUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_GET_SNGBONUS";
	}

	public int getSlotId(){
		return slotId;
	}
		
	public void setSlotId(int slotId){
		this.slotId = slotId;
	}

	public long getStartTime(){
		return startTime;
	}
		
	public void setStartTime(long startTime){
		this.startTime = startTime;
	}

	public long getAllBonus(){
		return allBonus;
	}
		
	public void setAllBonus(long allBonus){
		this.allBonus = allBonus;
	}
}