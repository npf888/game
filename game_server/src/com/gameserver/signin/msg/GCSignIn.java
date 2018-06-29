package com.gameserver.signin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 签到
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSignIn extends GCMessage{
	
	/** 签到天数 */
	private int day;
	/** 道具奖励 */
	private com.gameserver.common.data.RandRewardData[] randRewardList;

	public GCSignIn (){
	}
	
	public GCSignIn (
			int day,
			com.gameserver.common.data.RandRewardData[] randRewardList ){
			this.day = day;
			this.randRewardList = randRewardList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		day = readInteger();
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
		writeInteger(day);
		writeShort(randRewardList.length);
		for(int i=0; i<randRewardList.length; i++){
			writeInteger(randRewardList[i].getRewardId());
			writeInteger(randRewardList[i].getRewardCount());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SIGN_IN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SIGN_IN";
	}

	public int getDay(){
		return day;
	}
		
	public void setDay(int day){
		this.day = day;
	}

	public com.gameserver.common.data.RandRewardData[] getRandRewardList(){
		return randRewardList;
	}

	public void setRandRewardList(com.gameserver.common.data.RandRewardData[] randRewardList){
		this.randRewardList = randRewardList;
	}	
}