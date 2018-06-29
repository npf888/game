package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 狮身人面老虎机 判断是否开始小游戏 bouns
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType15BounsStart extends GCMessage{
	
	/** 当前的金币 */
	private long[] currentGold;

	public GCSlotType15BounsStart (){
	}
	
	public GCSlotType15BounsStart (
			long[] currentGold ){
			this.currentGold = currentGold;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		currentGold = new long[count];
		for(int i=0; i<count; i++){
			currentGold[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(currentGold.length);
		for(int i=0; i<currentGold.length; i++){
			writeLong(currentGold[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE15_BOUNS_START;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE15_BOUNS_START";
	}

	public long[] getCurrentGold(){
		return currentGold;
	}

	public void setCurrentGold(long[] currentGold){
		this.currentGold = currentGold;
	}	
}