package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.activity.handler.ActivityHandlerFactory;

/**
 * 还有未领取的活动的金币
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGStillHaveActivityGold extends CGMessage{
	
	
	public CGStillHaveActivityGold (){
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
		return MessageType.CG_STILL_HAVE_ACTIVITY_GOLD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_STILL_HAVE_ACTIVITY_GOLD";
	}
	


	@Override
	public void execute() {
		ActivityHandlerFactory.getHandler().handleStillHaveActivityGold(this.getSession().getPlayer(), this);
	}
}