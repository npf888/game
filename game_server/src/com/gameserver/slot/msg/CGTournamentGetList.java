package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 进入老虎机竞赛页面
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTournamentGetList extends CGMessage{
	
	
	public CGTournamentGetList (){
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
		return MessageType.CG_TOURNAMENT_GET_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TOURNAMENT_GET_LIST";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleTournamentGetList(this.getSession().getPlayer(), this);
	}
}