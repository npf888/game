package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 获取俱乐部信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubInfoGet extends CGMessage{
	
	/** 玩家id */
	private long playerId;
	
	public CGClubInfoGet (){
	}
	
	public CGClubInfoGet (
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
		return MessageType.CG_CLUB_INFO_GET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_INFO_GET";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubInfoGet(this.getSession().getPlayer(), this);
	}
}