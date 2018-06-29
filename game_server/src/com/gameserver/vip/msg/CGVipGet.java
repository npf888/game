package com.gameserver.vip.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.vip.handler.VipHandlerFactory;

/**
 * 领取vip
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGVipGet extends CGMessage{
	
	
	public CGVipGet (){
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
		return MessageType.CG_VIP_GET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_VIP_GET";
	}
	


	@Override
	public void execute() {
		VipHandlerFactory.getHandler().handleVipGet(this.getSession().getPlayer(), this);
	}
}