package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐玩家信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanBaccart extends GCMessage{
	
	/** 百家乐 */
	private com.gameserver.baccart.data.HumanBaccartData humanBaccartData;

	public GCHumanBaccart (){
	}
	
	public GCHumanBaccart (
			com.gameserver.baccart.data.HumanBaccartData humanBaccartData ){
			this.humanBaccartData = humanBaccartData;
	}

	@Override
	protected boolean readImpl() {
		humanBaccartData = new com.gameserver.baccart.data.HumanBaccartData();
					humanBaccartData.setGameNum(readLong());
							humanBaccartData.setWinNum(readLong());
							humanBaccartData.setBeaconNum(readLong());
							humanBaccartData.setPlayerId(readLong());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanBaccartData.getGameNum());
		writeLong(humanBaccartData.getWinNum());
		writeLong(humanBaccartData.getBeaconNum());
		writeLong(humanBaccartData.getPlayerId());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_BACCART;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_BACCART";
	}

	public com.gameserver.baccart.data.HumanBaccartData getHumanBaccartData(){
		return humanBaccartData;
	}
		
	public void setHumanBaccartData(com.gameserver.baccart.data.HumanBaccartData humanBaccartData){
		this.humanBaccartData = humanBaccartData;
	}
}