package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 犀牛老虎机bouns信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType27BounsInfo extends GCMessage{
	
	/** bouns个数 */
	private int bounsNum;
	/** 中奖额度 */
	private long totalGold;

	public GCSlotType27BounsInfo (){
	}
	
	public GCSlotType27BounsInfo (
			int bounsNum,
			long totalGold ){
			this.bounsNum = bounsNum;
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		bounsNum = readInteger();
		totalGold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bounsNum);
		writeLong(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE27_BOUNS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE27_BOUNS_INFO";
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
}