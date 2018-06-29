package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.lobby.handler.LobbyHandlerFactory;

/**
 * 请求所有老虎机的最高彩金（slotType->jackpot）
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGAllSlotNewJackpots extends CGMessage{
	
	
	public CGAllSlotNewJackpots (){
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
		return MessageType.CG_ALL_SLOT_NEW_JACKPOTS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ALL_SLOT_NEW_JACKPOTS";
	}
	


	@Override
	public void execute() {
		LobbyHandlerFactory.getHandler().handleAllSlotNewJackpots(this.getSession().getPlayer(), this);
	}
}