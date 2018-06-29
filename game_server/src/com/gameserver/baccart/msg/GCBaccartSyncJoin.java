package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家加入
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartSyncJoin extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.baccart.data.BaccartPlayerData playerData;

	public GCBaccartSyncJoin (){
	}
	
	public GCBaccartSyncJoin (
			com.gameserver.baccart.data.BaccartPlayerData playerData ){
			this.playerData = playerData;
	}

	@Override
	protected boolean readImpl() {
		playerData = new com.gameserver.baccart.data.BaccartPlayerData();
					playerData.setPlayerId(readLong());
							playerData.setName(readString());
							playerData.setImg(readString());
							playerData.setGold(readLong());
							playerData.setVip(readInteger());
							playerData.setPos(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerData.getPlayerId());
		writeString(playerData.getName());
		writeString(playerData.getImg());
		writeLong(playerData.getGold());
		writeInteger(playerData.getVip());
		writeInteger(playerData.getPos());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_SYNC_JOIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_SYNC_JOIN";
	}

	public com.gameserver.baccart.data.BaccartPlayerData getPlayerData(){
		return playerData;
	}
		
	public void setPlayerData(com.gameserver.baccart.data.BaccartPlayerData playerData){
		this.playerData = playerData;
	}
}