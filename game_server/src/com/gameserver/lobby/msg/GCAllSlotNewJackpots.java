package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回  所有老虎机（每种类型的老虎机 对应一个最高的彩金）的最高彩金
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCAllSlotNewJackpots extends GCMessage{
	
	/** 老虎机类型 */
	private int[] slotType;
	/** 老虎机类型 对应的最高彩金 */
	private long[] jackpot;

	public GCAllSlotNewJackpots (){
	}
	
	public GCAllSlotNewJackpots (
			int[] slotType,
			long[] jackpot ){
			this.slotType = slotType;
			this.jackpot = jackpot;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		slotType = new int[count];
		for(int i=0; i<count; i++){
			slotType[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		jackpot = new long[count];
		for(int i=0; i<count; i++){
			jackpot[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(slotType.length);
		for(int i=0; i<slotType.length; i++){
			writeInteger(slotType[i]);
		}
		writeShort(jackpot.length);
		for(int i=0; i<jackpot.length; i++){
			writeLong(jackpot[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ALL_SLOT_NEW_JACKPOTS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ALL_SLOT_NEW_JACKPOTS";
	}

	public int[] getSlotType(){
		return slotType;
	}

	public void setSlotType(int[] slotType){
		this.slotType = slotType;
	}	

	public long[] getJackpot(){
		return jackpot;
	}

	public void setJackpot(long[] jackpot){
		this.jackpot = jackpot;
	}	
}