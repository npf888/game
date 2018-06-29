package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 女巫魔法老虎机bouns信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType26BounsInfo extends GCMessage{
	
	/** bouns个数 */
	private int bounsNum;
	/** 奖金额度 */
	private long totalGold;
	/** bouns位置 */
	private int[] posList;

	public GCSlotType26BounsInfo (){
	}
	
	public GCSlotType26BounsInfo (
			int bounsNum,
			long totalGold,
			int[] posList ){
			this.bounsNum = bounsNum;
			this.totalGold = totalGold;
			this.posList = posList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		bounsNum = readInteger();
		totalGold = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		posList = new int[count];
		for(int i=0; i<count; i++){
			posList[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bounsNum);
		writeLong(totalGold);
		writeShort(posList.length);
		for(int i=0; i<posList.length; i++){
			writeInteger(posList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE26_BOUNS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE26_BOUNS_INFO";
	}

	public int getBounsNum(){
		return bounsNum;
	}
		
	public void setBounsNum(int bounsNum){
		this.bounsNum = bounsNum;
	}

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}

	public int[] getPosList(){
		return posList;
	}

	public void setPosList(int[] posList){
		this.posList = posList;
	}	
}