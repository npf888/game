package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家退出
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartExit extends GCMessage{
	
	/** 玩家id */
	private long playerId;

	public GCBaccartExit (){
	}
	
	public GCBaccartExit (
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
		return MessageType.GC_BACCART_EXIT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_EXIT";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
}