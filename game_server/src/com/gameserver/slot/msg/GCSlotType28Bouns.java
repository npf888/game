package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海底世界老虎机bouns小游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType28Bouns extends GCMessage{
	
	/** 奖金模板的ID */
	private int rewardId;

	public GCSlotType28Bouns (){
	}
	
	public GCSlotType28Bouns (
			int rewardId ){
			this.rewardId = rewardId;
	}

	@Override
	protected boolean readImpl() {
		rewardId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE28_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE28_BOUNS";
	}

	public int getRewardId(){
		return rewardId;
	}
		
	public void setRewardId(int rewardId){
		this.rewardId = rewardId;
	}
}