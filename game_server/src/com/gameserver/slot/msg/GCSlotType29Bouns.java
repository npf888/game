package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 西方龙老虎机bouns小游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType29Bouns extends GCMessage{
	
	/** 1:是龙子，0不是龙子 */
	private int isSon;
	/** 金币 */
	private int[] gold;

	public GCSlotType29Bouns (){
	}
	
	public GCSlotType29Bouns (
			int isSon,
			int[] gold ){
			this.isSon = isSon;
			this.gold = gold;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		isSon = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		gold = new int[count];
		for(int i=0; i<count; i++){
			gold[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isSon);
		writeShort(gold.length);
		for(int i=0; i<gold.length; i++){
			writeInteger(gold[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE29_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE29_BOUNS";
	}

	public int getIsSon(){
		return isSon;
	}
		
	public void setIsSon(int isSon){
		this.isSon = isSon;
	}

	public int[] getGold(){
		return gold;
	}

	public void setGold(int[] gold){
		this.gold = gold;
	}	
}