package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 转盘
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLuckySpinInfoData extends GCMessage{
	
	/** 内容 */
	private com.gameserver.luckyspin.data.HumanLuckySpinData humanLuckySpinData;

	public GCLuckySpinInfoData (){
	}
	
	public GCLuckySpinInfoData (
			com.gameserver.luckyspin.data.HumanLuckySpinData humanLuckySpinData ){
			this.humanLuckySpinData = humanLuckySpinData;
	}

	@Override
	protected boolean readImpl() {
		humanLuckySpinData = new com.gameserver.luckyspin.data.HumanLuckySpinData();
					humanLuckySpinData.setLastSpinTime(readLong());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanLuckySpinData.getLastSpinTime());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LUCKY_SPIN_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LUCKY_SPIN_INFO_DATA";
	}

	public com.gameserver.luckyspin.data.HumanLuckySpinData getHumanLuckySpinData(){
		return humanLuckySpinData;
	}
		
	public void setHumanLuckySpinData(com.gameserver.luckyspin.data.HumanLuckySpinData humanLuckySpinData){
		this.humanLuckySpinData = humanLuckySpinData;
	}
}