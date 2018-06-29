package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 巴西风情老虎机  桑巴  游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType24BounsSamba extends GCMessage{
	
	/** 获得的金币 */
	private long reward;

	public GCSlotType24BounsSamba (){
	}
	
	public GCSlotType24BounsSamba (
			long reward ){
			this.reward = reward;
	}

	@Override
	protected boolean readImpl() {
		reward = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(reward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE24_BOUNS_SAMBA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE24_BOUNS_SAMBA";
	}

	public long getReward(){
		return reward;
	}
		
	public void setReward(long reward){
		this.reward = reward;
	}
}