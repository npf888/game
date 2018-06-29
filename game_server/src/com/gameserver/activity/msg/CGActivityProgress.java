package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.activity.handler.ActivityHandlerFactory;

/**
 * 获取活动进度数据
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGActivityProgress extends CGMessage{
	
	
	public CGActivityProgress (){
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
		return MessageType.CG_ACTIVITY_PROGRESS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ACTIVITY_PROGRESS";
	}
	


	@Override
	public void execute() {
		ActivityHandlerFactory.getHandler().handleActivityProgress(this.getSession().getPlayer(), this);
	}
}