package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 弹劾状态
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubTanheState extends CGMessage{
	
	
	public CGClubTanheState (){
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
		return MessageType.CG_CLUB_TANHE_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_TANHE_STATE";
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubTanheState(this.getSession().getPlayer(), this);
	}
}