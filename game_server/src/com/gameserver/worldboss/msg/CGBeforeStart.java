package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.worldboss.handler.WorldbossHandlerFactory;

/**
 * 主动请求比赛之前 多少分钟
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBeforeStart extends CGMessage{
	
	
	public CGBeforeStart (){
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
		return MessageType.CG_BEFORE_START;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BEFORE_START";
	}
	


	@Override
	public void execute() {
		WorldbossHandlerFactory.getHandler().handleBeforeStart(this.getSession().getPlayer(), this);
	}
}