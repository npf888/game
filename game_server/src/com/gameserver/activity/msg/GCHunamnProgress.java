package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 活动进度数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHunamnProgress extends GCMessage{
	
	/** 活动进度数据 */
	private com.gameserver.activity.data.HumanActivitySmallData[] humanActivitySmallData;

	public GCHunamnProgress (){
	}
	
	public GCHunamnProgress (
			com.gameserver.activity.data.HumanActivitySmallData[] humanActivitySmallData ){
			this.humanActivitySmallData = humanActivitySmallData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		humanActivitySmallData = new com.gameserver.activity.data.HumanActivitySmallData[count];
		for(int i=0; i<count; i++){
			com.gameserver.activity.data.HumanActivitySmallData obj = new com.gameserver.activity.data.HumanActivitySmallData();
			obj.setActivityId(readLong());
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setSmallValue(subList);
			}
			humanActivitySmallData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(humanActivitySmallData.length);
		for(int i=0; i<humanActivitySmallData.length; i++){
			writeLong(humanActivitySmallData[i].getActivityId());
			int[] smallValue=humanActivitySmallData[i].getSmallValue();
			writeShort(smallValue.length);
			for(int j=0; j<smallValue.length; j++){
				writeInteger(smallValue[j]);
			}
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUNAMN_PROGRESS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUNAMN_PROGRESS";
	}

	public com.gameserver.activity.data.HumanActivitySmallData[] getHumanActivitySmallData(){
		return humanActivitySmallData;
	}

	public void setHumanActivitySmallData(com.gameserver.activity.data.HumanActivitySmallData[] humanActivitySmallData){
		this.humanActivitySmallData = humanActivitySmallData;
	}	
}