package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 活动进度数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHunamnProgressSingle extends GCMessage{
	
	/** 活动进度数据 */
	private com.gameserver.activity.data.HumanActivitySmallData humanActivitySmallData;

	public GCHunamnProgressSingle (){
	}
	
	public GCHunamnProgressSingle (
			com.gameserver.activity.data.HumanActivitySmallData humanActivitySmallData ){
			this.humanActivitySmallData = humanActivitySmallData;
	}

	@Override
	protected boolean readImpl() {
		humanActivitySmallData = new com.gameserver.activity.data.HumanActivitySmallData();
					humanActivitySmallData.setActivityId(readLong());
						{
			int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
			humanActivitySmallData.setSmallValue(subList);
		}
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanActivitySmallData.getActivityId());
		int[] smallValue=humanActivitySmallData.getSmallValue();
		writeShort(smallValue.length);
		for(int i=0; i<smallValue.length; i++){	
				writeInteger(smallValue[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUNAMN_PROGRESS_SINGLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUNAMN_PROGRESS_SINGLE";
	}

	public com.gameserver.activity.data.HumanActivitySmallData getHumanActivitySmallData(){
		return humanActivitySmallData;
	}
		
	public void setHumanActivitySmallData(com.gameserver.activity.data.HumanActivitySmallData humanActivitySmallData){
		this.humanActivitySmallData = humanActivitySmallData;
	}
}