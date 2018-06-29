package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 离开俱乐部
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubLeave extends CGMessage{
	
	
	public CGClubLeave (){
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
		return MessageType.CG_CLUB_LEAVE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_LEAVE";
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubLeave(this.getSession().getPlayer(), this);
	}
}