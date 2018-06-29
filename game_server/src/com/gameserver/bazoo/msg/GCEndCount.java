package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 结算返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCEndCount extends GCMessage{
	
	/** 最终 这一局 有几个 叫的色子值(个数) */
	private int finalDiceNum;
	/** 结算返回 每个人的信息 */
	private com.gameserver.bazoo.data.EndCountInfo[] endCountInfo;

	public GCEndCount (){
	}
	
	public GCEndCount (
			int finalDiceNum,
			com.gameserver.bazoo.data.EndCountInfo[] endCountInfo ){
			this.finalDiceNum = finalDiceNum;
			this.endCountInfo = endCountInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		finalDiceNum = readInteger();
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
		writeInteger(finalDiceNum);
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
		return MessageType.GC_END_COUNT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_END_COUNT";
	}

	public int getFinalDiceNum(){
		return finalDiceNum;
	}
		
	public void setFinalDiceNum(int finalDiceNum){
		this.finalDiceNum = finalDiceNum;
	}

	public com.gameserver.bazoo.data.EndCountInfo[] getEndCountInfo(){
		return endCountInfo;
	}

	public void setEndCountInfo(com.gameserver.bazoo.data.EndCountInfo[] endCountInfo){
		this.endCountInfo = endCountInfo;
	}	
}