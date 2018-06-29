package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 获取俱乐留邀请
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubInvateList extends CGMessage{
	
	
	public CGClubInvateList (){
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
		return MessageType.CG_CLUB_INVATE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_INVATE_LIST";
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubInvateList(this.getSession().getPlayer(), this);
	}
}