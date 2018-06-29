package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 心跳
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooHeartBeat extends CGMessage{
	
	
	public CGBazooHeartBeat (){
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
		return MessageType.CG_BAZOO_HEART_BEAT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_HEART_BEAT";
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleBazooHeartBeat(this.getSession().getPlayer(), this);
	}
}