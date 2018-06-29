package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐留言板面板
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubNotePanel extends CGMessage{
	
	
	public CGClubNotePanel (){
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
		return MessageType.CG_CLUB_NOTE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_NOTE_PANEL";
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubNotePanel(this.getSession().getPlayer(), this);
	}
}