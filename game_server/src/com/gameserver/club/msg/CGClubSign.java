package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐签到
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubSign extends CGMessage{
	
	
	public CGClubSign (){
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
		return MessageType.CG_CLUB_SIGN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_SIGN";
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubSign(this.getSession().getPlayer(), this);
	}
}