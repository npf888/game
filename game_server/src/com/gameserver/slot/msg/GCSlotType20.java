package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 泰国大象老虎机 BigWild
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType20 extends GCMessage{
	
	/** 那一列中了BigWild */
	private int[] column;
	/** 一共获得免费转动次数 */
	private int freeNum;

	public GCSlotType20 (){
	}
	
	public GCSlotType20 (
			int[] column,
			int freeNum ){
			this.column = column;
			this.freeNum = freeNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		column = new int[count];
		for(int i=0; i<count; i++){
			column[i] = readInteger();
		}
		freeNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(column.length);
		for(int i=0; i<column.length; i++){
			writeInteger(column[i]);
		}
		writeInteger(freeNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE20;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE20";
	}

	public int[] getColumn(){
		return column;
	}

	public void setColumn(int[] column){
		this.column = column;
	}	

	public int getFreeNum(){
		return freeNum;
	}
		
	public void setFreeNum(int freeNum){
		this.freeNum = freeNum;
	}
}