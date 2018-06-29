package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 打开俱乐部面板
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubPanel extends CGMessage{
	
	
	public CGClubPanel (){
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
		return MessageType.CG_CLUB_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_PANEL";
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubPanel(this.getSession().getPlayer(), this);
	}
}