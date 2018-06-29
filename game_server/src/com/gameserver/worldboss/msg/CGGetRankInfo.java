package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.worldboss.handler.WorldbossHandlerFactory;

/**
 * 伤害 排行榜 的 信息 请求
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetRankInfo extends CGMessage{
	
	
	public CGGetRankInfo (){
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
		return MessageType.CG_GET_RANK_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_RANK_INFO";
	}
	


	@Override
	public void execute() {
		WorldbossHandlerFactory.getHandler().handleGetRankInfo(this.getSession().getPlayer(), this);
	}
}