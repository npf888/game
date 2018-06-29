package com.gameserver.signin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 签到
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSignInInfoData extends GCMessage{
	
	/** 签到天数 */
	private int[] dayList;

	public GCSignInInfoData (){
	}
	
	public GCSignInInfoData (
			int[] dayList ){
			this.dayList = dayList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		dayList = new int[count];
		for(int i=0; i<count; i++){
			dayList[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(dayList.length);
		for(int i=0; i<dayList.length; i++){
			writeInteger(dayList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SIGN_IN_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SIGN_IN_INFO_DATA";
	}

	public int[] getDayList(){
		return dayList;
	}

	public void setDayList(int[] dayList){
		this.dayList = dayList;
	}	
}