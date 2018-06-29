package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 东方龙老虎机初始化奖池信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType23InitReward extends GCMessage{
	
	/** 奖池的初始化信息，每个奖池的金额以','分割 */
	private String jackpotInfo;

	public GCSlotType23InitReward (){
	}
	
	public GCSlotType23InitReward (
			String jackpotInfo ){
			this.jackpotInfo = jackpotInfo;
	}

	@Override
	protected boolean readImpl() {
		jackpotInfo = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(jackpotInfo);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE23_INIT_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE23_INIT_REWARD";
	}

	public String getJackpotInfo(){
		return jackpotInfo;
	}
		
	public void setJackpotInfo(String jackpotInfo){
		this.jackpotInfo = jackpotInfo;
	}
}