package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐部发起弹劾
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubTanhe extends CGMessage{
	
	
	public CGClubTanhe (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_TANHE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_TANHE";
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubTanhe(this.getSession().getPlayer(), this);
	}
}