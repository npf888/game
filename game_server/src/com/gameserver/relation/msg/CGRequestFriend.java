package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 客户端请求添加好友
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRequestFriend extends CGMessage{
	
	/** 玩家id */
	private long playerId;
	
	public CGRequestFriend (){
	}
	
	public CGRequestFriend (
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
		return MessageType.CG_REQUEST_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQUEST_FRIEND";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleRequestFriend(this.getSession().getPlayer(), this);
	}
}