package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机彩金 获得的值
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanJackpotReward extends GCMessage{
	
	/** 获得彩金的数量 */
	private long jackpotReward;

	public GCHumanJackpotReward (){
	}
	
	public GCHumanJackpotReward (
			long jackpotReward ){
			this.jackpotReward = jackpotReward;
	}

	@Override
	protected boolean readImpl() {
		jackpotReward = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(jackpotReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_JACKPOT_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_JACKPOT_REWARD";
	}

	public long getJackpotReward(){
		return jackpotReward;
	}
		
	public void setJackpotReward(long jackpotReward){
		this.jackpotReward = jackpotReward;
	}
}