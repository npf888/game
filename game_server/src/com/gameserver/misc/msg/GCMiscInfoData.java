package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 在线奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCMiscInfoData extends GCMessage{
	
	/** 内容 */
	private com.gameserver.misc.data.HumanMiscInfoData humanMiscInfoData;

	public GCMiscInfoData (){
	}
	
	public GCMiscInfoData (
			com.gameserver.misc.data.HumanMiscInfoData humanMiscInfoData ){
			this.humanMiscInfoData = humanMiscInfoData;
	}

	@Override
	protected boolean readImpl() {
		humanMiscInfoData = new com.gameserver.misc.data.HumanMiscInfoData();
					humanMiscInfoData.setOnlineTime(readLong());
							humanMiscInfoData.setCurrentOnlineRewardId(readInteger());
							humanMiscInfoData.setFirstRechargeTime(readLong());
							humanMiscInfoData.setRenameTimes(readInteger());
							humanMiscInfoData.setNewUser(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanMiscInfoData.getOnlineTime());
		writeInteger(humanMiscInfoData.getCurrentOnlineRewardId());
		writeLong(humanMiscInfoData.getFirstRechargeTime());
		writeInteger(humanMiscInfoData.getRenameTimes());
		writeInteger(humanMiscInfoData.getNewUser());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MISC_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MISC_INFO_DATA";
	}

	public com.gameserver.misc.data.HumanMiscInfoData getHumanMiscInfoData(){
		return humanMiscInfoData;
	}
		
	public void setHumanMiscInfoData(com.gameserver.misc.data.HumanMiscInfoData humanMiscInfoData){
		this.humanMiscInfoData = humanMiscInfoData;
	}
}