package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.worldboss.handler.WorldbossHandlerFactory;

/**
 * 进入老虎机主动请求
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRefreshBossInfo extends CGMessage{
	
	
	public CGRefreshBossInfo (){
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
		return MessageType.CG_REFRESH_BOSS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REFRESH_BOSS_INFO";
	}
	


	@Override
	public void execute() {
		WorldbossHandlerFactory.getHandler().handleRefreshBossInfo(this.getSession().getPlayer(), this);
	}
}