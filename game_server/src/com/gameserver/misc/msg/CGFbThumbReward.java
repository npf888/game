package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.misc.handler.MiscHandlerFactory;

/**
 * fb点赞奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFbThumbReward extends CGMessage{
	
	
	public CGFbThumbReward (){
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
		return MessageType.CG_FB_THUMB_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FB_THUMB_REWARD";
	}
	


	@Override
	public void execute() {
		MiscHandlerFactory.getHandler().handleFbThumbReward(this.getSession().getPlayer(), this);
	}
}