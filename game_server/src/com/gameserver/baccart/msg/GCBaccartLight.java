package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家明灯
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartLight extends GCMessage{
	
	/** 明灯信息 */
	private com.gameserver.baccart.data.BaccartLightData[] lightDataList;

	public GCBaccartLight (){
	}
	
	public GCBaccartLight (
			com.gameserver.baccart.data.BaccartLightData[] lightDataList ){
			this.lightDataList = lightDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		lightDataList = new com.gameserver.baccart.data.BaccartLightData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartLightData obj = new com.gameserver.baccart.data.BaccartLightData();
			obj.setContinueWin(readInteger());
			obj.setMinWins(readLong());
			obj.setPlayerId(readLong());
			lightDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(lightDataList.length);
		for(int i=0; i<lightDataList.length; i++){
			writeInteger(lightDataList[i].getContinueWin());
			writeLong(lightDataList[i].getMinWins());
			writeLong(lightDataList[i].getPlayerId());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_LIGHT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_LIGHT";
	}

	public com.gameserver.baccart.data.BaccartLightData[] getLightDataList(){
		return lightDataList;
	}

	public void setLightDataList(com.gameserver.baccart.data.BaccartLightData[] lightDataList){
		this.lightDataList = lightDataList;
	}	
}