package com.gameserver.weekcard.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 周卡数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanWeekCardInfoData extends GCMessage{
	
	/** 周卡数据 */
	private com.gameserver.weekcard.data.HumanWeekCardInfoData weekCardInfoData;

	public GCHumanWeekCardInfoData (){
	}
	
	public GCHumanWeekCardInfoData (
			com.gameserver.weekcard.data.HumanWeekCardInfoData weekCardInfoData ){
			this.weekCardInfoData = weekCardInfoData;
	}

	@Override
	protected boolean readImpl() {
		weekCardInfoData = new com.gameserver.weekcard.data.HumanWeekCardInfoData();
					weekCardInfoData.setBeginTime(readLong());
							weekCardInfoData.setGetTime(readLong());
							weekCardInfoData.setDuration(readLong());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(weekCardInfoData.getBeginTime());
		writeLong(weekCardInfoData.getGetTime());
		writeLong(weekCardInfoData.getDuration());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_WEEK_CARD_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_WEEK_CARD_INFO_DATA";
	}

	public com.gameserver.weekcard.data.HumanWeekCardInfoData getWeekCardInfoData(){
		return weekCardInfoData;
	}
		
	public void setWeekCardInfoData(com.gameserver.weekcard.data.HumanWeekCardInfoData weekCardInfoData){
		this.weekCardInfoData = weekCardInfoData;
	}
}