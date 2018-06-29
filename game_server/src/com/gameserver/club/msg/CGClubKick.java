package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 踢出俱乐部
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubKick extends CGMessage{
	
	/** 玩家id */
	private long playerId;
	
	public CGClubKick (){
	}
	
	public CGClubKick (
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
		return MessageType.CG_CLUB_KICK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_KICK";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubKick(this.getSession().getPlayer(), this);
	}
}