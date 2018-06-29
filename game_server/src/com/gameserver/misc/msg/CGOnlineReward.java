package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.misc.handler.MiscHandlerFactory;

/**
 * 在线奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGOnlineReward extends CGMessage{
	
	
	public CGOnlineReward (){
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
		return MessageType.CG_ONLINE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ONLINE_REWARD";
	}
	


	@Override
	public void execute() {
		MiscHandlerFactory.getHandler().handleOnlineReward(this.getSession().getPlayer(), this);
	}
}