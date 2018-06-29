package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 韩国推广奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCKoreanSb extends GCMessage{
	
	/** 奖励 */
	private int rewardNum;

	public GCKoreanSb (){
	}
	
	public GCKoreanSb (
			int rewardNum ){
			this.rewardNum = rewardNum;
	}

	@Override
	protected boolean readImpl() {
		rewardNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_KOREAN_SB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_KOREAN_SB";
	}

	public int getRewardNum(){
		return rewardNum;
	}
		
	public void setRewardNum(int rewardNum){
		this.rewardNum = rewardNum;
	}
}