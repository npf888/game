package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.worldboss.handler.WorldbossHandlerFactory;

/**
 * 获取boss信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetBossInfo extends CGMessage{
	
	
	public CGGetBossInfo (){
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
		return MessageType.CG_GET_BOSS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_BOSS_INFO";
	}
	


	@Override
	public void execute() {
		WorldbossHandlerFactory.getHandler().handleGetBossInfo(this.getSession().getPlayer(), this);
	}
}