package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 进入房间  有新人进入了，那些正在房间里的人  会收到 这个消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomEnter extends GCMessage{
	
	/** 其他人的信息 */
	private com.gameserver.bazoo.data.ReturnPlayerInfo[] returnPlayerInfo;

	public GCRoomEnter (){
	}
	
	public GCRoomEnter (
			com.gameserver.bazoo.data.ReturnPlayerInfo[] returnPlayerInfo ){
			this.returnPlayerInfo = returnPlayerInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		returnPlayerInfo = new com.gameserver.bazoo.data.ReturnPlayerInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.ReturnPlayerInfo obj = new com.gameserver.bazoo.data.ReturnPlayerInfo();
			obj.setPassportId(readLong());
			obj.setName(readString());
			obj.setGirlFlag(readInteger());
			obj.setUserStatus(readInteger());
			obj.setCurGold(readLong());
			obj.setHeadImg(readString());
			obj.setDiceContainer(readString());
			obj.setSeat(readInteger());
			obj.setWinTimes(readInteger());
			obj.setVipLevel(readInteger());
			obj.setNum(readInteger());
			obj.setValue(readInteger());
			obj.setDiceType(readInteger());
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setDiceValues(subList);
			}
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setRedDiceValues(subList);
			}
			obj.setClockImg(readString());
			obj.setClockItemId(readInteger());
			returnPlayerInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(returnPlayerInfo.length);
		for(int i=0; i<returnPlayerInfo.length; i++){
			writeLong(returnPlayerInfo[i].getPassportId());
			writeString(returnPlayerInfo[i].getName());
			writeInteger(returnPlayerInfo[i].getGirlFlag());
			writeInteger(returnPlayerInfo[i].getUserStatus());
			writeLong(returnPlayerInfo[i].getCurGold());
			writeString(returnPlayerInfo[i].getHeadImg());
			writeString(returnPlayerInfo[i].getDiceContainer());
			writeInteger(returnPlayerInfo[i].getSeat());
			writeInteger(returnPlayerInfo[i].getWinTimes());
			writeInteger(returnPlayerInfo[i].getVipLevel());
			writeInteger(returnPlayerInfo[i].getNum());
			writeInteger(returnPlayerInfo[i].getValue());
			writeInteger(returnPlayerInfo[i].getDiceType());
			int[] diceValues=returnPlayerInfo[i].getDiceValues();
			writeShort(diceValues.length);
			for(int j=0; j<diceValues.length; j++){
				writeInteger(diceValues[j]);
			}
			int[] redDiceValues=returnPlayerInfo[i].getRedDiceValues();
			writeShort(redDiceValues.length);
			for(int j=0; j<redDiceValues.length; j++){
				writeInteger(redDiceValues[j]);
			}
			writeString(returnPlayerInfo[i].getClockImg());
			writeInteger(returnPlayerInfo[i].getClockItemId());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_ENTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_ENTER";
	}

	public com.gameserver.bazoo.data.ReturnPlayerInfo[] getReturnPlayerInfo(){
		return returnPlayerInfo;
	}

	public void setReturnPlayerInfo(com.gameserver.bazoo.data.ReturnPlayerInfo[] returnPlayerInfo){
		this.returnPlayerInfo = returnPlayerInfo;
	}	
}