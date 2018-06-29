package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海底世界老虎机bouns信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType28BounsInfo extends GCMessage{
	
	/** bouns个数 */
	private int bounsNum;
	/** bouns位置 */
	private int[] posList;
	/** 奖池的ID */
	private int rewardId;

	public GCSlotType28BounsInfo (){
	}
	
	public GCSlotType28BounsInfo (
			int bounsNum,
			int[] posList,
			int rewardId ){
			this.bounsNum = bounsNum;
			this.posList = posList;
			this.rewardId = rewardId;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		bounsNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		posList = new int[count];
		for(int i=0; i<count; i++){
			posList[i] = readInteger();
		}
		rewardId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bounsNum);
		writeShort(posList.length);
		for(int i=0; i<posList.length; i++){
			writeInteger(posList[i]);
		}
		writeInteger(rewardId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE28_BOUNS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE28_BOUNS_INFO";
	}

	public int getBounsNum(){
		return bounsNum;
	}
		
	public void setBounsNum(int bounsNum){
		this.bounsNum = bounsNum;
	}

	public int[] getPosList(){
		return posList;
	}

	public void setPosList(int[] posList){
		this.posList = posList;
	}	

	public int getRewardId(){
		return rewardId;
	}
		
	public void setRewardId(int rewardId){
		this.rewardId = rewardId;
	}
}