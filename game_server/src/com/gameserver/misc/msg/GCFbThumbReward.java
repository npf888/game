package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * fb点赞奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFbThumbReward extends GCMessage{
	
	/** 道具奖励 */
	private com.gameserver.common.data.RandRewardData[] randRewardList;

	public GCFbThumbReward (){
	}
	
	public GCFbThumbReward (
			com.gameserver.common.data.RandRewardData[] randRewardList ){
			this.randRewardList = randRewardList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		randRewardList = new com.gameserver.common.data.RandRewardData[count];
		for(int i=0; i<count; i++){
			com.gameserver.common.data.RandRewardData obj = new com.gameserver.common.data.RandRewardData();
			obj.setRewardId(readInteger());
			obj.setRewardCount(readInteger());
			randRewardList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(randRewardList.length);
		for(int i=0; i<randRewardList.length; i++){
			writeInteger(randRewardList[i].getRewardId());
			writeInteger(randRewardList[i].getRewardCount());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FB_THUMB_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FB_THUMB_REWARD";
	}

	public com.gameserver.common.data.RandRewardData[] getRandRewardList(){
		return randRewardList;
	}

	public void setRandRewardList(com.gameserver.common.data.RandRewardData[] randRewardList){
		this.randRewardList = randRewardList;
	}	
}