package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家站起
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartStand extends GCMessage{
	
	/** 玩家d */
	private long playerId;

	public GCBaccartStand (){
	}
	
	public GCBaccartStand (
			long playerId ){
			this.playerId = playerId;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_STAND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_STAND";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
}