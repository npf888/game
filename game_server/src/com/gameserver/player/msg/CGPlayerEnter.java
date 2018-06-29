package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 加载角色
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerEnter extends CGMessage{
	
	
	public CGPlayerEnter (){
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
		return MessageType.CG_PLAYER_ENTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_ENTER";
	}
	


	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handlePlayerEnter(this.getSession().getPlayer(), this);
	}
}