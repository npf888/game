package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 8 或 11
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomRoundResult extends GCMessage{
	
	/** 结算 中 的状态  */
	private int status;
	/** 结算返回 每个人的信息 */
	private com.gameserver.bazoo.data.EndCountInfo[] endCountInfo;

	public GCStateRoomRoundResult (){
	}
	
	public GCStateRoomRoundResult (
			int status,
			com.gameserver.bazoo.data.EndCountInfo[] endCountInfo ){
			this.status = status;
			this.endCountInfo = endCountInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		status = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		endCountInfo = new com.gameserver.bazoo.data.EndCountInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.EndCountInfo obj = new com.gameserver.bazoo.data.EndCountInfo();
			obj.setPassportId(readLong());
			obj.setMultiple(readInteger());
			obj.setGold(readLong());
			obj.setName(readString());
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
				obj.setWinPassportId(subList);
			}
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setWinMultiple(subList);
			}
			obj.setPersonTotalGold(readLong());
			endCountInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(status);
		writeShort(endCountInfo.length);
		for(int i=0; i<endCountInfo.length; i++){
			writeLong(endCountInfo[i].getPassportId());
			writeInteger(endCountInfo[i].getMultiple());
			writeLong(endCountInfo[i].getGold());
			writeString(endCountInfo[i].getName());
			int[] diceValues=endCountInfo[i].getDiceValues();
			writeShort(diceValues.length);
			for(int j=0; j<diceValues.length; j++){
				writeInteger(diceValues[j]);
			}
			int[] winPassportId=endCountInfo[i].getWinPassportId();
			writeShort(winPassportId.length);
			for(int j=0; j<winPassportId.length; j++){
				writeInteger(winPassportId[j]);
			}
			int[] winMultiple=endCountInfo[i].getWinMultiple();
			writeShort(winMultiple.length);
			for(int j=0; j<winMultiple.length; j++){
				writeInteger(winMultiple[j]);
			}
			writeLong(endCountInfo[i].getPersonTotalGold());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_ROUND_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_ROUND_RESULT";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}

	public com.gameserver.bazoo.data.EndCountInfo[] getEndCountInfo(){
		return endCountInfo;
	}

	public void setEndCountInfo(com.gameserver.bazoo.data.EndCountInfo[] endCountInfo){
		this.endCountInfo = endCountInfo;
	}	
}