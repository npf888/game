package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.worldboss.handler.WorldbossHandlerFactory;

/**
 * boss的开始结束信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBossStartEndInfo extends CGMessage{
	
	
	public CGBossStartEndInfo (){
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
		return MessageType.CG_BOSS_START_END_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BOSS_START_END_INFO";
	}
	


	@Override
	public void execute() {
		WorldbossHandlerFactory.getHandler().handleBossStartEndInfo(this.getSession().getPlayer(), this);
	}
}