package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.misc.handler.MiscHandlerFactory;

/**
 * fb奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFbGetReward extends CGMessage{
	
	
	public CGFbGetReward (){
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
		return MessageType.CG_FB_GET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FB_GET_REWARD";
	}
	


	@Override
	public void execute() {
		MiscHandlerFactory.getHandler().handleFbGetReward(this.getSession().getPlayer(), this);
	}
}