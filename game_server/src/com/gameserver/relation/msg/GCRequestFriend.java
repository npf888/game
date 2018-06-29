package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 请求添加好友
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRequestFriend extends GCMessage{
	
	/** 玩家id */
	private long playerId;

	public GCRequestFriend (){
	}
	
	public GCRequestFriend (
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
		return MessageType.GC_REQUEST_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REQUEST_FRIEND";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
}