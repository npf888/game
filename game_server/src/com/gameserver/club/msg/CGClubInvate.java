package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 邀请某人加入俱乐部
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubInvate extends CGMessage{
	
	/** 玩家id */
	private long playerId;
	
	public CGClubInvate (){
	}
	
	public CGClubInvate (
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
		return MessageType.CG_CLUB_INVATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_INVATE";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubInvate(this.getSession().getPlayer(), this);
	}
}