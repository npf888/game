package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.activity.handler.ActivityHandlerFactory;

/**
 * 活动列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGActivityList extends CGMessage{
	
	
	public CGActivityList (){
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
		return MessageType.CG_ACTIVITY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ACTIVITY_LIST";
	}
	


	@Override
	public void execute() {
		ActivityHandlerFactory.getHandler().handleActivityList(this.getSession().getPlayer(), this);
	}
}