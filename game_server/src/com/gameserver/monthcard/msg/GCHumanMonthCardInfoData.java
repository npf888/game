package com.gameserver.monthcard.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 月卡数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanMonthCardInfoData extends GCMessage{
	
	/** 月 卡数据 */
	private com.gameserver.monthcard.data.HumanMonthCardInfoData monthCardInfoData;

	public GCHumanMonthCardInfoData (){
	}
	
	public GCHumanMonthCardInfoData (
			com.gameserver.monthcard.data.HumanMonthCardInfoData monthCardInfoData ){
			this.monthCardInfoData = monthCardInfoData;
	}

	@Override
	protected boolean readImpl() {
		monthCardInfoData = new com.gameserver.monthcard.data.HumanMonthCardInfoData();
					monthCardInfoData.setBeginTime(readLong());
							monthCardInfoData.setGetTime(readLong());
							monthCardInfoData.setDuration(readLong());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(monthCardInfoData.getBeginTime());
		writeLong(monthCardInfoData.getGetTime());
		writeLong(monthCardInfoData.getDuration());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_MONTH_CARD_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_MONTH_CARD_INFO_DATA";
	}

	public com.gameserver.monthcard.data.HumanMonthCardInfoData getMonthCardInfoData(){
		return monthCardInfoData;
	}
		
	public void setMonthCardInfoData(com.gameserver.monthcard.data.HumanMonthCardInfoData monthCardInfoData){
		this.monthCardInfoData = monthCardInfoData;
	}
}