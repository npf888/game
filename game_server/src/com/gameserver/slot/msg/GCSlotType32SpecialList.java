package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 斯巴达老虎机 特殊元素 包含 大战士 小战士 用来固定的
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType32SpecialList extends GCMessage{
	
	/** 小战士集合 */
	private int[] smallSoldier;
	/** 大战士集合 */
	private int[] bigSoldier;

	public GCSlotType32SpecialList (){
	}
	
	public GCSlotType32SpecialList (
			int[] smallSoldier,
			int[] bigSoldier ){
			this.smallSoldier = smallSoldier;
			this.bigSoldier = bigSoldier;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		smallSoldier = new int[count];
		for(int i=0; i<count; i++){
			smallSoldier[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		bigSoldier = new int[count];
		for(int i=0; i<count; i++){
			bigSoldier[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(smallSoldier.length);
		for(int i=0; i<smallSoldier.length; i++){
			writeInteger(smallSoldier[i]);
		}
		writeShort(bigSoldier.length);
		for(int i=0; i<bigSoldier.length; i++){
			writeInteger(bigSoldier[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE32_SPECIAL_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE32_SPECIAL_LIST";
	}

	public int[] getSmallSoldier(){
		return smallSoldier;
	}

	public void setSmallSoldier(int[] smallSoldier){
		this.smallSoldier = smallSoldier;
	}	

	public int[] getBigSoldier(){
		return bigSoldier;
	}

	public void setBigSoldier(int[] bigSoldier){
		this.bigSoldier = bigSoldier;
	}	
}