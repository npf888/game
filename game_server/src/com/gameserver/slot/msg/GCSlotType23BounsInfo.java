package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 东方龙老虎机bouns信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType23BounsInfo extends GCMessage{
	
	/** bouns个数 */
	private int bounsNum;
	/** 每次中奖获得的奖金数 */
	private long obtainReward;
	/** 奖池的初始化信息，每个奖池的金额以','分割 */
	private String jackpotInfo;

	public GCSlotType23BounsInfo (){
	}
	
	public GCSlotType23BounsInfo (
			int bounsNum,
			long obtainReward,
			String jackpotInfo ){
			this.bounsNum = bounsNum;
			this.obtainReward = obtainReward;
			this.jackpotInfo = jackpotInfo;
	}

	@Override
	protected boolean readImpl() {
		bounsNum = readInteger();
		obtainReward = readLong();
		jackpotInfo = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bounsNum);
		writeLong(obtainReward);
		writeString(jackpotInfo);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE23_BOUNS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE23_BOUNS_INFO";
	}

	public int getBounsNum(){
		return bounsNum;
	}
		
	public void setBounsNum(int bounsNum){
		this.bounsNum = bounsNum;
	}

	public long getObtainReward(){
		return obtainReward;
	}
		
	public void setObtainReward(long obtainReward){
		this.obtainReward = obtainReward;
	}

	public String getJackpotInfo(){
		return jackpotInfo;
	}
		
	public void setJackpotInfo(String jackpotInfo){
		this.jackpotInfo = jackpotInfo;
	}
}