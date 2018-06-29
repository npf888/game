package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.item.handler.ItemHandlerFactory;

/**
 * 请求商城的道具列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooMallRequest extends CGMessage{
	
	
	public CGBazooMallRequest (){
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
		return MessageType.CG_BAZOO_MALL_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_MALL_REQUEST";
	}
	


	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleBazooMallRequest(this.getSession().getPlayer(), this);
	}
}